package com.cwong51799.triviaworld.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.cwong51799.triviaworld.viewmodels.TriviaViewModel
import com.cwong51799.triviaworld.triviautils.TriviaOptionView
import com.cwong51799.triviaworld.network.TriviaQuestion
import com.cwong51799.triviaworld.triviautils.TriviaUtils
import com.cwong51799.triviaworld.R


class TriviaFragment : Fragment() {
    private lateinit var viewModel: TriviaViewModel
    private lateinit var navController : NavController
    private lateinit var triviaQuestionTV : TextView
    private lateinit var triviaOptionsTV : ScrollView
    private lateinit var triviaOptionsLL : LinearLayout
    private lateinit var triviaLockAnswerBtn : Button
    private lateinit var triviaProgressBar : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(requireActivity()).get(TriviaViewModel::class.java)
        navController = NavHostFragment.findNavController(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_trivia_api, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        triviaQuestionTV = view.findViewById(R.id.triviaQuestionTV)
        triviaOptionsTV = view.findViewById(R.id.triviaOptionsSV)
        triviaOptionsLL = view.findViewById(R.id.triviaOptionLL)
        triviaLockAnswerBtn = view.findViewById(R.id.triviaLockAnswerBtn)
        triviaProgressBar = view.findViewById(R.id.triviaLoadProgressBar)
        // Observe changes within the view model and dynamically load the question
        viewModel.currentQuestion.observe(viewLifecycleOwner) { currentQuestion ->
            if(currentQuestion != null) {
                generateTriviaQuestion(view, currentQuestion)
                triviaProgressBar.visibility = View.GONE
            } else {
                triviaProgressBar.visibility = View.VISIBLE
            }
        }
        viewModel.selectedAnswer.observe(viewLifecycleOwner) {selectedAnswer ->
            if(selectedAnswer != null) {
                triviaLockAnswerBtn.text = "LOCK IN " + TriviaUtils.getFormattedHtmlFromString(selectedAnswer.first)
                triviaLockAnswerBtn.isEnabled = true
            }
        }
        triviaLockAnswerBtn.setOnClickListener {
            navigateToPostQuestion()
        }
        viewModel.getNextTriviaQuestion()
        super.onViewCreated(view, savedInstanceState)
    }

    /**
     * Sets the question and options for the result
     */
    private fun generateTriviaQuestion(view : View, question : TriviaQuestion?) {
        if(question != null) {
            triviaOptionsLL.removeAllViews()
            triviaQuestionTV.text =
                TriviaUtils.getFormattedHtmlFromString(question.question)
            val possibleAnswers = mutableListOf<Pair<String, Boolean>>()
            question.incorrect_answers.forEach {
                possibleAnswers.add(Pair(it, false))
            }
            possibleAnswers.add(Pair(question.correct_answer, true))
            possibleAnswers.shuffle()
            for (answer in possibleAnswers) {
                val newOptionView =
                    TriviaOptionView(
                        answerText = answer.first,
                        correct = answer.second,
                        context = view.context
                    )
                newOptionView.setOnClickListener {
                    deselectAllButtons()
                    newOptionView.selectAnswer()
                    viewModel.selectedAnswer.value = answer
                }
                triviaOptionsLL.addView(newOptionView)
            }
        }
    }

    /**
     * Navigates to the post question fragment after selecting an answer
     */
    private fun navigateToPostQuestion() {
        navController.navigate(R.id.postQuestionFragment, null, TriviaUtils.getOptionsWithReturnSetToFragment(R.id.triviaOptionsFragment))
    }

    /**
     * Deselects all buttons in the option scroll view
     */
    fun deselectAllButtons() {
        for (view in triviaOptionsLL.children) {
            if (view is TriviaOptionView) {
                view.deselectAnswer()
            }
        }
    }

    companion object {
    }
}