package com.example.clean_arch_demo.data.repository

import com.example.clean_arch_demo.data.local.Maytinh
import com.example.clean_arch_demo.data.local.MaytinhDao
import kotlinx.coroutines.flow.Flow

class MaytinhRepository(private val maytinhDao: MaytinhDao) {   // Quản lý dữ liệu
    fun getAllMaytinh(): Flow<List<Maytinh>> = maytinhDao.getAllMaytinh()

    suspend fun insertMaytinh(maytinh: Maytinh) {
        maytinhDao.upsertMaytinh(maytinh)
    }

    suspend fun updateMaytinh(maytinh: Maytinh) {
        maytinhDao.updateMaytinh(maytinh)
    }

    suspend fun deleteMaytinh(maytinh: Maytinh) {
        maytinhDao.deleteMaytinh(maytinh)
    }

    fun searchMaytinh(query: String): Flow<List<Maytinh>> = maytinhDao.searchMaytinh("%$query%")

    suspend fun getHocphanById(id: Int): Maytinh? = maytinhDao.getHocphanById(id)
}