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
class Transaction(val merchant: String, val amount: Double, val category: String)
fun addAmount(current:Double,newAmmount: Double):Double{
    return current+newAmmount
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

    var displayText = "Hello $name!\n"
    for (transaction in transactions) {
        displayText += "${transaction.merchant}: ₹${transaction.amount} - ${transaction.category}\n"
    }
    var totalamount=0.0
    for (transaction in transactions){
        totalamount=addAmount(totalamount,transaction.amount)
    }
    displayText += " total amount: $totalamount"

    val foodTransactions = transactions.filter { it.category == "Food" }
    val totalFoodSpent = foodTransactions.sumOf { it.amount }
    displayText += "\nTotal spent on Food: ₹$totalFoodSpent"

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