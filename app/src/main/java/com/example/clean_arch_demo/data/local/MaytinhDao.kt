package com.example.clean_arch_demo.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface MaytinhDao {  // Khai báo các truy vấn
    @Upsert
    suspend fun upsertMaytinh(maytinh: Maytinh)

    @Delete
    suspend fun deleteMaytinh(maytinh: Maytinh)

    @Update
    suspend fun updateMaytinh(maytinh: Maytinh)

    @Query("SELECT * FROM maytinh")
    fun getAllMaytinh(): Flow<List<Maytinh>>

    @Query("SELECT * FROM maytinh WHERE tenmay LIKE :query OR loaimay LIKE :query")
    fun searchMaytinh(query: String): Flow<List<Maytinh>>

    @Query("SELECT * FROM maytinh WHERE mamay = :id")
    suspend fun getHocphanById(id: Int): Maytinh?
}