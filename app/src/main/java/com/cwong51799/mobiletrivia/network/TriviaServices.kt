package com.cwong51799.mobiletrivia.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TriviaServices {
    @GET("/api.php")
    fun getTrivia(
        @Query("amount") amount : Int,
        @Query("category") category : Int?,
        @Query("difficulty") difficulty : String?
    ) : Call<TriviaResponse>
}