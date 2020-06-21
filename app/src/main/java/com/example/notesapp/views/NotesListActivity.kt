package com.example.notesapp.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.NotesApplication
import com.example.notesapp.R
import com.example.notesapp.adapter.NotesClickListener
import com.example.notesapp.adapter.NotesListAdapter
import com.example.notesapp.databinding.ActivityNotesListBinding
import com.example.notesapp.db.entity.Note
import com.example.notesapp.di.factories.NotesListViewModelFactory
import com.example.notesapp.viewmodels.NotesListViewModel
import javax.inject.Inject

class NotesListActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: NotesListViewModelFactory

    private lateinit var viewModel: NotesListViewModel
    private lateinit var binding: ActivityNotesListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        performInjection()
        initViewModel()
        initBinding()
        initObservables()

    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)[NotesListViewModel::class.java]
    }

    private fun initBinding() {
        binding = ActivityNotesListBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.addNoteFb.setOnClickListener {
            openNoteEditor()
        }

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                if (swipeDir == ItemTouchHelper.LEFT) {
                    if (viewHolder is NotesListAdapter.ViewHolder) {
                        val noteForDeleting = viewHolder.binding.note
                        viewModel.onDelete(noteForDeleting)

                    }
                }
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.notesListRv)

        setContentView(binding.root)

    }

    private fun initObservables() {
        updateNotesList()
        CommonUI.observeDeletingNote(this, this, viewModel.deletedNoteLd, getString(R.string.was_deleted))
    }


    private fun updateNotesList() {
        viewModel.notes.observe(this, Observer {
            val adapter = NotesListAdapter(NotesClickListener {
                openNoteEditor(it)

            })
            binding.notesListRv.adapter = adapter
            adapter.submitList(it)
        })

    }

    private fun openNoteEditor() {
        val intent = Intent(this, NoteActivity::class.java)
        startActivity(intent)

    }
    private fun openNoteEditor(note: Note) {
        val intent = Intent(this, NoteActivity::class.java).apply {
            putExtra("note", note)
        }
        startActivity(intent)
    }

    private fun performInjection() {
        NotesApplication.instance.component.inject(this)
    }

    override fun onResume() {
        super.onResume()
        updateNotesList()
    }
}