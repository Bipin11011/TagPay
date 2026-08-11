package com.example.tagpay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tagpay.ui.theme.TagPayTheme

class MainActivity : ComponentActivity() {
    val appName="TagPay"
    var transactionCount=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TagPayTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
class Transaction(val merchant: String, val amount: Double, val category: String) {

    init {
        require(amount > 0) { "Amount must be positive" }
        require(merchant.isNotBlank()) { "Merchant cannot be blank" }
    }

    fun addAmount(extra: Double): Transaction {
        require(extra > 0) { "Extra amount must be positive" }
        return Transaction(merchant, amount + extra, category)
    }
}
fun categorize(merchant: String): String {
    return when {

        merchant.contains("Swiggy") -> "Food"
        merchant.contains("Uber") -> "Transport"
        else -> "Uncategorized"
    }
}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val transactions = listOf(
        Transaction(merchant = "Swiggy", amount = 450.0, category = categorize("Swiggy")),
        Transaction(merchant = "Uber", amount = 220.0, category = categorize("Uber")),
        Transaction(merchant = "Random Shop", amount = 100.0, category = categorize(""))
    )

    val original = Transaction("Amazon", 500.0, "Shopping")
    val updated = original.addAmount(200.0)

    var displayText = "Hello $name!\n"
    for (transaction in transactions) {
        displayText += "${transaction.merchant}: ₹${transaction.amount} - ${transaction.category}\n"
    }

    val totalAmount = transactions.sumOf { it.amount }
    displayText += " total amount: $totalAmount"

    val foodTransactions = transactions.filter { it.category == "Food" }
    val totalFoodSpent = foodTransactions.sumOf { it.amount }
    displayText += "\nTotal spent on Food: ₹$totalFoodSpent"

    displayText += "\n\nOriginal: ${original.merchant} - ${original.amount}"
    displayText += "\nUpdated: ${updated.merchant} - ${updated.amount}"

    Text(
        text = displayText,
        modifier = modifier
    )
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TagPayTheme {
        Greeting("Android")
    }
}