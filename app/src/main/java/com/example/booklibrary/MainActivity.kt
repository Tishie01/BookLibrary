package com.example.booklibrary

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var add_button: FloatingActionButton
    private lateinit var viewModel: BookViewModel
    private lateinit var customAdapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        add_button = findViewById(R.id.add_button)

        viewModel = ViewModelProvider(this).get(BookViewModel::class.java)

        add_button.setOnClickListener {
            val intent = Intent(this@MainActivity, AddActivity::class.java)
            startActivity(intent)
        }

        observeViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.my_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_all -> {
                // Handle delete all action
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun observeViewModel() {
        viewModel.allBooks.observe(this, { books ->
            customAdapter = CustomAdapter(this@MainActivity, books)
            recyclerView.adapter = customAdapter
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        })
    }
}
