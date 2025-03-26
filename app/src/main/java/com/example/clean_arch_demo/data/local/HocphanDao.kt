package com.example.clean_arch_demo.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface HocphanDao {  // Khai báo các truy vấn
    @Upsert
    suspend fun upsertHocphan(hocphan: Hocphan)

    @Delete
    suspend fun deleteHocphan(hocphan: Hocphan)

    @Update
    suspend fun updateHocphan(hocphan: Hocphan)

    @Query("SELECT * FROM hocphan")
    fun getAllHocphan(): Flow<List<Hocphan>>

    @Query("SELECT * FROM hocphan WHERE tenhocphan LIKE :query OR hocky LIKE :query")
    fun searchHocphan(query: String): Flow<List<Hocphan>>

    @Query("SELECT * FROM hocphan WHERE mahocphan = :id")
    suspend fun getHocphanById(id: Int): Hocphan?
}