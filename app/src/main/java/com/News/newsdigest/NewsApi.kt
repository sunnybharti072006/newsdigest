package com.News.newsdigest

import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("everything")
    suspend fun getAllNews(
        @Query("q") query: String = "India",
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("apiKey") apiKey: String = ""
    ): NewsApiResponse

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "in",
        @Query("apiKey") apiKey: String = ""
    ): NewsApiResponse
}