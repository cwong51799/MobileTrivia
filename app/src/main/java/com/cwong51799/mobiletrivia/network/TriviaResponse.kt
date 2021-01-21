package com.cwong51799.mobiletrivia.network

import com.squareup.moshi.JsonClass

/**
 * The complete response object obtained from the query
 */
@JsonClass(generateAdapter = true)
data class TriviaResponse(val response_code : Int, val results : List<TriviaQuestion>)

/**
 * An individual TriviaQuestion
 */
@JsonClass(generateAdapter = true)
data class TriviaQuestion(val category : String, val type : String, val difficulty : String, val question : String,
                          val correct_answer : String, val incorrect_answers : List<String>)


