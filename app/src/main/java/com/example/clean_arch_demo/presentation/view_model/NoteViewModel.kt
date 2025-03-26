package com.example.clean_arch_demo.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clean_arch_demo.data.local.Note
import com.example.clean_arch_demo.domain.usecase.NoteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoteViewModel(private val useCase: NoteUseCase) : ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes = _notes.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _selectedNote = MutableStateFlow<Note?>(null)
    val selectedNote = _selectedNote.asStateFlow()

    private val _isEditing = MutableStateFlow(false)
    val isEditing = _isEditing.asStateFlow()

    init {
        loadNotes()
    }

    fun loadNotes() {
        viewModelScope.launch {
            if (_searchQuery.value.isEmpty()) {
                useCase.getAllNotes().collect { noteList ->
                    _notes.value = noteList
                }
            } else {
                useCase.searchNotes(_searchQuery.value).collect { noteList ->
                    _notes.value = noteList
                }
            }
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
        loadNotes()
    }

    fun selectNote(note: Note) {
        _selectedNote.value = note
        _isEditing.value = false
    }

    fun startEditing() {
        _isEditing.value = true
    }

    fun cancelEditing() {
        _isEditing.value = false
    }

    fun clearSelectedNote() {
        _selectedNote.value = null
        _isEditing.value = false
    }

    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            useCase.addNote(title, content)
            loadNotes()
        }
    }

    fun updateNote(updatedNote: Note) {
        viewModelScope.launch {
            useCase.updateNote(updatedNote.noteName, updatedNote.noteBody)
            _selectedNote.value = updatedNote
            _isEditing.value = false
            loadNotes()
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            useCase.deleteNote(note)
            if (_selectedNote.value?.noteId == note.noteId) {
                clearSelectedNote()
            }
            loadNotes()
        }
    }
}