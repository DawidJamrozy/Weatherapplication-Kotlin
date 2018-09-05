package com.synexoit.weatherapplication.utils

import com.synexoit.weatherapplication.data.entity.CityPreviewData
import com.synexoit.weatherapplication.utils.DataFactory.Factory.randomUuid

class CityPreviewCreator {

    companion object {
        fun createCityPreviewDataList(itemsCount: Int): List<CityPreviewData> {
            val list = mutableListOf<CityPreviewData>()

            for(i in 1..itemsCount) list.add(CityPreviewData(randomUuid(), randomUuid(), randomUuid(), i))

            return list
        }
    }
}