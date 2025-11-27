# Fincent - Personal Finance Management App

Fincent is a comprehensive mobile application designed to help students and young professionals manage their finances effectively. Built with modern Android development practices, it offers features for tracking expenses, managing budgets, setting financial goals, and monitoring upcoming bills.

## Features

### Dashboard
- Provides a quick overview of financial health.
- Displays a weekly summary of spending versus remaining budget.
- Lists upcoming unpaid bills.
- Shows recent transactions for quick reference.

### Expense Management
- **Add Expenses**: Users can log expenses with amount, description, category, and date.
- **Edit & Delete**: Full CRUD capabilities for expense records.
- **Spend from Budget**: Option to directly deduct expense amounts from specific active budgets.
- **Split with Friends**: A utility to calculate shared costs among a group (UI implementation).

### Budgeting
- **Create Budgets**: Users can set up budgets for specific categories (e.g., Food, Transport) or general spending.
- **Budget Types**: Supports Weekly, Monthly, Semester, and Yearly budget cycles.
- **Tracking**: Visual indicators of spent amount versus total allocated budget.
- **Validation**: Ensures valid data entry for budget creation.

### Financial Goals
- **Set Goals**: Users can define savings goals with target amounts and priorities.
- **Progress Tracking**: Monitor progress towards financial objectives.
- **Prioritization**: Categorize goals by priority (High, Medium, Low).

### Bill Reminders
- **Track Bills**: Keep track of recurring bills and due dates.
- **Status**: Mark bills as paid or unpaid.
- **Dashboard Integration**: Upcoming bills are prominently displayed on the dashboard.

## Technical Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose (Material Design 3)
- **Architecture**: MVVM (Model-View-ViewModel)
- **Dependency Injection**: Dagger Hilt
- **Local Database**: Room Database
- **Asynchronous Programming**: Kotlin Coroutines & Flow
- **Navigation**: Jetpack Compose Navigation

## Setup and Installation

1. Clone the repository.
2. Open the project in Android Studio (Ladybug or newer recommended).
3. Sync the project with Gradle files.
4. Run the application on an Android Emulator or physical device.

## Note on Authentication

The application currently operates in a "Guest Mode" (using a demo-user) to allow full functionality testing without requiring Firebase Authentication setup. All data is persisted locally on the device.
