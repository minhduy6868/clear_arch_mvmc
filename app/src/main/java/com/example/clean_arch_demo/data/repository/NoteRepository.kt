package com.example.clean_arch_demo.data.repository

import com.example.clean_arch_demo.data.local.Note
import com.example.clean_arch_demo.data.local.NoteDao
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {   // Quảng lí dữ liêu
    fun getAllNotes(): Flow<List<Note>> = noteDao.getAllNotes()

    suspend fun insertNote(note: Note) {
        noteDao.upsertNote(note)
    }
suspend fun updateNote(note: Note) {
    noteDao.updateNote(note)
}

    suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }

    fun searchNotes(query: String): Flow<List<Note>> = noteDao.searchNotes("%$query%")

    suspend fun getNoteById(id: Int): Note? = noteDao.getNoteById(id)
}
