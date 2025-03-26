package com.example.clean_arch_demo.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note( // Lớp này để khai báo bảng
    val noteName: String,
    val noteBody: String,
    @PrimaryKey(autoGenerate = true)
    val noteId: Int = 0
)
