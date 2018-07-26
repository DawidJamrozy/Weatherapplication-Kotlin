package com.synexoit.weatherapplication.util

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Utils to show and hide soft keyboard
 */
class KeyboardUtil {

    companion object {
        /**
         * Responsible for hiding keyboard for [view]
         * @param view
         */
        fun hideKeyboard(view: View?) {
            view?.run {
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
                isFocusable = false
                isFocusableInTouchMode = false
                isFocusable = true
                isFocusableInTouchMode = true
                requestFocus()
            }
        }

        /**
         * Responsible for hiding keyboard for [activity]
         * @param activity
         */
        fun hideKeyboard(activity: Activity?) {
            activity?.let {
                hideKeyboard(it.currentFocus)
            }
        }

        /**
         * Responsible for hiding keyboard for [fragment]
         * @param fragment
         */
        fun hideKeyboard(fragment: Fragment) {
            hideKeyboard(fragment.view)
        }

        /**
         * Responsible for showing keyboard for [view]
         * @param view
         */
        fun showKeyboard(view: View) {
            val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, 0)
        }
    }


}