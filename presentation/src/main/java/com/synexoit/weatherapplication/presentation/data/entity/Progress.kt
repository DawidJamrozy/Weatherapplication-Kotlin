package com.synexoit.weatherapplication.presentation.data.entity

import com.synexoit.weatherapplication.presentation.util.ViewType

class Progress : ViewType {

    companion object {
        private const val ID = "PROGRESS"
        const val VIEW_TYPE = 0
    }

    override val viewType: Int
        get() = VIEW_TYPE

    override val uniqueId: String
        get() = ID
}

