# Fincent: Technical Implementation Report

## 1. Project Architecture & Tech Stack
This project follows the **MVVM (Model-View-ViewModel)** architectural pattern to ensure separation of concerns and testability.
*   **UI**: Jetpack Compose (Declarative UI).
*   **Dependency Injection**: Hilt (Dagger).
*   **Local Database**: Room Persistence Library (SQLite abstraction).
*   **Concurrency**: Kotlin Coroutines & Flow.
*   **State Management**: `StateFlow` and Compose `State`.

---

## 2. Feature Implementation Details

### Feature A: Enhanced Expense Entry with Smart Features
**Objective:** Allow users to backdate expenses, split costs, and automatically update related budgets.

**Technical Implementation:**
*   **Date Selection:** Integrated the native `android.app.DatePickerDialog` wrapped in a Composable `LaunchedEffect` or click listener. The selected timestamp is stored in a `mutableStateOf` and formatted using `SimpleDateFormat`.
*   **Split Calculator:** Implemented a local calculation logic within the `AddExpenseScreen`. It uses a simple algorithm: `Total / Count` to derive per-person share. This is currently a UI-level calculation.
*   **Budget Deduction:** This is a cross-feature interaction. When the "Spend from Budget" checkbox is active, the `AddExpenseScreen` triggers an action in the `BudgetViewModel` to update the corresponding budget entity.

**Key Code Snippet (`AddExpenseScreen.kt` & `BudgetViewModel.kt`):**
```kotlin
// UI: Date Picker Integration
val datePickerDialog = android.app.DatePickerDialog(
    context,
    { _, year, month, dayOfMonth ->
        calendar.set(year, month, dayOfMonth)
        selectedDate = calendar.timeInMillis
    },
    // ... current date params
)

// Logic: Deducting from Budget (BudgetViewModel)
fun deductExpenseFromBudget(category: ExpenseCategory, amount: Double) {
    viewModelScope.launch {
        // 1. Filter for active budgets
        val currentBudgets = if (_activeBudgets.value.isNotEmpty()) 
            _activeBudgets.value 
        else 
            _budgets.value.filter { it.isActive }
        
        // 2. Find matching category OR fallback to General OR fallback to first active
        val budgetToUpdate = currentBudgets.find { it.category.name == category.name } 
            ?: currentBudgets.find { it.category == BudgetCategory.GENERAL }
            ?: currentBudgets.firstOrNull()

        // 3. Update database
        budgetToUpdate?.let { budget ->
            val updatedBudget = budget.copy(spentAmount = budget.spentAmount + amount)
            budgetRepository.updateBudget(updatedBudget)
        }
    }
}
```

### Feature B: Bill Management System
**Objective:** Track upcoming bills and display them on the dashboard.

**Technical Implementation:**
*   **Database Layer:** Created a new `BillEntity` annotated with `@Entity` and a `BillDao` with queries to fetch unpaid bills ordered by due date.
*   **Repository Pattern:** `BillRepository` acts as the single source of truth, exposing `Flow<List<Bill>>` to the ViewModel.
*   **Reactive UI:** The `DashboardScreen` observes `billViewModel.unpaidBills`. Any change in the database (e.g., marking a bill as paid) automatically triggers a UI recomposition via the Flow collector.

**Key Code Snippet (`BillDao.kt`):**
```kotlin
@Dao
interface BillDao {
    // Query to fetch only unpaid bills, sorted by nearest due date
    @Query("SELECT * FROM bills WHERE userId = :userId AND isPaid = 0 ORDER BY dueDate ASC")
    fun getUnpaidBills(userId: String): Flow<List<BillEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBill(bill: BillEntity)
}
```

### Feature C: Dashboard Analytics
**Objective:** Provide a real-time summary of weekly spending and budget health.

**Technical Implementation:**
*   **Data Aggregation:** The `DashboardScreen` acts as a consumer for multiple data streams (`ExpenseViewModel`, `BudgetViewModel`, `BillViewModel`).
*   **In-Memory Filtering:** To calculate "Weekly Spending", I filter the full list of expenses based on a timestamp calculated via `java.util.Calendar`. This avoids complex SQL date queries and allows for flexible time-window adjustments on the client side.
*   **Dynamic Calculation:** "Budget Left" is calculated dynamically: `Total Active Budget Amount - Total Monthly Expenses`.

**Key Code Snippet (`DashboardScreen.kt`):**
```kotlin
// Calculating Weekly Spend
val totalExpense = expenses
    .filter { it.date >= getStartOfWeek() } // Filter by timestamp
    .sumOf { it.amount }

// Calculating Budget Left
val totalBudget = budgets.sumOf { it.totalAmount }
val totalSpentMonth = expenses
    .filter { it.date >= getStartOfMonth() }
    .sumOf { it.amount }
val budgetLeft = (totalBudget - totalSpentMonth).coerceAtLeast(0.0)
```

## 3. Challenges & Solutions
*   **Challenge:** The "Spend from Budget" feature required updating a separate table (`budgets`) based on an action in the Expense screen.
*   **Solution:** I injected `BudgetViewModel` into `AddExpenseScreen`. This allowed the screen to orchestrate the transaction: saving the expense via `ExpenseViewModel` and concurrently updating the budget via `BudgetViewModel`.
*   **Challenge:** Room Database queries for boolean values (`isActive = 1`) were occasionally returning inconsistent results during rapid updates.
*   **Solution:** I shifted the filtering logic to the ViewModel layer (`filter { it.isActive }`). We fetch *all* budgets and filter them in memory using Kotlin's collection operators, ensuring the UI always reflects the true state of the data.
