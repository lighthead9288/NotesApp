package com.example.notesapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notesapp.db.entity.Note

interface NoteViewModelContract {

    fun onDelete(note: Note?)

    fun getDeletingNoteLd(): LiveData<Note>
}