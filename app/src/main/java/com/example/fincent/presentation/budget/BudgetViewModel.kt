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
            budgetRepository.getActiveBudgets(userId).collect { budgetList ->
                _activeBudgets.value = budgetList
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
}

