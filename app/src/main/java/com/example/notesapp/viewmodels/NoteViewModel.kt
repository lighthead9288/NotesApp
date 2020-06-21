package com.example.notesapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notesapp.db.entity.Note
import com.example.notesapp.repository.NotesRepository

class NoteViewModel(val notesRepository: NotesRepository): ViewModel(), NoteViewModelContract {

    var _isNoteNameEmpty = MutableLiveData<Boolean>()
    val isNoteNameEmpty: LiveData<Boolean>
        get() = _isNoteNameEmpty

    var _isNoteDescriptionEmpty = MutableLiveData<Boolean>()
    val isNoteDescriptionEmpty: LiveData<Boolean>
        get() = _isNoteDescriptionEmpty

    var _isShouldCloseEditor = MutableLiveData<Boolean>()
    val isShouldCloseEditor: LiveData<Boolean>
        get() = _isShouldCloseEditor

    var _savedNoteLd = MutableLiveData<Note>()
    val savedNoteLd: LiveData<Note>
        get() = _savedNoteLd

    var _deletedNoteLd = MutableLiveData<Note>()
    val deletedNoteLd: LiveData<Note>
        get() = _deletedNoteLd


    fun onSave(savedNote: Note, isEditMode: Boolean = false) {
        val isNoteEmpty = checkFieldsValues(savedNote)
        if (!isNoteEmpty) {
            if (isEditMode) {
                notesRepository.updateNote(savedNote)
                _savedNoteLd.value = savedNote
            }
            else {
                notesRepository.addNote(savedNote)
                _savedNoteLd.value = savedNote
            }
            _isShouldCloseEditor.value = true
        }
        else
            _isShouldCloseEditor.value = false

    }

    override fun onDelete(note: Note?) {
        note?.let {
            notesRepository.deleteNote(note as Note)
            _deletedNoteLd.value = note
            _isShouldCloseEditor.value = true
        }
    }

    override fun getDeletingNoteLd(): LiveData<Note> {
        return deletedNoteLd
    }

    private fun checkFieldsValues(note:Note): Boolean {
        val name = /*noteName*/note.name
        val desc = /*noteDescription*/note.description
        _isNoteNameEmpty.value = false
        _isNoteDescriptionEmpty.value = false

        var isNoteEmpty = false
        if (name.isNullOrEmpty()) {
            _isNoteNameEmpty.value = true
            isNoteEmpty = true
        }
        if (desc.isNullOrEmpty()) {
            _isNoteDescriptionEmpty.value = true
            isNoteEmpty = true
        }
        return isNoteEmpty
    }

}