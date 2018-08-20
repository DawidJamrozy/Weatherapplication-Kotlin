package com.synexoit.weatherapplication.ui.base.navigator

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.View
import com.synexoit.weatherapplication.Henson
import com.synexoit.weatherapplication.ui.base.BaseFragmentActivity
import javax.inject.Inject

/**
 * Created by dawidjamrozy on 19.12.2017.
 */
class FNavigator @Inject constructor(private val mFragment: Fragment) : ANavigator(mFragment.activity!!), FragmentNavigator {

	override fun startActivityInterval(intent: Intent, options: Bundle?, requestCode: Int) {
		if (requestCode == -1)
			mFragment.startActivity(intent)
		else
			mFragment.startActivityForResult(intent, requestCode)
	}

	override fun replaceFragmentInterval(fragment: Fragment, addToBackStack: Boolean, args: Bundle?, clearBackStack: Boolean, sharedElement: Pair<View, String>?, animation: Any?) {
		val fm = mFragment.activity!!.supportFragmentManager

		val parentActivity = mFragment.activity as BaseFragmentActivity<*>

		if (args != null)
			fragment.arguments = args

		if (clearBackStack) fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

		val ft = fm.beginTransaction().replace(parentActivity.contentResId, fragment, null)

		sharedElement?.run {
			ft.addSharedElement(first, second)
			animation?.let {
				fragment.sharedElementEnterTransition = it
			}
		}

		if (addToBackStack) {
			ft.addToBackStack(null).commit()
			fm.executePendingTransactions()
		} else
			ft.commit()
	}

	override fun getHensonIntent(): Henson.WithContextSetState {
		return Henson.with(mFragment.activity)
	}
}