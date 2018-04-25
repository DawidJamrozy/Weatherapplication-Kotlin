package com.synexoit.weatherapp.ui.base.navigator

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.view.View
import com.synexoit.weatherapp.Henson
import com.synexoit.weatherapp.ui.base.BaseFragmentActivity
import javax.inject.Inject

/**
 * Created by dawidjamrozy on 01.09.2017.
 */
open class ANavigator @Inject constructor(private val mActivity: FragmentActivity) : Navigator {

	override fun finishActivity() {
		finishActivityInterval()
	}

	override fun finishActivityWithResult(resultCode: Int, data: Intent?) {
		finishActivityInterval(resultCode, data)
	}

	protected open fun finishActivityInterval(resultCode: Int = -1, data: Intent? = null) {
		mActivity.setResult(resultCode, data)
		mActivity.finish()
	}

	override fun startActivity(intent: Intent, options: Bundle?) {
		startActivityInterval(intent, options)
	}

	override fun startActivityAndFinishCurrent(intent: Intent) {
		startActivityInterval(intent)
		finishActivityInterval()
	}

	override fun startActivityForResult(intent: Intent, requestCode: Int) {
		startActivityInterval(intent, requestCode = requestCode)
	}

	protected open fun startActivityInterval(intent: Intent, options: Bundle? = null, requestCode: Int = -1) {
		if (requestCode == -1)
			mActivity.startActivity(intent, options)
		else
			mActivity.startActivityForResult(intent, requestCode)
	}

	override fun replaceFragment(fragment: Fragment, addToBackStack: Boolean, clearBackStack: Boolean, args: Bundle?,  sharedElement: Pair<View, String>?, animation: Any?) {
		replaceFragmentInterval(fragment, addToBackStack, args, clearBackStack, sharedElement, animation)
	}

	protected open fun replaceFragmentInterval(fragment: Fragment, addToBackStack: Boolean = false, args: Bundle?, clearBackStack: Boolean = false, sharedElement: Pair<View, String>? = null, animation: Any? = null) {
		val fragmentManager = mActivity.supportFragmentManager

		val currentActivity = mActivity as BaseFragmentActivity<*>
		val currentFragment = fragmentManager.findFragmentById(currentActivity.getContentResId())

		// fragment to replace is same as current, do nothing
		if (currentFragment != null && currentFragment.javaClass == fragment.javaClass)
			return

		if (args != null) fragment.arguments = args

		if (clearBackStack) fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)


		val fragmentTransaction = fragmentManager.beginTransaction().replace(currentActivity.getContentResId(), fragment, null)

		sharedElement?.let {
			fragmentTransaction.addSharedElement(it.first, it.second)
			animation?.let {
				fragment.sharedElementEnterTransition = it
			}
		}

		if (addToBackStack) {
			fragmentTransaction.addToBackStack(null).commit()
			fragmentManager.executePendingTransactions()
		} else
			fragmentTransaction.commit()
	}

    override fun getHensonIntent(): Henson.WithContextSetState {
		return Henson.with(mActivity)
	}
}