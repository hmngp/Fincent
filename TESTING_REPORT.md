# Testing Strategy & Execution Report

## 1. Database Testing
**Definition:** Database testing verifies the integrity, data mapping, and ACID properties (Atomicity, Consistency, Isolation, Durability) of the database. In Android (Room), this typically involves:
*   **Unit Tests:** Testing DAOs (Data Access Objects) to ensure queries return expected results.
*   **Integration Tests:** Verifying that the database interacts correctly with the Repository and ViewModel.

**Status in Fincent:**
*   **Manual Verification (Black Box Testing):** We have performed extensive manual testing of the database operations.
    *   *Create:* Verified that adding Expenses, Budgets, and Goals persists data across app restarts.
    *   *Read:* Verified that the Dashboard correctly aggregates and displays data from multiple tables (Expenses, Budgets, Bills).
    *   *Update:* Verified that the "Spend from Budget" feature correctly updates the `spentAmount` in the `BudgetEntity`.
    *   *Delete:* Verified that items can be removed from lists.
*   **Schema Validation:** The Room library performs compile-time checks on SQL queries, ensuring that our table structures and relationships are valid.

## 2. User Testing (UAT - User Acceptance Testing)
**Definition:** User testing involves real users interacting with the application to validate usability, workflow efficiency, and feature correctness.

**Status in Fincent:**
*   **Developer/Alpha Testing:** As the primary developer, I have acted as the alpha tester, simulating real-world student scenarios (e.g., splitting a pizza bill, setting a monthly grocery budget).
*   **Scenario-Based Testing:** We validated specific user stories:
    *   *Scenario 1:* "As a student, I want to split a bill with 3 friends." -> Verified using the Split Calculator dialog.
    *   *Scenario 2:* "As a student, I want to see my upcoming bills." -> Verified using the new Dashboard Bills section.
*   **Usability Improvements:** Based on this testing, we improved the UI (e.g., adding the Date Picker for better accuracy, adding the "Spend from Budget" checkbox to automate manual tasks).

## 3. Summary for Professor
"While we have not implemented an automated test suite (JUnit/Espresso) due to time constraints, we have conducted rigorous **Manual Integration Testing** and **Scenario-Based User Testing**.
*   **Database Integrity:** Verified through persistent storage checks and complex cross-table updates (Expense -> Budget).
*   **User Workflows:** Validated against core student use cases (Budgeting, Splitting Bills, Tracking Goals).
The application is stable and functional for the defined requirements."
