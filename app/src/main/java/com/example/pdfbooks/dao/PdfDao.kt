package com.example.pdfbooks.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pdfbooks.model.PdfData

@Dao

interface PdfDao {

    @Query("Select * FROM book_list")
    fun getAllBooks(): List<PdfData>

    @Query("Select * FROM book_list WHERE id = :newId LIMIT 5")
    fun getAllBooksById(newId: Int): List<PdfData>

    @Query("Select * FROM book_list WHERE author LIKE '%' || :author || '%'")
    fun getBooksByAuthor(author: String): List<PdfData>
    @Query("Select * FROM book_list WHERE name LIKE '%' || :name || '%'")
    fun getBooksByName(name: String): List<PdfData>

    @Insert
    fun insertData(book: PdfData)

    @Delete
    fun deleteData(book: PdfData)

    @Update
    fun updateData(book: PdfData)

}