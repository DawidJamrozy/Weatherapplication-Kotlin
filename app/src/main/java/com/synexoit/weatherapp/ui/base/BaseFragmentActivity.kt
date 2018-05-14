package com.synexoit.weatherapp.ui.base

import android.databinding.ViewDataBinding
import android.support.v4.app.Fragment
import com.synexoit.weatherapp.data.extensions.empty
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * Created by dawidjamrozy on 01.09.2017.
 */
abstract class BaseFragmentActivity<B: ViewDataBinding> : BaseActivity<B>(), HasSupportFragmentInjector {

    @Inject
    protected lateinit var mDispatchAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = mDispatchAndroidInjector

    /**
     * Layout resource id of View which will be used to change content
     */
    abstract fun getContentResId(): Int

    override fun getScreenTitle(): String = String.empty()

    override fun onBackPressed() = navigateBack()

    fun navigateBack() {

        val fragmentManager = supportFragmentManager

        val currentFragment: BaseFragment<*>? = fragmentManager.findFragmentById(getContentResId()) as? BaseFragment<*>
        val flgFragmentBackDone = currentFragment != null && currentFragment.onBackPressed()

        if (flgFragmentBackDone) {
            return
        }

        if (currentFragment != null && currentFragment.getChildContentResId() != BaseFragment.NO_CHILD_CONTENT) {

            val childFragmentManager = currentFragment.childFragmentManager

            val currentChildFragment: BaseFragment<*>? = childFragmentManager.findFragmentById(currentFragment.getChildContentResId()) as BaseFragment<*>

            val flgChildFragmentBackDone = currentChildFragment != null && currentChildFragment.onBackPressed()

            if (flgChildFragmentBackDone) {
                return
            }

            if (childFragmentManager.backStackEntryCount > 0) {
                childFragmentManager.popBackStack()
                return
            }
        }

        if (fragmentManager.backStackEntryCount == 0) {
            finish()
        }

        fragmentManager.popBackStack()
    }
}