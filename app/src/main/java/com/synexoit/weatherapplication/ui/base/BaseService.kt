package com.synexoit.weatherapplication.ui.base

import android.app.Service
import dagger.android.AndroidInjection

/**
 * Created by Dawid on 10.02.2018.
 */
abstract class BaseService : Service() {

	override fun onCreate() {
		super.onCreate()
		AndroidInjection.inject(this)
	}
}