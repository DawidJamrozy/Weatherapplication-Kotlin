package com.synexoit.weatherapplication.utils

import android.support.test.espresso.Espresso
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.BoundedMatcher
import android.support.test.espresso.matcher.ViewMatchers
import android.support.v7.widget.RecyclerView
import android.view.View
import org.hamcrest.Description
import org.hamcrest.Matcher

class CustomMatcher {

    companion object {
        fun checkAtPosition(recyclerViewId: Int, position: Int, itemMatcher: Matcher<View>) {
            checkNotNull(itemMatcher)
            Espresso.onView(ViewMatchers.withId(recyclerViewId))
                    .check(ViewAssertions.matches(object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
                        override fun describeTo(description: Description) {
                            description.appendText("has item at position $position: ")
                            itemMatcher.describeTo(description)
                        }

                        override fun matchesSafely(view: RecyclerView): Boolean {
                            val viewHolder = view.findViewHolderForAdapterPosition(position)
                            return viewHolder != null && itemMatcher.matches(viewHolder.itemView)
                        }
                    }))
        }
    }

}