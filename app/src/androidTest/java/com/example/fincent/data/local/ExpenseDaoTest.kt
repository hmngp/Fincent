package com.example.fincent.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.fincent.data.local.dao.ExpenseDao
import com.example.fincent.data.local.entity.ExpenseEntity
import com.example.fincent.domain.model.ExpenseCategory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ExpenseDaoTest {
    private lateinit var expenseDao: ExpenseDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        expenseDao = db.expenseDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetExpense() = runBlocking {
        val expense = ExpenseEntity(
            id = "1",
            userId = "user1",
            amount = 100.0,
            category = ExpenseCategory.FOOD,
            description = "Pizza",
            date = System.currentTimeMillis(),
            isRecurring = false,
            recurringType = null,
            receiptImageUrl = null,
            isSplit = false,
            splitDetails = null,
            tags = emptyList()
        )
        expenseDao.insertExpense(expense)
        val allExpenses = expenseDao.getExpenses("user1").first()
        assertEquals(allExpenses[0].description, "Pizza")
    }
}
