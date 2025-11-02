package com.example.fincent.domain.model

data class FinancialGoal(
    val id: String = "",
    val userId: String = "",
    val name: String = "",
    val targetAmount: Double = 0.0,
    val currentAmount: Double = 0.0,
    val deadline: Long = System.currentTimeMillis(),
    val category: GoalCategory = GoalCategory.SAVINGS,
    val priority: Priority = Priority.MEDIUM,
    val description: String = "",
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
) {
    val progress: Float
        get() = if (targetAmount > 0) (currentAmount / targetAmount).toFloat() else 0f
}

enum class GoalCategory {
    SAVINGS,
    EMERGENCY_FUND,
    EDUCATION,
    TRAVEL,
    GADGET,
    OTHER
}

enum class Priority {
    LOW,
    MEDIUM,
    HIGH
}

data class StudentLoan(
    val id: String = "",
    val userId: String = "",
    val loanName: String = "",
    val totalAmount: Double = 0.0,
    val remainingAmount: Double = 0.0,
    val interestRate: Double = 0.0,
    val monthlyPayment: Double = 0.0,
    val startDate: Long = System.currentTimeMillis(),
    val endDate: Long = System.currentTimeMillis(),
    val lender: String = "",
    val description: String = ""
) {
    val progress: Float
        get() = if (totalAmount > 0) ((totalAmount - remainingAmount) / totalAmount).toFloat() else 0f
}

