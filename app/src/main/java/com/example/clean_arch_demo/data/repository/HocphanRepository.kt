package com.example.clean_arch_demo.data.repository

import com.example.clean_arch_demo.data.local.Hocphan
import com.example.clean_arch_demo.data.local.HocphanDao
import kotlinx.coroutines.flow.Flow

class HocphanRepository(private val hocphanDao: HocphanDao) {   // Quản lý dữ liệu
    fun getAllHocphan(): Flow<List<Hocphan>> = hocphanDao.getAllHocphan()

    suspend fun insertHocphan(hocphan: Hocphan) {
        hocphanDao.upsertHocphan(hocphan)
    }

    suspend fun updateHocphan(hocphan: Hocphan) {
        hocphanDao.updateHocphan(hocphan)
    }

    suspend fun deleteHocphan(hocphan: Hocphan) {
        hocphanDao.deleteHocphan(hocphan)
    }

    fun searchHocphan(query: String): Flow<List<Hocphan>> = hocphanDao.searchHocphan("%$query%")

    suspend fun getHocphanById(id: Int): Hocphan? = hocphanDao.getHocphanById(id)
}