package com.cwong51799.mobiletrivia.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cwong51799.mobiletrivia.network.TriviaQuestion
import com.cwong51799.mobiletrivia.network.TriviaResponse
import com.cwong51799.mobiletrivia.triviautils.TriviaQuestionWithStatus
import com.cwong51799.mobiletrivia.triviautils.TriviaSettings
import com.cwong51799.mobiletrivia.triviautils.TriviaUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TriviaViewModel : ViewModel()  {

    private var listOfQuestions = MutableLiveData<MutableList<TriviaQuestionWithStatus>>()
    val currentQuestion = MutableLiveData<TriviaQuestion>()
    val selectedAnswer = MutableLiveData<Pair<String, Boolean>>()

    private var triviaSettings: TriviaSettings? = null

    var numCorrect = 0
    var numIncorrect = 0


    fun queryTrivia(newTrivia : TriviaSettings){
        triviaSettings = newTrivia
        val call = TriviaUtils.TriviaServices.getTrivia(newTrivia.numQuestions, newTrivia.category, newTrivia.difficulty)
        call.enqueue(object : Callback<TriviaResponse> {
            override fun onFailure(call: Call<TriviaResponse>, t: Throwable) {
                // TODO Handle Failure
            }
            override fun onResponse(
                call: Call<TriviaResponse>,
                response: Response<TriviaResponse>
            ) {
                if (response.isSuccessful)
                for (question in response.body()?.results ?: emptyList()) {
                    addANewTriviaQuestion(question)
                }
                getNextTriviaQuestion()
            }
        })
    }

    fun addANewTriviaQuestion(newQuestion : TriviaQuestion){
        if (listOfQuestions.value == null) {
            listOfQuestions.value = mutableListOf(TriviaQuestionWithStatus(newQuestion))
        } else {
            listOfQuestions.value?.add(TriviaQuestionWithStatus(newQuestion))
        }
    }

    fun getNextTriviaQuestion() {
        val unusedTriviaQuestion = listOfQuestions.value?.find{ !it.used }
        unusedTriviaQuestion?.used = true
        currentQuestion.value = unusedTriviaQuestion?.triviaQuestion
    }

    /**
     * If there are no more questions to be used, the game is done.
     */
    fun isGameDone() : Boolean {
        val unusedTriviaQuestion = listOfQuestions.value?.find{ !it.used }
        return unusedTriviaQuestion == null
    }

    /**
     * @return true if the selected answer is true
     */
    fun isSelectedAnswerCorrect() : Boolean {
        return selectedAnswer.value?.second ?: false
    }

    /**
     * Resets the question and selected answer
     */
    fun resetQuestion() {
        currentQuestion.value = null
        selectedAnswer.value = null
    }

    fun resetTrivia() {
        currentQuestion.value = null
        selectedAnswer.value = null
        listOfQuestions.value = mutableListOf()
        triviaSettings = null
        numCorrect = 0
        numIncorrect = 0
    }


}
