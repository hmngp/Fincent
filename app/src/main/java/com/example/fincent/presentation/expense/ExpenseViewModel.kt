package com.example.fincent.presentation.expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fincent.data.repository.ExpenseRepository
import com.example.fincent.domain.model.Expense
import com.example.fincent.domain.model.ExpenseCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository
) : ViewModel() {

    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())
    val expenses: StateFlow<List<Expense>> = _expenses.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _selectedCategory = MutableStateFlow<ExpenseCategory?>(null)
    val selectedCategory: StateFlow<ExpenseCategory?> = _selectedCategory.asStateFlow()

    fun loadExpenses(userId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            expenseRepository.getAllExpenses(userId).collect { expenseList ->
                _expenses.value = expenseList
                _isLoading.value = false
            }
        }
    }

    fun loadExpensesByDateRange(userId: String, startDate: Long, endDate: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            expenseRepository.getExpensesByDateRange(userId, startDate, endDate).collect { expenseList ->
                _expenses.value = expenseList
                _isLoading.value = false
            }
        }
    }

    fun filterByCategory(category: ExpenseCategory?) {
        _selectedCategory.value = category
    }

    fun addExpense(expense: Expense) {
        viewModelScope.launch {
            expenseRepository.insertExpense(expense)
        }
    }

    fun updateExpense(expense: Expense) {
        viewModelScope.launch {
            expenseRepository.updateExpense(expense)
        }
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            expenseRepository.deleteExpense(expense)
        }
    }

    suspend fun getTotalExpenseForMonth(userId: String): Double {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startOfMonth = calendar.timeInMillis

        calendar.add(Calendar.MONTH, 1)
        val endOfMonth = calendar.timeInMillis

        return expenseRepository.getTotalExpenseInRange(userId, startOfMonth, endOfMonth)
    }
}

