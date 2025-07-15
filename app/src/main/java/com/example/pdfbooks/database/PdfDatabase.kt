package com.example.pdfbooks.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pdfbooks.dao.PdfDao
import com.example.pdfbooks.model.PdfData

@Database(entities = [PdfData::class], version = 1)
abstract class PdfDatabase : RoomDatabase() {
    abstract fun pdfDao(): PdfDao

    companion object {
        @Volatile
        var INSTANCE: PdfDatabase? = null
        fun getInstance(context: Context): PdfDatabase {
            val instance = Room.databaseBuilder(
                context.applicationContext, PdfDatabase::class.java, "app_database"
            ).build()
            INSTANCE = instance
            return instance
        }
    }
}