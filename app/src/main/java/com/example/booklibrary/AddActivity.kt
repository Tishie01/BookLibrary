package com.example.booklibrary

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import com.example.booklibrary.Book
import com.example.booklibrary.BookRepository
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope

class AddActivity : AppCompatActivity() {
    private lateinit var titleInput: EditText
    private lateinit var authorInput: EditText
    private lateinit var pagesInput: EditText
    private lateinit var addButton: Button
    private lateinit var bookRepository: BookRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        titleInput = findViewById(R.id.title_input)
        authorInput = findViewById(R.id.author_input)
        pagesInput = findViewById(R.id.pages_input)
        addButton = findViewById(R.id.add_button)

        val database = BookDatabase.getDatabase(this)
        val bookDao = database.bookDao()
        bookRepository = BookRepository(bookDao)

        addButton.setOnClickListener {
            val title = titleInput.text.toString().trim()
            val author = authorInput.text.toString().trim()
            val pages = pagesInput.text.toString().trim().toInt()

            val book = Book(title = title, author = author, pages = pages)

            // Add book to the database using the repository
            lifecycleScope.launch {
                bookRepository.addBook(book)
                // Navigate up to parent activity (MainActivity)
                NavUtils.navigateUpFromSameTask(this@AddActivity)
            }
        }
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
}
