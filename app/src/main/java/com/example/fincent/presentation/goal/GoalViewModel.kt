package com.example.fincent.presentation.goal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fincent.data.repository.GoalRepository
import com.example.fincent.domain.model.FinancialGoal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalViewModel @Inject constructor(
    private val goalRepository: GoalRepository
) : ViewModel() {

    private val _goals = MutableStateFlow<List<FinancialGoal>>(emptyList())
    val goals: StateFlow<List<FinancialGoal>> = _goals.asStateFlow()

    private val _activeGoals = MutableStateFlow<List<FinancialGoal>>(emptyList())
    val activeGoals: StateFlow<List<FinancialGoal>> = _activeGoals.asStateFlow()

    fun loadGoals(userId: String) {
        viewModelScope.launch {
            goalRepository.getAllGoals(userId).collect { goalList ->
                _goals.value = goalList
            }
        }
    }

    fun loadActiveGoals(userId: String) {
        viewModelScope.launch {
            goalRepository.getActiveGoals(userId).collect { goalList ->
                _activeGoals.value = goalList
            }
        }
    }

    fun addGoal(goal: FinancialGoal) {
        viewModelScope.launch {
            goalRepository.insertGoal(goal)
        }
    }

    fun updateGoal(goal: FinancialGoal) {
        viewModelScope.launch {
            goalRepository.updateGoal(goal)
        }
    }

    fun deleteGoal(goal: FinancialGoal) {
        viewModelScope.launch {
            goalRepository.deleteGoal(goal)
        }
    }
}

