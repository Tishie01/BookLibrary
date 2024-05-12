package com.example.booklibrary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(
    private val context: Context,
    private val books: List<Book>
) : RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.my_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val book = books[position]
        holder.bind(book)
    }

    override fun getItemCount(): Int {
        return books.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTxt: TextView = itemView.findViewById(R.id.book_title_txt)
        private val authorTxt: TextView = itemView.findViewById(R.id.book_author_txt)
        private val pagesTxt: TextView = itemView.findViewById(R.id.book_pages_txt)

        fun bind(book: Book) {
            titleTxt.text = book.title
            authorTxt.text = book.author
            pagesTxt.text = book.pages.toString()
        }
    }
}
