package com.example.news.api

import com.example.news.model.NewsResponse
import com.example.news.model.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {
    // كدا دا فاتكشن نوعها  get ودا اللينك الى هيعمل منه get resources وهياخد apikey as a param والفانكشن هترجعلي تيمبليت نوعها الكلاس بتاعي الي هو sourcesresponse
    @GET("v2/top-headlines/sources")
    fun getsources(
        @Query("apiKey") apiKey: String,
        @Query("category") category: String

    ): Call<SourcesResponse>

    @GET("v2/everything")
    fun getnews(
        @Query("apiKey") apiKey: String,
        @Query("sources") sources: String
    ): Call<NewsResponse>


}