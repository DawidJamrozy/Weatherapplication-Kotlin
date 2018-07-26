package com.synexoit.weatherapplication.util

import android.content.Context
import android.support.v4.app.FragmentActivity
import android.widget.Toast

/**
 * Created by Dawidj on 01.09.2017.
 */
class SingleToast {

	companion object {
		private var mToast: Toast? = null

		fun show(context: Context, text: String, time: Int) {
			mToast?.cancel()
			mToast = Toast.makeText(context, text, time)
			mToast?.show()
		}

		fun show(context: FragmentActivity, text: String, time: Int) {
			mToast?.cancel()
			mToast = Toast.makeText(context, text, time)
			mToast?.show()
		}
	}
}