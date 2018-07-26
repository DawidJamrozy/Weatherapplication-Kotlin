package com.synexoit.weatherapplication.ui.base.navigator

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.synexoit.weatherapplication.Henson

/**
 * Created by dawidjamrozy on 01.09.2017.
 */
interface Navigator {

    fun finishActivity()
    fun finishActivityWithResult(resultCode: Int, data: Intent? = null)

    fun startActivity(intent: Intent, options:Bundle? = null)
    fun startActivityAndFinishCurrent(intent: Intent)
    fun startActivityForResult(intent: Intent, requestCode: Int)

    fun openWebsite(websiteUrl: String)

    fun replaceFragment(fragment: Fragment, addToBackStack:Boolean = false, clearBackStack:Boolean = false, args: Bundle? = null, sharedElement: Pair<View, String>? = null, animation: Any? = null)

    fun getHensonIntent() : Henson.WithContextSetState
}