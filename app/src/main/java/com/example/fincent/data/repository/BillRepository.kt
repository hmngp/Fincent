package com.example.fincent.data.repository

import com.example.fincent.data.local.dao.BillDao
import com.example.fincent.data.local.entity.BillEntity
import com.example.fincent.domain.model.Bill
import com.example.fincent.domain.model.ExpenseCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BillRepository @Inject constructor(
    private val billDao: BillDao
) {
    fun getAllBills(userId: String): Flow<List<Bill>> {
        return billDao.getAllBills(userId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    fun getUnpaidBills(userId: String): Flow<List<Bill>> {
        return billDao.getUnpaidBills(userId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    suspend fun insertBill(bill: Bill) {
        billDao.insertBill(bill.toEntity())
    }

    suspend fun updateBill(bill: Bill) {
        billDao.updateBill(bill.toEntity())
    }

    suspend fun deleteBill(bill: Bill) {
        billDao.deleteBill(bill.toEntity())
    }

    private fun BillEntity.toDomain(): Bill {
        return Bill(
            id = id,
            userId = userId,
            name = name,
            amount = amount,
            dueDate = dueDate,
            category = category,
            isPaid = isPaid,
            reminderDays = reminderDays
        )
    }

    private fun Bill.toEntity(): BillEntity {
        return BillEntity(
            id = id.ifEmpty { java.util.UUID.randomUUID().toString() },
            userId = userId,
            name = name,
            amount = amount,
            dueDate = dueDate,
            category = category,
            isPaid = isPaid,
            reminderDays = reminderDays
        )
    }
}
