package com.example.notesapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notesapp.db.entity.Note
import com.example.notesapp.repository.NotesRepository
import java.lang.Exception

class NotesListViewModel(val notesRepository: NotesRepository): ViewModel(), NoteViewModelContract {

    lateinit var notes: LiveData<List<Note>>

    var _deletedNoteLd = MutableLiveData<Note>()
    val deletedNoteLd: LiveData<Note>
        get() = _deletedNoteLd

    init {
        onGetNotes()
    }

    fun onGetNotes() {
        notes = notesRepository.getAllNotes()
    }

    override fun onDelete(note: Note?) {
        note?.let {
            _deletedNoteLd.value = notesRepository.deleteNote(note).value
        }
    }

    override fun getDeletingNoteLd(): LiveData<Note> {
        return deletedNoteLd
    }
}