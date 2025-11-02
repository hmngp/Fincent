package com.example.fincent.domain.model

data class Expense(
    val id: String = "",
    val userId: String = "",
    val amount: Double = 0.0,
    val category: ExpenseCategory = ExpenseCategory.OTHER,
    val description: String = "",
    val date: Long = System.currentTimeMillis(),
    val isRecurring: Boolean = false,
    val recurringType: RecurringType? = null,
    val receiptImageUrl: String? = null,
    val isSplit: Boolean = false,
    val splitDetails: SplitExpense? = null,
    val tags: List<String> = emptyList()
)

enum class ExpenseCategory {
    FOOD,
    TRANSPORTATION,
    ENTERTAINMENT,
    SHOPPING,
    BILLS,
    EDUCATION,
    HEALTHCARE,
    HOUSING,
    OTHER
}

enum class RecurringType {
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY
}

data class SplitExpense(
    val totalParticipants: Int = 0,
    val participants: List<Participant> = emptyList(),
    val splitType: SplitType = SplitType.EQUAL
)

data class Participant(
    val name: String = "",
    val email: String? = null,
    val amountOwed: Double = 0.0,
    val hasPaid: Boolean = false
)

enum class SplitType {
    EQUAL,
    PERCENTAGE,
    CUSTOM
}

