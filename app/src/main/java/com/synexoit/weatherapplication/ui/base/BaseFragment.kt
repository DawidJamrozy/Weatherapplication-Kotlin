package com.synexoit.weatherapplication.ui.base

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
import com.synexoit.weatherapplication.R
import com.synexoit.weatherapplication.data.extensions.gone
import com.synexoit.weatherapplication.data.extensions.onClick
import com.synexoit.weatherapplication.data.extensions.visible
import com.synexoit.weatherapplication.di.Injectable
import com.synexoit.weatherapplication.ui.base.navigator.FragmentNavigator
import com.synexoit.weatherapplication.util.SingleToast
import icepick.Icepick
import javax.inject.Inject


/**
 * Created by dawidjamrozy on 29.08.2017.
 */
abstract class BaseFragment<B : ViewDataBinding> : ViewLifecycleFragment(), Injectable {

    companion object {
        const val NO_CHILD_CONTENT: Int = 0
    }

    @Inject
    protected lateinit var navigator: FragmentNavigator

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    /**
     * Base binding generic object
     */
    protected lateinit var binding: B

    /**
     * Provides layout resource id to set content view by data binding
     */
    abstract val layoutResId: Int

    /**
     * Provides default title which will by used in activity toolbar
     */
    abstract val screenTitle: String

    /**
     * @return if Toolbar back arrow should be displayed
     */
    protected open val isDisplayingBackArrow: Boolean
        get() = true

    open val childContentResId: Int = NO_CHILD_CONTENT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Icepick.restoreInstanceState(this, savedInstanceState)
        FragmentArgs.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        binding.setLifecycleOwner(this)
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Icepick.saveInstanceState(this, outState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (screenTitle.isNotEmpty())
            setUpCustomToolbar()
    }

    protected fun navigateBack() = (activity as BaseFragmentActivity<*>).navigateBack()

    /**
     * Sets custom toolbar
     */
    private fun setUpCustomToolbar() {
        val arrowBack: ImageView? = view?.findViewById(R.id.toolbar_back_arrow)
        val toolbarTitle: TextView? = view?.findViewById(R.id.toolbar_title)

        arrowBack?.run {
            if (isDisplayingBackArrow) visible() else gone()
            onClick { navigateBack() }
        }
        toolbarTitle?.run { text = screenTitle }
    }

    /**
     * Method called by navigator, can be override to add custom on back pressed flow in app
     */
    open fun onBackPressed() = false

    /**
     * Show basic toast message with fragment context
     */
    protected fun showToast(text: String? = "ERROR", stringId: Int? = null, time: Int = Toast.LENGTH_SHORT) {
        val message = if (stringId == null) text ?: "ERROR" else getString(stringId)
        context?.let { SingleToast.show(it, message, time) }
    }
}