# Fincent - Smart Student Budgeting App 💰

<div align="center">
  <h3>Your Personal Finance Companion for Student Life</h3>
  <p>Built with Kotlin, Jetpack Compose, and Modern Android Architecture</p>
</div>

---

## 🎯 Project Status: **MVP COMPLETE & FUNCTIONAL**

Fincent is a comprehensive student budgeting Android application with a working MVP that includes authentication, expense tracking, budget management, goal setting, and more!

---

## ✨ Implemented Features

### 🔐 **Authentication & User Management**
- [x] Firebase Authentication with email/password
- [x] Email verification system
- [x] User profile management
- [x] Password reset functionality
- [x] Secure sign-in/sign-out

### 💸 **Expense Management**
- [x] Add, edit, and delete expenses
- [x] Category-based expense tracking
- [x] Date range filtering
- [x] Monthly expense summaries
- [x] Comprehensive expense list view

### 📊 **Budget Tracking**
- [x] Create and manage budgets
- [x] Budget progress visualization
- [x] Active budget monitoring
- [x] Budget vs. actual spending comparison
- [x] Multiple budget types (Weekly, Monthly, Semester, Yearly)

### 🎯 **Financial Goals**
- [x] Set savings goals with deadlines
- [x] Track goal progress
- [x] Priority-based goal management
- [x] Visual progress indicators
- [x] Goal categories (Savings, Emergency Fund, Education, Travel, etc.)

### 📱 **Modern UI & Navigation**
- [x] Material Design 3 components
- [x] Bottom navigation with 5 main tabs
- [x] Responsive layouts
- [x] Dark/Light theme support (Material3)
- [x] Intuitive user experience

### 🔔 **Notifications & Reminders**
- [x] WorkManager integration
- [x] Bill reminder notifications
- [x] Weekly financial report generation
- [x] Budget alert system
- [x] Notification channels

### 📄 **Data Management**
- [x] JSON data export
- [x] CSV report generation
- [x] Data backup functionality
- [x] Import/restore capabilities

### 📚 **Financial Literacy**
- [x] Educational content viewer
- [x] Financial tips and articles
- [x] Reading time estimates
- [x] Category-based content

### 👤 **Profile Management**
- [x] User profile display
- [x] University and course information
- [x] Email verification status
- [x] Account settings
- [x] Sign out functionality

---

## 🛠️ Technical Stack

### **Core Technologies**
- **Language**: Kotlin 2.0.21
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM (Model-View-ViewModel)
- **Dependency Injection**: Hilt
- **Navigation**: Navigation Compose

### **Database & Storage**
- **Local Database**: Room 2.6.1
- **Preferences**: DataStore
- **Firebase**: Authentication & Firestore

### **Background Processing**
- **WorkManager**: 2.9.1 for scheduled tasks
- **Notifications**: Android Notification API

### **Additional Libraries**
- **Image Loading**: Coil 2.5.0
- **Network**: Retrofit 2.9.0 & OkHttp 4.12.0
- **JSON**: Gson 2.10.1
- **Permissions**: Accompanist Permissions 0.34.0

---

## 📁 Project Architecture

```
com.example.fincent/
├── data/
│   ├── local/
│   │   ├── entity/          # Room entities
│   │   ├── dao/             # Data Access Objects
│   │   ├── converter/       # Type converters
│   │   └── AppDatabase.kt
│   └── repository/          # Repository implementations
│
├── domain/
│   └── model/               # Domain models
│
├── presentation/
│   ├── auth/                # Authentication screens
│   ├── dashboard/           # Dashboard screen
│   ├── expense/             # Expense management
│   ├── budget/              # Budget tracking
│   ├── goal/                # Goal setting
│   ├── profile/             # User profile
│   ├── literacy/            # Financial literacy
│   └── main/                # Main screen with bottom nav
│
├── ui/
│   ├── navigation/          # Navigation setup
│   └── theme/               # Material Design 3 theme
│
├── worker/                  # Background workers
├── util/                    # Utility classes
├── di/                      # Hilt modules
└── FincentApp.kt           # Application class
```

---

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

