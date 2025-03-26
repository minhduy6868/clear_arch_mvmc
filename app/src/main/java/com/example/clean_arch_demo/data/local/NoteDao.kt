package com.example.clean_arch_demo.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {  //khai báo các truy vấn
    @Upsert
    suspend fun upsertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Query("SELECT * FROM note")
    fun getAllNotes() : Flow<List<Note>>

    @Query("SELECT * FROM note WHERE noteName LIKE :query OR noteBody LIKE :query")
    fun searchNotes(query: String): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE noteId = :id")
    suspend fun getNoteById(id: Int): Note?

}