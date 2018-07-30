package com.synexoit.weatherapplication.util

/**
 * interface must be implemented by models which will be used in delegate adapters
 */
interface ViewType {

    /**
     *  Layout resource which will be used for delegate adapter
     */
    val viewType : Int

    /**
     *  Unique Id which is used to distinct items in DiffCallback
     *  Can set to "" if not used
     */
    val uniqueId: String

}