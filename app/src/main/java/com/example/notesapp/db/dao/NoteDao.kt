package com.example.notesapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notesapp.db.entity.Note

@Dao
interface NoteDao {

    @Insert
    fun add(note: Note): Long

    @Update
    fun update(note: Note): Int

    @Delete
    fun delete(note: Note): Int

    @Query("SELECT * FROM Notes")
    fun getAllNotes(): LiveData<List<Note>>
}