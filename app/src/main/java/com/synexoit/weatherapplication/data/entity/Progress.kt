package com.synexoit.weatherapplication.data.entity

import com.synexoit.weatherapplication.util.ViewType

class Progress : ViewType {

    companion object {
        private const val ID = "PROGRESS"
    }

    override val viewType: Int
        get() = 2

    override val uniqueId: String
        get() = ID
}

