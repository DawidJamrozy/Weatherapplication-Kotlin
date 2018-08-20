package com.synexoit.weatherapplication.ui.base

import android.arch.lifecycle.ViewModelProvider
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.f2prateek.dart.Dart
import com.synexoit.weatherapplication.R
import com.synexoit.weatherapplication.data.extensions.onClick
import com.synexoit.weatherapplication.data.manager.SharedPreferencesManager
import com.synexoit.weatherapplication.util.SingleToast
import dagger.android.AndroidInjection
import icepick.Icepick
import javax.inject.Inject

/**
 * Created by dawidjamrozy on 29.08.2017.
 */
abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity() {

	protected lateinit var binding: B

	@Inject
	protected lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    protected lateinit var sharedPreferencesManager:SharedPreferencesManager

	override fun onCreate(savedInstanceState: Bundle?) {
		AndroidInjection.inject(this)
		super.onCreate(savedInstanceState)
        Icepick.restoreInstanceState(this, savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutResId())
        binding.setLifecycleOwner(this)

		Dart.inject(this)
	}

	abstract fun getLayoutResId(): Int

	abstract fun getScreenTitle(): String

	private fun setUpCustomToolbar() {
		val arrowBack: ImageView? = findViewById(R.id.toolbar_back_arrow)
		val toolbarTitle: TextView? = findViewById(R.id.toolbar_title)

		arrowBack?.run {
			visibility = if (isDisplayingBackArrow()) View.VISIBLE else View.INVISIBLE
			onClick { onBackPressed() }
		}
		toolbarTitle?.run { text = getScreenTitle() }
	}

	open protected fun isDisplayingBackArrow(): Boolean {
		return true
	}

	override fun onPostCreate(savedInstanceState: Bundle?) {
		super.onPostCreate(savedInstanceState)
		if (!getScreenTitle().isEmpty())
			setUpCustomToolbar()
	}

	override fun onSaveInstanceState(outState: Bundle?) {
		super.onSaveInstanceState(outState)
        outState?.let { Icepick.saveInstanceState(this, it) }
	}

	protected fun showToast(text: String? = "ERROR", stringId: Int? = null, time: Int = Toast.LENGTH_SHORT) {
		val message = if (stringId != null) getString(stringId) else text ?: "ERROR"
		SingleToast.show(this, message, time)
	}
}