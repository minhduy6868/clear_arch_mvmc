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

    init {
        viewModelScope.launch {
            useCase.getAllNotes().collect { noteList ->
                _notes.value = noteList
            }
        }
    }

    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            useCase.addNote(title, content)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            useCase.deleteNote(note)
        }
    }
}
