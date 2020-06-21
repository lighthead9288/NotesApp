package com.example.notesapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.notesapp.db.entity.Note
import com.example.notesapp.repository.NotesRepository
import com.example.notesapp.viewmodels.NotesListViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith

import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExtendWith(InstantExecutorExtension::class)
class NotesListViewModelUnitTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var notesRepository: NotesRepository

    lateinit var notesListViewModel: NotesListViewModel

    var notesLiveData = MutableLiveData<List<Note>>()

    var deletedNoteLiveData = MutableLiveData<Note>()

    private var testNotesList = mutableListOf<Note>()


    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        notesListViewModel = NotesListViewModel(notesRepository)

        testNotesList = arrayListOf(Note(id = 0, name = "Test 0", description = "Description test 0"), Note(id=1, name="Test 1", description = "Description test 1"))

    }

    @Test
    fun getNotes() {
        notesLiveData.value = testNotesList

        `when`(notesRepository.getAllNotes()).thenReturn(notesLiveData)

        notesListViewModel.onGetNotes()

        Assert.assertEquals(2, notesListViewModel.notes.value?.size)

        for (i in testNotesList.indices) {
            Assert.assertEquals(testNotesList[i], notesListViewModel.notes.value?.get(i))
        }

    }

    @Test
    fun deleteNote() {
        val noteForDeleting = testNotesList[0]
        CommonTest.onDeleteTest(noteForDeleting, notesListViewModel, notesRepository, deletedNoteLiveData, testNotesList)

    }



}