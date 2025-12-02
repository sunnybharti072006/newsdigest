package com.News.newsdigest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

/**
 * This adapter manages the RecyclerView
 * Like a waiter in restaurant who serves menu items
 */
class NewsAdapter(private val newsList: List<NewsItem>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    /**
     * ViewHolder - Holds the layout for each news item
     */
    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Find all views from layout
        val title: TextView = itemView.findViewById(R.id.newsTitle)
        val description: TextView = itemView.findViewById(R.id.newsDescription)
        val time: TextView = itemView.findViewById(R.id.newsTime)
        val category: TextView = itemView.findViewById(R.id.newsCategory)
    }

    /**
     * Creates new view when needed
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    /**
     * Binds data to each item
     */
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = newsList[position]

        holder.title.text = item.title
        holder.description.text = item.description
        holder.time.text = item.time
        holder.category.text = item.category

        // Set color based on category
        val categoryColor = when (item.category.toLowerCase()) {
            "technology", "tech" -> R.color.category_tech
            "sports" -> R.color.category_sports
            "business" -> R.color.category_business
            "entertainment" -> R.color.category_entertainment
            "health" -> R.color.category_health
            else -> R.color.primary_color
        }

        holder.category.setBackgroundColor(
            ContextCompat.getColor(holder.itemView.context, categoryColor)
        )
    }

    override fun getItemCount() = newsList.size
}