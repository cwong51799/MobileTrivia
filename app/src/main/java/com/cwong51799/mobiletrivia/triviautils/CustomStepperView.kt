package com.cwong51799.mobiletrivia.triviautils

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.cwong51799.mobiletrivia.R
import kotlinx.android.synthetic.main.custom_stepper_view.view.*

class CustomStepperView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_stepper_view, this, true)
        minusStep.setOnClickListener { decrementCount() }
        plusStep.setOnClickListener { incrementCount() }
    }

    fun getCount(): Int {
        return Integer.parseInt(stepperCount.text.toString())
    }

    private fun incrementCount() {
        val currentCount = getCount()
        if (currentCount < MAX_QUESTION_COUNT)
            stepperCount.text = (currentCount + 1).toString()
    }

    private fun decrementCount() {
        val currentCount = getCount()
        if (currentCount > MIN_QUESTION_COUNT)
            stepperCount.text = (currentCount - 1).toString()
    }


    companion object {
        private const val MIN_QUESTION_COUNT = 1
        private const val MAX_QUESTION_COUNT = 100
    }
}