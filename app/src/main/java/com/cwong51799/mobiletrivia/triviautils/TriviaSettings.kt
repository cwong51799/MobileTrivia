package com.cwong51799.mobiletrivia.triviautils

import com.cwong51799.mobiletrivia.network.TriviaQuestion

data class TriviaSettings (var numQuestions : Int, var category : Int?, var difficulty : String)

data class TriviaQuestionWithStatus(var triviaQuestion : TriviaQuestion, var used : Boolean = false)