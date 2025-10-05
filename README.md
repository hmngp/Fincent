# Fincent - Student Budgeting Android App

<div align="center">
  <img src="app/src/main/res/drawable/dashboard_screen.png" alt="Dashboard Screen" width="200"/>
  <img src="app/src/main/res/drawable/add_expense_screen.png" alt="Add Expense Screen" width="200"/>
</div>

## 📱 Overview

**Fincent** is a modern student budgeting Android application built with Kotlin and Jetpack Compose. The app helps students track their expenses, manage budgets, and stay on top of their financial goals with an intuitive and beautiful Material Design 3 interface.

## ✨ Features

### 🏠 Dashboard Screen
- **Weekly Summary Card**: Track total spending and remaining budget
- **Upcoming Bills**: View and manage upcoming bill payments
- **Recent Transactions**: Quick overview of recent expense history
- **Floating Action Button**: Easy access to add new expenses

### 💰 Add Expense Screen
- **Amount Input**: Enter expense amounts with validation
- **Description Field**: Add detailed descriptions for expenses
- **Category Selection**: Choose from predefined categories (Food, Transportation, Entertainment, Shopping, Bills, Other)
- **Date Picker**: Select or use current date for expenses
- **Split with Friends**: Future feature for shared expenses

## 🛠️ Technical Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM (Model-View-ViewModel)
- **Navigation**: Navigation Compose
- **Design System**: Material Design 3
- **Build System**: Gradle with Kotlin DSL
- **Minimum SDK**: API 24 (Android 7.0)
- **Target SDK**: API 36 (Android 14)

## 📁 Project Structure

```
app/src/main/java/com/example/fincent/
├── MainActivity.kt                 # Main activity with screen routing
├── ui/
│   ├── DashboardScreen.kt         # Dashboard with cards and summary
│   ├── AddExpenseScreen.kt        # Expense form with validation
│   └── theme/
│       ├── Color.kt               # App color scheme
│       ├── Theme.kt               # Material 3 theming
│       └── Type.kt                # Typography definitions
└── viewmodel/
    └── DashboardViewModel.kt      # State management for dashboard
```

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or later
- JDK 11 or later
- Android SDK with API 24+
- Android Emulator or physical device

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/hmngp/Fincent.git
   cd Fincent
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned Fincent directory

3. **Sync Project**
   - Android Studio will automatically sync Gradle files
   - Wait for the sync to complete

4. **Run the App**
   - Connect an Android device or start an emulator
   - Click the "Run" button or press `Shift + F10`
   - The app will build and install on your device

## 📱 Screenshots

### Dashboard Screen
The main dashboard provides a comprehensive overview of your financial status:
- Weekly spending summary with budget tracking
- Upcoming bills with due dates and amounts
- Recent transaction history with descriptions and amounts
- Quick access floating action button for adding expenses

### Add Expense Screen
A clean and intuitive form for recording new expenses:
- Amount input with proper validation
- Description field for expense details
- Category dropdown with common expense types
- Date selection with current date as default
- Action buttons for saving or splitting expenses

## 🎨 Design Features

- **Material Design 3**: Modern, adaptive design system
- **Responsive Layout**: Optimized for various screen sizes
- **Card-based UI**: Clean, organized information display
- **Consistent Theming**: Cohesive color scheme and typography
- **Accessibility**: Built with accessibility best practices

## 🔧 Dependencies

The project uses the following key dependencies:

```kotlin
// Core Android
implementation("androidx.core:core-ktx:1.17.0")
implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.9.4")
implementation("androidx.activity:activity-compose:1.11.0")

// Compose BOM
implementation(platform("androidx.compose:compose-bom:2024.09.00"))
implementation("androidx.compose.ui:ui")
implementation("androidx.compose.ui:ui-graphics")
implementation("androidx.compose.ui:ui-tooling-preview")
implementation("androidx.compose.material3:material3")

// Navigation
implementation("androidx.navigation:navigation-compose:2.8.4")
```

## 🚧 Future Enhancements

- [ ] **Data Persistence**: Room database integration
- [ ] **User Authentication**: Login and registration system
- [ ] **Budget Management**: Set and track monthly budgets
- [ ] **Expense Categories**: Custom category creation
- [ ] **Reports & Analytics**: Spending insights and trends
- [ ] **Split Expenses**: Share expenses with friends
- [ ] **Notifications**: Bill reminders and budget alerts
- [ ] **Dark Mode**: Theme switching capability
- [ ] **Export Data**: CSV/PDF export functionality

## 🤝 Contributing

We welcome contributions to Fincent! Here's how you can help:

1. **Fork the repository**
2. **Create a feature branch** (`git checkout -b feature/amazing-feature`)
3. **Commit your changes** (`git commit -m 'Add some amazing feature'`)
4. **Push to the branch** (`git push origin feature/amazing-feature`)
5. **Open a Pull Request**

### Development Guidelines
- Follow Kotlin coding conventions
- Use meaningful commit messages
- Add comments for complex logic
- Test on multiple screen sizes
- Ensure accessibility compliance

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Hemang Patel**
- GitHub: [@hmngp](https://github.com/hmngp)
- Email: hemangbpatel1109@gmail.com

## 🙏 Acknowledgments

- Material Design 3 guidelines for UI/UX inspiration
- Android Developer documentation for best practices
- Jetpack Compose team for the amazing UI framework
- Open source community for various libraries and tools

## 📞 Support

If you encounter any issues or have questions:

1. Check the [Issues](https://github.com/hmngp/Fincent/issues) page
2. Create a new issue with detailed description
3. Contact the maintainer via email

---

<div align="center">
  <p>Made with ❤️ for students who want to take control of their finances</p>
  <p>⭐ Star this repository if you found it helpful!</p>
</div>
