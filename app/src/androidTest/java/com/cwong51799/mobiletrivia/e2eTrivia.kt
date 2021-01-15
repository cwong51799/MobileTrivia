package com.cwong51799.mobiletrivia

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.cwong51799.mobiletrivia.pageactions.TriviaPageActions
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TriviaTest(){

    /**
     * Launch the API Activity module
     */
    @get:Rule
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    /**
     * An end to end test of trivia.
     */
    @Test
    fun playTrivia() {
        TriviaPageActions.clickBeginTrivia()
        TriviaPageActions.waitForTriviaToLoad()
        while(!TriviaPageActions.onTriviaOptionsPage()) {
            TriviaPageActions.selectTriviaOption()
            TriviaPageActions.clickLockInAnswer()
            TriviaPageActions.clickNextQuestion()
        }
    }
}