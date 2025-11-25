package com.example.fincent.data.local.dao

import androidx.room.*
import com.example.fincent.data.local.entity.BillEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BillDao {
    @Query("SELECT * FROM bills WHERE userId = :userId ORDER BY dueDate ASC")
    fun getAllBills(userId: String): Flow<List<BillEntity>>

    @Query("SELECT * FROM bills WHERE userId = :userId AND isPaid = 0 ORDER BY dueDate ASC")
    fun getUnpaidBills(userId: String): Flow<List<BillEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBill(bill: BillEntity)

    @Update
    suspend fun updateBill(bill: BillEntity)

    @Delete
    suspend fun deleteBill(bill: BillEntity)
}
