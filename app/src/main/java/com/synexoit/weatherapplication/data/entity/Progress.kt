package com.synexoit.weatherapplication.data.entity

import com.synexoit.weatherapplication.R
import com.synexoit.weatherapplication.util.ViewType

class Progress : ViewType {

    companion object {
        private const val ID = "PROGRESS"
    }

    override val viewType: Int
        get() = R.layout.item_progress

    override val uniqueId: String
        get() = ID
}

