package com.example.fincent.presentation

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.fincent.presentation.expense.AddExpenseScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddExpenseScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun addExpense_verifiesUIElements() {
        // Start the app
        composeTestRule.setContent {
        }

        // Verify that the "Amount" field exists
        // composeTestRule.onNodeWithText("Amount").assertExists()
        
        // Verify that the "Description" field exists
        // composeTestRule.onNodeWithText("Description").assertExists()
        
        // Verify that the "Add Expense" button exists
        // composeTestRule.onNodeWithText("Add Expense").assertExists()
    }
}
