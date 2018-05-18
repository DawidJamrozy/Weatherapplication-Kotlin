package com.synexoit.weatherapp.ui.base

import android.arch.lifecycle.ViewModelProvider
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.hannesdorfmann.fragmentargs.FragmentArgs
import com.synexoit.weatherapp.R
import com.synexoit.weatherapp.di.Injectable
import com.synexoit.weatherapp.util.SingleToast
import icepick.Icepick
import javax.inject.Inject


/**
 * Created by dawidjamrozy on 29.08.2017.
 */
abstract class BaseFragment<B : ViewDataBinding> : ViewLifecycleFragment(), Injectable {

	protected lateinit var binding: B

	@Inject
	protected lateinit var viewModelFactory: ViewModelProvider.Factory

	companion object {
		val NO_CHILD_CONTENT: Int = 0
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
        Icepick.restoreInstanceState(this, savedInstanceState)
		FragmentArgs.inject(this)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false)
        binding.setLifecycleOwner(this)
		return binding.root
	}

    /**
     * Fragment layout resource id
     */
	abstract fun getLayoutResId(): Int

	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)
		Icepick.saveInstanceState(this, outState)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		if (getScreenTitle().isNotEmpty())
			setUpCustomToolbar()
	}

	fun navigateBack() = (activity as BaseFragmentActivity<*>).navigateBack()

	private fun setUpCustomToolbar() {
		val arrowBack: ImageView? = view?.findViewById(R.id.toolbar_back_arrow)
		val toolbarTitle: TextView? = view?.findViewById(R.id.toolbar_title)

		arrowBack?.run {
			visibility = if (isDisplayingBackArrow()) View.VISIBLE else View.INVISIBLE
			setOnClickListener { navigateBack() }
		}
		toolbarTitle?.run { text = getScreenTitle() }
	}

	abstract fun getScreenTitle(): String

	protected open fun isDisplayingBackArrow() = false

	fun getChildContentResId(): Int = NO_CHILD_CONTENT

	open fun onBackPressed() = false

	protected fun showToast(text: String? = "ERROR", stringId: Int? = null, time: Int = Toast.LENGTH_SHORT) {
		val message = if (stringId == null) text ?: "ERROR" else getString(stringId)
		context?.let { SingleToast.show(it, message, time) }
	}

	protected fun hideKeyboard() {
        (activity as BaseActivity<*>).hideKeyboard()
	}
}