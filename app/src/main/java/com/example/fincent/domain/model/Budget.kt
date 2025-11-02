package com.example.fincent.domain.model

data class Budget(
    val id: String = "",
    val userId: String = "",
    val name: String = "",
    val totalAmount: Double = 0.0,
    val spentAmount: Double = 0.0,
    val category: BudgetCategory = BudgetCategory.GENERAL,
    val startDate: Long = System.currentTimeMillis(),
    val endDate: Long = System.currentTimeMillis(),
    val isActive: Boolean = true,
    val budgetType: BudgetType = BudgetType.MONTHLY
)

enum class BudgetCategory {
    GENERAL,
    ACADEMIC,
    HOLIDAY,
    SEMESTER
}

enum class BudgetType {
    WEEKLY,
    MONTHLY,
    SEMESTER,
    YEARLY
}

data class Income(
    val id: String = "",
    val userId: String = "",
    val amount: Double = 0.0,
    val source: String = "",
    val date: Long = System.currentTimeMillis(),
    val isRegular: Boolean = false,
    val description: String = ""
)

data class Bill(
    val id: String = "",
    val userId: String = "",
    val name: String = "",
    val amount: Double = 0.0,
    val dueDate: Long = System.currentTimeMillis(),
    val category: ExpenseCategory = ExpenseCategory.BILLS,
    val isPaid: Boolean = false,
    val isRecurring: Boolean = false,
    val recurringType: RecurringType? = null,
    val reminderDays: Int = 3
)

