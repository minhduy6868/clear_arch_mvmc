package com.example.clean_arch_demo.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Hocphan( // Lớp này để khai báo bảng
    val tenhocphan: String,
    val sotinchi: Int,
    val hocky: String,
    @PrimaryKey(autoGenerate = true)
    val mahocphan: Int = 0
)
