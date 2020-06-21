package com.example.notesapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notesapp.db.entity.Note
import com.example.notesapp.repository.NotesRepository
import com.example.notesapp.viewmodels.NoteViewModelContract
import org.junit.Assert
import org.mockito.Mockito

class CommonTest {

    companion object {

        fun onDeleteTest(note: Note, viewModel: NoteViewModelContract, repository: NotesRepository, liveData: MutableLiveData<Note>, testNotesList: MutableList<Note>) {
            liveData.value = note

            Mockito.`when`(repository.deleteNote(note)).then {
                testNotesList.remove(note)
                return@then liveData

            }

            viewModel.onDelete(note)

            Assert.assertEquals(note.id, viewModel.getDeletingNoteLd().value?.id)
            Assert.assertEquals(false, testNotesList.contains(note))
        }
    }
}