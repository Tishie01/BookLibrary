package com.example.booklibrary

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import com.example.booklibrary.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UpdateActivity : AppCompatActivity() {
    private lateinit var titleInput: EditText
    private lateinit var authorInput: EditText
    private lateinit var pagesInput: EditText
    private lateinit var updateButton: Button
    private lateinit var deleteButton: Button
    private lateinit var bookRepository: BookRepository

    private var id: Long = 0
    private var title: String? = null
    private var author: String? = null
    private var pages: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        titleInput = findViewById(R.id.title_input2)
        authorInput = findViewById(R.id.author_input2)
        pagesInput = findViewById(R.id.pages_input2)
        updateButton = findViewById(R.id.update_button)
        deleteButton = findViewById(R.id.delete_button)

        val database = BookDatabase.getDatabase(this)
        val bookDao = database.bookDao()
        bookRepository = BookRepository(bookDao)

        //First we call this
        andSetIntentData()

        //Set actionbar title after getAndSetIntentData method
        supportActionBar?.title = title

        updateButton.setOnClickListener {
            val updatedTitle = titleInput.text.toString().trim()
            val updatedAuthor = authorInput.text.toString().trim()
            val updatedPages = pagesInput.text.toString().trim().toInt()

            val updatedBook = Book(id, updatedTitle, updatedAuthor, updatedPages)

            GlobalScope.launch(Dispatchers.Main) {
                bookRepository.updateBook(updatedBook)
                Toast.makeText(applicationContext, "Book updated", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        deleteButton.setOnClickListener { confirmDialog() }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun andSetIntentData() {
        if (intent.hasExtra("id") && intent.hasExtra("title") &&
            intent.hasExtra("author") && intent.hasExtra("pages")
        ) {
            // Getting Data from Intent
            id = intent.getLongExtra("id", 0)
            title = intent.getStringExtra("title")
            author = intent.getStringExtra("author")
            pages = intent.getIntExtra("pages", 0)

            // Setting Intent Data
            titleInput.setText(title)
            authorInput.setText(author)
            pagesInput.setText(pages.toString())
            Log.d("stev", "$title $author $pages")
        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun confirmDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete $title ?")
        builder.setMessage("Are you sure you want to delete $title ?")
        builder.setPositiveButton("Yes") { _, _ ->
            val bookToDelete =  Book(id, "", "", 0)
            GlobalScope.launch(Dispatchers.Main) {
                bookRepository.deleteBook(bookToDelete)
                Toast.makeText(applicationContext, "Book deleted", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
        builder.setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
        builder.create().show()
    }
}
