package com.cwong51799.mobiletrivia.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.cwong51799.mobiletrivia.R
import com.cwong51799.mobiletrivia.viewmodels.TriviaViewModel
import com.cwong51799.mobiletrivia.triviautils.TriviaUtils

/**
 * The fragment associated with the page displayed after each question is answered
 */
class PostQuestionFragment : Fragment() {
    private lateinit var viewModel: TriviaViewModel
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(requireActivity()).get(TriviaViewModel::class.java)
        navController = NavHostFragment.findNavController(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_post_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Set the views
        setAnswerStatus(view)
        setTriviaQuestion(view)
        setCorrectAnswer(view)
        setScore(view)
        val goNextButton = view.findViewById<Button>(R.id.triviaGoToNextQuestionBtn)
        // Handle the case where there are no more questions left
        if (viewModel.isGameDone()) {
            goNextButton.text = getString(R.string.post_game_prompt)
        }
        // Set conditional onClickListener to the goNextButton
        goNextButton.setOnClickListener {
            if (viewModel.isGameDone()) {
                navController.navigate(
                    R.id.triviaOptionsFragment,
                    null,
                    TriviaUtils.getOptionsWithReturnSetToFragment(R.id.triviaMainFragment)
                )
            } else {
                viewModel.resetQuestion()
                navController.navigate(
                    R.id.triviaMainFragment,
                    null,
                    TriviaUtils.getOptionsWithReturnSetToFragment(R.id.triviaOptionsFragment)
                )
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    /**
     * Sets INCORRECT / CORRECT status of the answer
     */
    private fun setAnswerStatus(view: View) {
        val answerStatusTV = view.findViewById<TextView>(R.id.triviaAnswerStatusTV)
        if (viewModel.isSelectedAnswerCorrect()) {
            answerStatusTV.text = getString(R.string.correct)
            answerStatusTV.setTextColor(ContextCompat.getColor(view.context, R.color.correctGreen))
            viewModel.numCorrect++
        } else {
            answerStatusTV.text = getString(R.string.incorrect)
            answerStatusTV.setTextColor(ContextCompat.getColor(view.context, R.color.incorrectRed))
            viewModel.numIncorrect++
        }
    }

    /**
     * Sets the text to remind the user of the most recent question
     */
    private fun setTriviaQuestion(view: View) {
        val triviaQuestionTV = view.findViewById<TextView>(R.id.triviaQuestionTV)
        triviaQuestionTV.text =
            TriviaUtils.getFormattedHtmlFromString(viewModel.currentQuestion.value?.question ?: "")
    }

    /**
     * Sets the text to notify the user of the answer
     */
    private fun setCorrectAnswer(view: View) {
        val correctAnswerTV = view.findViewById<TextView>(R.id.triviaAnswerTV)
        correctAnswerTV.text = TriviaUtils.getFormattedHtmlFromString(
            viewModel.currentQuestion.value?.correct_answer ?: ""
        )

    }

    /**
     * Sets the score
     */
    fun setScore(view: View) {
        val numCorrectTV = view.findViewById<TextView>(R.id.triviaNumCorrectTV)
        val numIncorrectTV = view.findViewById<TextView>(R.id.triviaNumIncorrectTV)
        numCorrectTV.text = viewModel.numCorrect.toString()
        numIncorrectTV.text = viewModel.numIncorrect.toString()
    }

    companion object {

    }
}