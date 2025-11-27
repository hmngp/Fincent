package com.example.fincent.presentation.budget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fincent.data.repository.BudgetRepository
import com.example.fincent.domain.model.Budget
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val budgetRepository: BudgetRepository
) : ViewModel() {

    private val _budgets = MutableStateFlow<List<Budget>>(emptyList())
    val budgets: StateFlow<List<Budget>> = _budgets.asStateFlow()

    private val _activeBudgets = MutableStateFlow<List<Budget>>(emptyList())
    val activeBudgets: StateFlow<List<Budget>> = _activeBudgets.asStateFlow()

    fun loadBudgets(userId: String) {
        viewModelScope.launch {
            budgetRepository.getAllBudgets(userId).collect { budgetList ->
                _budgets.value = budgetList
            }
        }
    }

    fun loadActiveBudgets(userId: String) {
        viewModelScope.launch {
            budgetRepository.getAllBudgets(userId).collect { budgetList ->
                _activeBudgets.value = budgetList.filter { it.isActive }
            }
        }
    }

    fun addBudget(budget: Budget) {
        viewModelScope.launch {
            budgetRepository.insertBudget(budget)
        }
    }

    fun updateBudget(budget: Budget) {
        viewModelScope.launch {
            budgetRepository.updateBudget(budget)
        }
    }

    fun deleteBudget(budget: Budget) {
        viewModelScope.launch {
            budgetRepository.deleteBudget(budget)
        }
    }

    fun deductExpenseFromBudget(category: com.example.fincent.domain.model.ExpenseCategory, amount: Double, budgetId: String? = null) {
        viewModelScope.launch {
            val currentBudgets = if (_activeBudgets.value.isNotEmpty()) _activeBudgets.value else _budgets.value.filter { it.isActive }
            
            val budgetToUpdate = if (budgetId != null) {
                currentBudgets.find { it.id == budgetId }
            } else {
                currentBudgets.find { 
                    it.category.name == category.name 
                } ?: currentBudgets.find { it.category == com.example.fincent.domain.model.BudgetCategory.GENERAL }
                ?: currentBudgets.firstOrNull()
            }

            budgetToUpdate?.let { budget ->
                val updatedBudget = budget.copy(spentAmount = budget.spentAmount + amount)
                budgetRepository.updateBudget(updatedBudget)
            }
        }
    }
}

