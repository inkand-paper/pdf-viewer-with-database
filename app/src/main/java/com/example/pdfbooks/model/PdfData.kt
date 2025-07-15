package com.example.pdfbooks.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_list")
data class PdfData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name")val name: String,
    @ColumnInfo(name = "author")val author: String,
    @ColumnInfo(name = "version")val version: String,
    @ColumnInfo(name = "publish")val publish: String
)
