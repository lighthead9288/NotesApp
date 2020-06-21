package com.example.notesapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.notesapp.db.entity.Note
import com.example.notesapp.repository.NotesRepository
import com.example.notesapp.viewmodels.NoteViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith

import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExtendWith(InstantExecutorExtension::class)
class NoteViewModelUnitTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var notesRepository: NotesRepository

    lateinit var noteViewModel: NoteViewModel

    var savedNoteLiveData = MutableLiveData<Note>()

    var deletedNoteLiveData = MutableLiveData<Note>()


    private var testNotesList = mutableListOf<Note>()


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        noteViewModel = NoteViewModel(notesRepository)

        testNotesList = arrayListOf(Note(id = 0, name = "Test 0", description = "Description test 0"), Note(id=1, name="Test 1", description = "Description test 1"))
    }

    @Test
    fun onSaveNewNote() {
        val newNote = Note(id=2, name="New note", description = "New note description")
      //  noteViewModel.isEditMode = false
        initNote(newNote)

        `when`(notesRepository.addNote(newNote)).thenReturn(savedNoteLiveData)

        noteViewModel.onSave(newNote)

        Assert.assertEquals(newNote.name, noteViewModel.savedNoteLd.value?.name)
        Assert.assertEquals(newNote.description, noteViewModel.savedNoteLd.value?.description)
        Assert.assertEquals(true, noteViewModel.isShouldCloseEditor.value)

    }

    @Test
    fun onSaveExistingNote() {
        val existingNote = testNotesList[0]
        val prevDescription = existingNote.description
       // noteViewModel.isEditMode = true
        existingNote.description += " changed"
        initNote(existingNote)

        `when`(notesRepository.updateNote(existingNote)).thenReturn(savedNoteLiveData)

        noteViewModel.onSave(existingNote, true)

        Assert.assertNotEquals(prevDescription, noteViewModel.savedNoteLd.value?.description)
        Assert.assertEquals(true, noteViewModel.isShouldCloseEditor.value)
    }

    @Test
    fun deleteNote() {
        val noteForDeleting = testNotesList[0]
        CommonTest.onDeleteTest(noteForDeleting, noteViewModel, notesRepository, deletedNoteLiveData, testNotesList)

    }

    @Test
    fun checkFieldsValues() {
        val noteWithoutName = Note(id=2, name="", description = "New note description")
        initNote(noteWithoutName)
        noteViewModel.onSave(noteWithoutName)
        Assert.assertEquals(true, noteViewModel.isNoteNameEmpty.value)

        val noteWithoutDescription = Note(id=2, name="New note", description = "")
        initNote(noteWithoutDescription)
        noteViewModel.onSave(noteWithoutDescription)
        Assert.assertEquals(true, noteViewModel.isNoteDescriptionEmpty.value)

        val correctNote = Note(id=2, name="New note", description = "New note description")
        initNote(correctNote)
        noteViewModel.onSave(correctNote)
        Assert.assertEquals(false, noteViewModel.isNoteNameEmpty.value)
        Assert.assertEquals(false, noteViewModel.isNoteDescriptionEmpty.value)

    }

    private fun initNote(note: Note) {
        savedNoteLiveData.value = note

    }


}