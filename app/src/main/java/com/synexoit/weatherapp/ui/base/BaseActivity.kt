package com.synexoit.weatherapp.ui.base

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.f2prateek.dart.Dart
import com.synexoit.weatherapp.R
import com.synexoit.weatherapp.util.SingleToast
import dagger.android.AndroidInjection
import icepick.Icepick
import java.util.*
import javax.inject.Inject

/**
 * Created by dawidjamrozy on 29.08.2017.
 */
abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity() {

	protected lateinit var mBinding: B

	@Inject
	protected lateinit var mViewModelFactory: ViewModelProvider.Factory

	override fun onCreate(savedInstanceState: Bundle?) {
		AndroidInjection.inject(this)
		super.onCreate(savedInstanceState)
        Icepick.restoreInstanceState(this, savedInstanceState)
        //TODO 25.04.2018 by Dawid Jamroży
        //overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
		mBinding = DataBindingUtil.setContentView(this, getLayoutResId())
		mBinding.setLifecycleOwner(this)

		val sp = PreferenceManager.getDefaultSharedPreferences(baseContext)
		setLanguage(sp.getString("language", "pl"))

		Dart.inject(this)
	}

	abstract fun getLayoutResId(): Int

	abstract fun getScreenTitle(): String

	private fun setUpCustomToolbar() {
		val arrowBack: ImageView? = findViewById(R.id.toolbar_back_arrow)
		val toolbarTitle: TextView? = findViewById(R.id.toolbar_title)

		arrowBack?.let {
			it.visibility = if (isDisplayingBackArrow()) View.VISIBLE else View.INVISIBLE
			it.setOnClickListener { onBackPressed() }
		}
		toolbarTitle?.let { it.text = getScreenTitle() }
	}

	open protected fun isDisplayingBackArrow(): Boolean {
		return true
	}

	override fun finish() {
		super.finish()
		// //TODO 25.04.2018 by Dawid Jamroży
        //overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
	}

	override fun onPostCreate(savedInstanceState: Bundle?) {
		super.onPostCreate(savedInstanceState)
		if (!getScreenTitle().isEmpty())
			setUpCustomToolbar()
	}

	@Suppress("DEPRECATION")
	fun setLanguage(language: String) {
		//TODO change to context wrapper
		val conf = resources.configuration
		conf.locale = Locale(language)
		resources.updateConfiguration(conf, resources.displayMetrics)
	}

	override fun onSaveInstanceState(outState: Bundle?) {
		super.onSaveInstanceState(outState)

		if (outState != null) {
            //TODO 25.04.2018 by Dawid Jamroży
			Icepick.saveInstanceState(this, outState)
		}
	}

	protected fun showToast(text: String = "ERROR", stringId: Int? = null, time: Int = Toast.LENGTH_SHORT) {
		val message = if (stringId == null) text else getString(stringId)
		SingleToast.show(this, message, time)
	}

	protected fun hideKeyboard() {
		val view = this.currentFocus
		val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
		imm.hideSoftInputFromWindow(view.windowToken, 0)
	}
}