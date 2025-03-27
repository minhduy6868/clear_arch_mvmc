package com.example.clean_arch_demo.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Maytinh( // Lớp này để khai báo bảng
    val tenmay: String,
    val soluong: Int,
    val dongia: Double,
    val loaimay: String,
    @PrimaryKey(autoGenerate = true)
    val mamay: Int = 0
)
