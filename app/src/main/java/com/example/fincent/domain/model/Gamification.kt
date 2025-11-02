package com.example.fincent.domain.model

data class UserGamification(
    val userId: String = "",
    val level: Int = 1,
    val experiencePoints: Int = 0,
    val badges: List<Badge> = emptyList(),
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val lastActivityDate: Long = System.currentTimeMillis(),
    val completedChallenges: List<String> = emptyList()
)

data class Badge(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val iconName: String = "",
    val earnedAt: Long = System.currentTimeMillis(),
    val type: BadgeType = BadgeType.SAVINGS
)

enum class BadgeType {
    SAVINGS,
    BUDGETING,
    CONSISTENCY,
    GOALS,
    LEARNING
}

data class Challenge(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val targetValue: Double = 0.0,
    val currentValue: Double = 0.0,
    val rewardPoints: Int = 0,
    val startDate: Long = System.currentTimeMillis(),
    val endDate: Long = System.currentTimeMillis(),
    val isCompleted: Boolean = false,
    val type: ChallengeType = ChallengeType.WEEKLY_SAVINGS
)

enum class ChallengeType {
    WEEKLY_SAVINGS,
    NO_SPEND_DAY,
    BUDGET_GOAL,
    EXPENSE_TRACKING,
    LEARNING_MILESTONE
}

data class FinancialArticle(
    val id: String = "",
    val title: String = "",
    val content: String = "",
    val category: ArticleCategory = ArticleCategory.BUDGETING,
    val readingTimeMinutes: Int = 5,
    val imageUrl: String? = null,
    val isRead: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

enum class ArticleCategory {
    BUDGETING,
    SAVING,
    INVESTING,
    DEBT_MANAGEMENT,
    STUDENT_FINANCE,
    INCOME_GENERATION
}

