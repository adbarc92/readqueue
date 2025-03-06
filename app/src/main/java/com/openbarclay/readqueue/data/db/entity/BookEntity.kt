package com.openbarclay.readqueue.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class BookStatus {
    TO_READ, IN_PROGRESS, COMPLETED
}

// FIXME: This should probably go somewhere else eventually
data class Book(
    val id: Int = 0,
    val title: String,
    val author: String,
    val status: BookStatus,
    val position: Int
)

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val author: String,
    val status: BookStatus,
    val position: Int
) {
    fun toBook() = Book(id, title, author, status, position)
}
