package com.example.notesapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.notesapp.db.NotesDatabase
import com.example.notesapp.db.entity.Note
import com.example.notesapp.repository.NotesRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExtendWith(InstantExecutorExtension::class)
class NotesRepositoryUnitTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var notesRepository: NotesRepository

    var notesLiveData = MutableLiveData<List<Note>>()

    var noteLiveData = MutableLiveData<Note>()

    private var testNotesList = mutableListOf<Note>()


    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        testNotesList = arrayListOf(Note(id = 0, name = "Test 0", description = "Description test 0"), Note(id=1, name="Test 1", description = "Description test 1"))
    }

    @Test
    fun getNotesTest() {
        notesLiveData.value = testNotesList
        `when`(notesRepository.getAllNotes()).thenReturn(notesLiveData)



        val result = notesRepository.getAllNotes()
        Assert.assertEquals(notesLiveData.value, result.value)
    }

    @Test
    fun updateNoteTest() {
        noteLiveData.value = testNotesList[0]

        `when`(notesRepository.updateNote(testNotesList[0])).thenReturn(noteLiveData)

        val result = notesRepository.updateNote(testNotesList[0])
        Assert.assertEquals(noteLiveData.value, result.value)
    }

    @Test
    fun addNoteTest() {
        noteLiveData.value = testNotesList[0]

        `when`(notesRepository.addNote(testNotesList[0])).thenReturn(noteLiveData)

        val result = notesRepository.addNote(testNotesList[0])
        Assert.assertEquals(noteLiveData.value, result.value)
    }

    @Test
    fun deleteNoteTest() {
        noteLiveData.value = testNotesList[0]

        `when`(notesRepository.deleteNote(testNotesList[0])).thenReturn(noteLiveData)

        val result = notesRepository.deleteNote(testNotesList[0])
        Assert.assertEquals(noteLiveData.value, result.value)
    }

}