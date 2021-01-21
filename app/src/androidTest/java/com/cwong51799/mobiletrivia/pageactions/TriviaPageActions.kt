package com.cwong51799.mobiletrivia.pageactions

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.cwong51799.mobiletrivia.R
import org.hamcrest.Matcher
import java.lang.Exception
import java.lang.Thread.sleep

object TriviaPageActions {
    fun onTriviaOptionsPage() : Boolean {
        try {
            onView((withId(R.id.beginTriviaBtn))).check(matches(isDisplayed()))
            return true
        }catch (e : Exception){
            return false
        }
    }

    /**
     * Clicks the begin trivia option on the trivia options page
     */
    fun clickBeginTrivia() {
        onView(withId(R.id.beginTriviaBtn)).perform(click())
    }

    /**
     * Waits for the trivia to load by checking the visibility of the loading spinner
     */
    fun waitForTriviaToLoad() {
        waitForView(R.id.triviaLoadProgressBar, withEffectiveVisibility(Visibility.GONE), 2)
    }

    /**
     * Selects the middle trivia option. This works for now, since there will always be a middle
     * option to select. I think. Maybe this may fall apart on bigger screens. Should make something
     * that can click on a specific index. Will check out at another time.
     */
    fun selectTriviaOption() {
        onView(withId(R.id.triviaOptionsSV)).perform(click())
    }

    /**
     * Clicks the lock in answer button at the bottom of the main trivia fragment
     */
    fun clickLockInAnswer(){
        onView(withId(R.id.triviaLockAnswerBtn)).perform(click())
    }

    /**
     * Clicks the next question button on the post question fragment
     */
    fun clickNextQuestion(){
        onView(withId(R.id.triviaGoToNextQuestionBtn)).perform(click())
    }

    /**
     * Waits for a view that matches the view matcher after a given amount of seconds
     * @param viewId The view resource id
     * @param viewMatcher The ViewMatcher to check against
     * @param secondsToWait The amount of time that the automation should wait.
     */
    fun waitForView(
        viewId: Int,
        viewMatcher: Matcher<View>,
        secondsToWait : Long
    ): ViewInteraction? {
        sleep(secondsToWait*1000)
        return onView(withId(viewId)).check(matches(viewMatcher))
    }
}