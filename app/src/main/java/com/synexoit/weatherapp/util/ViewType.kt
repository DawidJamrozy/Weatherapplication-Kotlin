package com.synexoit.weatherapp.util

/**
 * interface must be implemented by models which will be used in Delegates
 */
interface ViewType {

    fun getViewType() : Int

    fun getUniqueId(): String

}