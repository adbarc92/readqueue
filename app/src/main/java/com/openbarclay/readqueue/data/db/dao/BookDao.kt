package com.openbarclay.readqueue.data.db.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.openbarclay.readqueue.data.db.entity.BookEntity
import kotlinx.coroutines.flow.Flow

@androidx.room.Dao
interface BookDao {
    @Query("SELECT * FROM books ORDER BY position ASC")
    fun getAllBooks(): Flow<List<BookEntity>>

    @Query("SELECT * FROM books WHERE id = :id")
    suspend fun getBookById(id: Int): BookEntity?

    @Insert
    suspend fun insert(book: BookEntity)

    @Update
    suspend fun update(book: BookEntity)

    @Delete
    suspend fun delete(book: BookEntity)
}