package com.example.loginscreen.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.loginscreen.dao.PlantDao
import com.example.loginscreen.entities.Plants

@Database(entities = [Plants::class], version = 1, exportSchema = false)
abstract class PlantDatabase : RoomDatabase() {

    companion object {
        var notesDatabase: PlantDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): PlantDatabase {
            if (notesDatabase == null) {
                notesDatabase = Room.databaseBuilder(
                    context
                    , PlantDatabase::class.java
                    , "notes.db"
                ).build()
            }
            return notesDatabase!!
        }
    }

    abstract fun noteDao():PlantDao
}