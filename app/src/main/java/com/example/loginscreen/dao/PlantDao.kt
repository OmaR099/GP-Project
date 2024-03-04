package com.example.loginscreen.dao

import androidx.room.*
import com.example.loginscreen.entities.Plants

@Dao
interface PlantDao {

    @Query("SELECT * FROM notes ORDER BY id DESC")
    suspend fun getAllNotes() : List<Plants>

    @Query("SELECT * FROM notes WHERE id =:id")
    suspend fun getSpecificNote(id:Int) : Plants

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(note:Plants)

    @Delete
    suspend fun deleteNote(note:Plants)

    @Query("DELETE FROM notes WHERE id =:id")
    suspend fun deleteSpecificNote(id:Int)

    @Update
    suspend fun updateNote(note:Plants)
}