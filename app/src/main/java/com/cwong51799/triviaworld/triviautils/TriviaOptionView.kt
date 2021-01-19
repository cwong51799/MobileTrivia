package com.cwong51799.triviaworld.triviautils

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.cwong51799.triviaworld.R
import kotlinx.android.synthetic.main.trivia_option_view.view.*

class TriviaOptionView @JvmOverloads constructor(
    answerText : String,
    correct : Boolean,
    context : Context,
    attrs : AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    init{
        LayoutInflater.from(context).inflate(R.layout.trivia_option_view,this, true)
        triviaOptionTV.text = TriviaUtils.getFormattedHtmlFromString(answerText)
    }

    fun selectAnswer() {
        triviaOptionParentView.setBackgroundResource(R.color.colorPrimary)
    }

    fun deselectAnswer() {
        triviaOptionParentView.setBackgroundResource(0)
    }
}