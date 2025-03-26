package com.example.clean_arch_demo.domain.usecase

import com.example.clean_arch_demo.data.local.Hocphan
import com.example.clean_arch_demo.data.repository.HocphanRepository
import kotlinx.coroutines.flow.Flow

class HocphanUseCase(private val repository: HocphanRepository) {   // xử lý logic nghiệp vụ
    fun getAllHocPhan(): Flow<List<Hocphan>> = repository.getAllHocphan()

    suspend fun addHocPhan(tenhocphan: String, sotinchi: Int, hocky: String) {
        repository.insertHocphan(Hocphan(tenhocphan = tenhocphan, sotinchi = sotinchi, hocky = hocky))
    }

    suspend fun updateHocPhan(tenhocphan: String, sotinchi: Int, hocky: String) {
        repository.updateHocphan(Hocphan(tenhocphan = tenhocphan, sotinchi = sotinchi, hocky = hocky))
    }

    suspend fun deleteHocPhan(hocphan: Hocphan) {
        repository.deleteHocphan(hocphan)
    }

    fun searchHocPhan(query: String): Flow<List<Hocphan>> = repository.searchHocphan(query)

    suspend fun getHocPhanById(id: Int): Hocphan? = repository.getHocphanById(id)
}