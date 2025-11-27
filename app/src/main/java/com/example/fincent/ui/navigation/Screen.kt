package com.example.fincent.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object EmailVerification : Screen("email_verification")
    object SetupProfile : Screen("setup_profile")
    
    // Main screens
    object Dashboard : Screen("dashboard")
    object Expenses : Screen("expenses")
    object Budget : Screen("budget")
    object Goals : Screen("goals")
    object Profile : Screen("profile")
    
    // Detail screens
    object AddExpense : Screen("add_expense?expenseId={expenseId}") {
        fun createRoute(expenseId: String? = null) = 
            if (expenseId != null) "add_expense?expenseId=$expenseId" else "add_expense"
    }
    object ExpenseDetail : Screen("expense_detail/{expenseId}") {
        fun createRoute(expenseId: String) = "expense_detail/$expenseId"
    }
    object AddBudget : Screen("add_budget")
    object BudgetDetail : Screen("budget_detail/{budgetId}") {
        fun createRoute(budgetId: String) = "budget_detail/$budgetId"
    }
    object AddGoal : Screen("add_goal")
    object GoalDetail : Screen("goal_detail/{goalId}") {
        fun createRoute(goalId: String) = "goal_detail/$goalId"
    }
    
    object AddBill : Screen("add_bill")
    object BillList : Screen("bill_list")
    
    // Feature screens
    object SplitExpense : Screen("split_expense")
    object StudentLoan : Screen("student_loan")
    object FinancialLiteracy : Screen("financial_literacy")
    object Gamification : Screen("gamification")
    object CampusDeals : Screen("campus_deals")
    object Settings : Screen("settings")
    object Privacy : Screen("privacy")
    object DataExport : Screen("data_export")
    object QuickEntry : Screen("quick_entry")
}

