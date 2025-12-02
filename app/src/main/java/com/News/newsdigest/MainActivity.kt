package com.News.newsdigest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    // Declare UI components
    private lateinit var newsRecyclerView: RecyclerView
    private lateinit var greetingText: TextView
    private lateinit var dateText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize all components
        initializeViews()
        setupGreeting()
        setupDate()
        loadNewsFromApi()  // Load real news from API
    }

    /**
     * Find and initialize all views from XML layout
     */
    private fun initializeViews() {
        greetingText = findViewById(R.id.greetingText)
        dateText = findViewById(R.id.dateText)
        newsRecyclerView = findViewById(R.id.newsRecyclerView)
    }

    /**
     * Set greeting message based on current time
     * Good Morning, Good Afternoon, or Good Evening
     */
    private fun setupGreeting() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)

        val greeting = when {
            hour < 12 -> "Good Morning! ☀️"
            hour < 18 -> "Good Afternoon! 🌤️"
            else -> "Good Evening! 🌙"
        }
        greetingText.text = greeting
    }

    /**
     * Display current date in formatted way
     * Example: Friday, November 29, 2024
     */
    private fun setupDate() {
        val dateFormat = SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault())
        val todayDate = dateFormat.format(Date())
        dateText.text = todayDate
    }
    private fun loadNewsFromApi() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = ApiClient.newsApi.getAllNews()

                // Switch to main thread to update UI
                withContext(Dispatchers.Main) {
                    if (response.status == "ok" && response.articles.isNotEmpty()) {
                        // Convert API response to NewsItem objects
                        val newsList = response.articles.map { apiArticle ->
                            NewsItem.fromApiArticle(apiArticle)
                        }

                        // Display the news in RecyclerView
                        setupNewsList(newsList)
                        Toast.makeText(
                            this@MainActivity,
                            "${newsList.size} news loaded!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        // Show dummy data if no news found
                        showDummyData()
                        Toast.makeText(
                            this@MainActivity,
                            "No news found. Try different country/category.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } catch (e: Exception) {
                // Handle network errors
                withContext(Dispatchers.Main) {
                    showDummyData()
                    Toast.makeText(
                        this@MainActivity,
                        "Network issue! Using sample data.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
    /**
     * Setup RecyclerView with news data
     * @param newsList List of news items to display
     */
    private fun setupNewsList(newsList: List<NewsItem>) {
        newsRecyclerView.layoutManager = LinearLayoutManager(this)
        newsRecyclerView.adapter = NewsAdapter(newsList)
    }
    /**
     * Show sample data when API fails or no internet
     */
    private fun showDummyData() {
        val dummyNews = listOf(
            NewsItem(
                "Welcome to News Digest",
                "Real news will load when connected to internet.",
                "Just now",
                "APP"
            ),
            NewsItem(
                "How to Use This App",
                "Make sure you have internet connection to load real news from API",
                "1 min ago",
                "GUIDE"
            )
        )
        setupNewsList(dummyNews)
    }
}