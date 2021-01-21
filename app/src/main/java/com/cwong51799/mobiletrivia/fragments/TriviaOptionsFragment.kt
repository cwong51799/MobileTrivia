package com.cwong51799.mobiletrivia.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.cwong51799.mobiletrivia.viewmodels.TriviaViewModel
import com.cwong51799.mobiletrivia.triviautils.TriviaSettings
import com.cwong51799.mobiletrivia.triviautils.TriviaUtils
import com.cwong51799.mobiletrivia.R
import com.cwong51799.mobiletrivia.triviautils.CustomStepperView


/**
 * A simple [Fragment] subclass.
 * Use the [TriviaOptionsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TriviaOptionsFragment : Fragment() {

    private lateinit var viewModel : TriviaViewModel
    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(requireActivity()).get(TriviaViewModel::class.java)
        navController = NavHostFragment.findNavController(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trivia_options, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.resetTrivia()
        val numQuestionsSelector = view.findViewById<CustomStepperView>(R.id.questionCountStepper)
        val categorySelector = view.findViewById<Spinner>(R.id.categorySelectorSpinner)
        val difficultySelector = view.findViewById<Spinner>(R.id.difficultySelectorSpinner)
        val startTriviaBtn = view.findViewById<Button>(R.id.beginTriviaBtn)

        startTriviaBtn.setOnClickListener {
            viewModel.queryTrivia(
                TriviaSettings(
                numQuestions = numQuestionsSelector.getCount(),
                category = TriviaUtils.getIndexOfCategory(categorySelector.selectedItem.toString()),
                difficulty = difficultySelector.selectedItem.toString().toLowerCase()
            )
            )
            navigateToTrivia()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    /**
     * Navigate to the main trivia fragment
     */
    private fun navigateToTrivia(){
        navController.navigate(R.id.triviaMainFragment)
    }
}