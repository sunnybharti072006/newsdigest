package com.News.newsdigest

data class NewsItem(
    val title: String,
    val description: String,
    val time: String,
    val category: String = "General",
    val imageUrl: String? = null,
    val source: String = "Unknown"
) {
    companion object {
        fun fromApiArticle(apiArticle: ApiArticle): NewsItem {
            return NewsItem(
                title = apiArticle.title ?: "No Title Available",
                description = apiArticle.description ?: "Click to read more...",
                time = formatTime(apiArticle.publishedAt),
                category = "News",
                imageUrl = apiArticle.urlToImage,
                source = apiArticle.source.name
            )
        }

        private fun formatTime(publishedAt: String): String {
            return try {
                "Today"
            } catch (e: Exception) {
                "Recently"
            }
        }
    }
}