3. **Configure Firebase** (Optional for full functionality)
   - Create a Firebase project at [Firebase Console](https://console.firebase.google.com/)
   - Download `google-services.json`
   - Place it in `app/` directory

4. **Sync Project**
   - Android Studio will automatically sync Gradle files
   - Wait for the sync to complete

5. **Run the App**
   - Connect an Android device or start an emulator
   - Click the "Run" button or press `Shift + F10`
   - The app will build and install on your device

---

## 📸 Screenshots

### Authentication Flow
- Modern login and signup screens
- Email verification
- Password reset

### Main Dashboard
- Financial overview
- Recent expenses
- Budget summary
- Quick actions

### Feature Screens
- Expense list with filtering
- Budget tracking with progress
- Goal management
- User profile

---

## 🎨 Design Features

- **Material Design 3**: Latest design system with dynamic color
- **Responsive Layout**: Optimized for various screen sizes
- **Card-based UI**: Clean, organized information display
- **Progress Indicators**: Visual feedback for budgets and goals
- **Consistent Theming**: Cohesive color scheme and typography
- **Accessibility**: Built with accessibility best practices

---

## 📊 Database Schema

### Main Tables
- **expenses**: User expenses with categories and dates
- **budgets**: Budget tracking with progress
- **goals**: Financial goals with targets and progress

### Entity Relationships
- User → Expenses (One-to-Many)
- User → Budgets (One-to-Many)
- User → Goals (One-to-Many)

---

## 🔧 Development Features

### Clean Architecture
```
UI Layer (Compose) → ViewModel → Repository → Data Source (Room/Firebase)
```

### Dependency Injection
- All dependencies managed through Hilt
- Scoped providers for singletons
- ViewModels injected automatically

### State Management
- StateFlow for reactive UI updates
- Loading states and error handling
- Coroutines for async operations

---

## 🌟 Upcoming Features

See [FEATURE_IMPLEMENTATION_GUIDE.md](FEATURE_IMPLEMENTATION_GUIDE.md) for detailed implementation guides for:

- [ ] **Bill Splitting**: Share expenses with friends
- [ ] **Student Loan Tracker**: Manage and track student debt
- [ ] **Semester Budgeting**: Academic vs. holiday budget planning
- [ ] **Irregular Income**: Track variable income sources
- [ ] **Gamification**: Badges, challenges, and rewards
- [ ] **Quick Entry**: Voice and photo receipt capture
- [ ] **Campus Deals**: University-specific discounts
- [ ] **Enhanced Privacy**: Advanced privacy settings

---

## 🤝 Contributing

We welcome contributions to Fincent! Here's how you can help:

1. **Fork the repository**
2. **Create a feature branch** (`git checkout -b feature/amazing-feature`)
3. **Commit your changes** (`git commit -m 'feat: add amazing feature'`)
4. **Push to the branch** (`git push origin feature/amazing-feature`)
5. **Open a Pull Request**

### Development Guidelines
- Follow Kotlin coding conventions
- Use meaningful commit messages (Conventional Commits)
- Add comments for complex logic
- Test on multiple screen sizes
- Ensure accessibility compliance

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 👨‍💻 Author

**Hemang Patel**
- GitHub: [@hmngp](https://github.com/hmngp)
- Email: hemangbpatel1109@gmail.com
- Institution: James Cook University

---

## 🙏 Acknowledgments

- Material Design 3 guidelines for UI/UX inspiration
- Android Developer documentation for best practices
- Jetpack Compose team for the amazing UI framework
- Firebase team for authentication and cloud services
- Open source community for various libraries and tools

---

## 📞 Support

If you encounter any issues or have questions:

1. Check the [Feature Implementation Guide](FEATURE_IMPLEMENTATION_GUIDE.md)
2. Review [Implementation Progress](IMPLEMENTATION_PROGRESS.md)
3. Create an issue on [GitHub Issues](https://github.com/hmngp/Fincent/issues)
4. Contact the maintainer via email

---

## 📈 Project Stats

- **Languages**: Kotlin 100%
- **Architecture**: Clean Architecture with MVVM
- **UI**: Jetpack Compose
- **Database**: Room + Firebase Firestore
- **Min SDK**: API 24 (Android 7.0)
- **Target SDK**: API 36 (Android 14)

---

<div align="center">
  <p>Made with ❤️ for students who want to take control of their finances</p>
  <p>⭐ Star this repository if you found it helpful!</p>
  <p><strong>Version 1.0 - MVP Complete</strong></p>
</div>
