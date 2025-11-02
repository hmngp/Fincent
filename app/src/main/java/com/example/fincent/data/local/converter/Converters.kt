package com.example.fincent.data.local.converter

import androidx.room.TypeConverter
import com.example.fincent.domain.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromExpenseCategory(value: ExpenseCategory): String = value.name

    @TypeConverter
    fun toExpenseCategory(value: String): ExpenseCategory = ExpenseCategory.valueOf(value)

    @TypeConverter
    fun fromRecurringType(value: RecurringType?): String? = value?.name

    @TypeConverter
    fun toRecurringType(value: String?): RecurringType? = value?.let { RecurringType.valueOf(it) }

    @TypeConverter
    fun fromStringList(value: List<String>): String = gson.toJson(value)

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromSplitExpense(value: SplitExpense?): String? = value?.let { gson.toJson(it) }

    @TypeConverter
    fun toSplitExpense(value: String?): SplitExpense? = value?.let {
        gson.fromJson(it, SplitExpense::class.java)
    }

    @TypeConverter
    fun fromBudgetCategory(value: BudgetCategory): String = value.name

    @TypeConverter
    fun toBudgetCategory(value: String): BudgetCategory = BudgetCategory.valueOf(value)

    @TypeConverter
    fun fromBudgetType(value: BudgetType): String = value.name

    @TypeConverter
    fun toBudgetType(value: String): BudgetType = BudgetType.valueOf(value)

    @TypeConverter
    fun fromGoalCategory(value: GoalCategory): String = value.name

    @TypeConverter
    fun toGoalCategory(value: String): GoalCategory = GoalCategory.valueOf(value)

    @TypeConverter
    fun fromPriority(value: Priority): String = value.name

    @TypeConverter
    fun toPriority(value: String): Priority = Priority.valueOf(value)

    @TypeConverter
    fun fromBadgeList(value: List<Badge>): String = gson.toJson(value)

    @TypeConverter
    fun toBadgeList(value: String): List<Badge> {
        val listType = object : TypeToken<List<Badge>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromBadgeType(value: BadgeType): String = value.name

    @TypeConverter
    fun toBadgeType(value: String): BadgeType = BadgeType.valueOf(value)

    @TypeConverter
    fun fromChallengeType(value: ChallengeType): String = value.name

    @TypeConverter
    fun toChallengeType(value: String): ChallengeType = ChallengeType.valueOf(value)

    @TypeConverter
    fun fromArticleCategory(value: ArticleCategory): String = value.name

    @TypeConverter
    fun toArticleCategory(value: String): ArticleCategory = ArticleCategory.valueOf(value)
}

