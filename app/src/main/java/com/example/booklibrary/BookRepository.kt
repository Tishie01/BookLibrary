package com.example.booklibrary


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class BookRepository(private val bookDao: BookDao) {
    suspend fun addBook(book: Book) {
        withContext(Dispatchers.IO) {
            bookDao.addBook(book)
        }
    }

    suspend fun updateBook(book: Book) {
        withContext(Dispatchers.IO) {
            bookDao.updateBook(book)
        }
    }

    suspend fun deleteBook(book: Book) {
        withContext(Dispatchers.IO) {
            bookDao.deleteBook(book)
        }
    }

    fun getAllBooks() = bookDao.getAllBooks()
}
