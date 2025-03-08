package com.openbarclay.readqueue.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openbarclay.readqueue.data.db.dao.BookDao
import com.openbarclay.readqueue.data.db.entity.Book
import com.openbarclay.readqueue.data.db.entity.BookEntity
import com.openbarclay.readqueue.data.db.entity.BookStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(private val bookDao: BookDao) : ViewModel() {
    // Private mutable state flow to hold the list of books
    private val _books = MutableStateFlow<List<Book>>(emptyList())
    // Public read-only state flow for UI to observe
    val books: StateFlow<List<Book>> = _books.asStateFlow()

    // Initialize by loading books from Room
    init {
        loadBooks()
    }

    // Load books from Room database
    private fun loadBooks() {
        viewModelScope.launch {
            bookDao.getAllBooks().collect { bookEntities ->
                _books.value = bookEntities.map { it.toBook() }
            }
        }
    }

    // Add a new book
    fun addBook(title: String, author: String) {
        if (title.isBlank()) return // Basic validation
        viewModelScope.launch {
            val newBook = BookEntity(
                title = title,
                author = author,
                status = BookStatus.TO_READ,
                position = _books.value.size // Append to end
            )
            bookDao.insert(newBook)
            // loadBooks() is not needed here because Room Flow will update _books
        }
    }

    // Update book status
    fun updateStatus(bookId: Int, newStatus: BookStatus) {
        viewModelScope.launch {
            val updatedBook = bookDao.getBookById(bookId)?.copy(status = newStatus) ?: return@launch
            bookDao.update(updatedBook)
        }
    }

    // Reorder books (drag-and-drop)
    fun reorderBooks(fromPosition: Int, toPosition: Int) {
        viewModelScope.launch {
            val currentList = _books.value.toMutableList()
            val bookToMove = currentList.removeAt(fromPosition)
            currentList.add(toPosition, bookToMove)

            // Update positions in Room
            currentList.forEachIndexed { index, book ->
                val updatedEntity = bookDao.getBookById(book.id)?.copy(position = index)
                updatedEntity?.let { bookDao.update(it) }
            }
            _books.value = currentList // Update UI state
        }
    }
}