package com.example.fincent.presentation.literacy

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Book
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fincent.domain.model.ArticleCategory
import com.example.fincent.domain.model.FinancialArticle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinancialLiteracyScreen(
    onNavigateBack: () -> Unit
) {
    // Sample articles
    val sampleArticles = remember {
        listOf(
            FinancialArticle(
                id = "1",
                title = "Student Budgeting Basics",
                content = "Learn the fundamentals of managing your finances as a student...",
                category = ArticleCategory.BUDGETING,
                readingTimeMinutes = 5
            ),
            FinancialArticle(
                id = "2",
                title = "How to Save on a Tight Budget",
                content = "Practical tips for saving money while living on a student budget...",
                category = ArticleCategory.SAVING,
                readingTimeMinutes = 7
            ),
            FinancialArticle(
                id = "3",
                title = "Managing Student Loans",
                content = "Everything you need to know about student loan repayment...",
                category = ArticleCategory.DEBT_MANAGEMENT,
                readingTimeMinutes = 10
            ),
            FinancialArticle(
                id = "4",
                title = "Part-Time Income Ideas",
                content = "Ways to earn extra money while studying...",
                category = ArticleCategory.INCOME_GENERATION,
                readingTimeMinutes = 6
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Financial Literacy") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    "Learn About Personal Finance",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(sampleArticles) { article ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Icon(
                            Icons.Default.Book,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                article.title,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row {
                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                                    )
                                ) {
                                    Text(
                                        article.category.name,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                        fontSize = 11.sp
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    "${article.readingTimeMinutes} min read",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

