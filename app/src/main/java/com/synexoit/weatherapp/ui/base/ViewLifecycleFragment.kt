package com.synexoit.weatherapp.ui.base

import android.arch.lifecycle.Lifecycle.Event
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View

/**
 * Fix in Android JetPack and Android KTX (Support library 28.0.0)
 */
abstract class ViewLifecycleFragment : Fragment() {

    private var viewLifecycleOwner: ViewLifecycleOwner? = null

    internal class ViewLifecycleOwner : LifecycleOwner {
        private val lifecycleRegistry = LifecycleRegistry(this)

        override fun getLifecycle(): LifecycleRegistry {
            return lifecycleRegistry
        }
    }

    /**
     * @return the Lifecycle owner of the current view hierarchy,
     * or null if there is no current view hierarchy.
     */
    fun getViewLifecycleOwner(): LifecycleOwner? {
        return viewLifecycleOwner
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner = ViewLifecycleOwner()
        viewLifecycleOwner?.run { lifecycle.handleLifecycleEvent(Event.ON_CREATE) }
    }

    override fun onStart() {
        super.onStart()
        viewLifecycleOwner?.run { lifecycle.handleLifecycleEvent(Event.ON_START) }
    }

    override fun onResume() {
        super.onResume()
        viewLifecycleOwner?.run { lifecycle.handleLifecycleEvent(Event.ON_RESUME) }
    }

    override fun onPause() {
        viewLifecycleOwner?.run { lifecycle.handleLifecycleEvent(Event.ON_PAUSE) }
        super.onPause()
    }

    override fun onStop() {
        viewLifecycleOwner?.run { lifecycle.handleLifecycleEvent(Event.ON_STOP) }
        super.onStop()
    }

    override fun onDestroyView() {
        viewLifecycleOwner?.run {
            lifecycle.handleLifecycleEvent(Event.ON_DESTROY)
            viewLifecycleOwner = null
        }
        super.onDestroyView()
    }
}