package com.example.clean_arch_demo.domain.usecase

import com.example.clean_arch_demo.data.local.Maytinh
import com.example.clean_arch_demo.data.repository.MaytinhRepository
import kotlinx.coroutines.flow.Flow

class MaytinhUseCase(private val repository: MaytinhRepository) {   // xử lý logic nghiệp vụ
    fun getAllMaytinh(): Flow<List<Maytinh>> = repository.getAllMaytinh()

    suspend fun addMaytinh(tenmay: String, dongia: Double, loaimay: String, soluong: Int) {
        repository.insertMaytinh(Maytinh(tenmay = tenmay, dongia = dongia, loaimay = loaimay, soluong = soluong))
    }

    suspend fun updateMaytinh(tenmay: String, dongia: Double, loaimay: String, soluong: Int) {
        repository.updateMaytinh(Maytinh(tenmay = tenmay, dongia = dongia, loaimay = loaimay, soluong = soluong))
    }

    suspend fun deleteMaytinh(hocphan: Maytinh) {
        repository.deleteMaytinh(hocphan)
    }

    fun searchMaytinh(query: String): Flow<List<Maytinh>> = repository.searchMaytinh(query)

    suspend fun getHocPhanById(id: Int): Maytinh? = repository.getHocphanById(id)
}