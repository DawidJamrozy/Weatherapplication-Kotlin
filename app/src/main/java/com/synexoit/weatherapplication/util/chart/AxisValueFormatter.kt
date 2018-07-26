package com.synexoit.weatherapplication.util.chart

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter

/**
 * Created by dawidjamrozy on 10.05.2018.
 */
class AxisValueFormatter(private val array: MutableList<String>) : IAxisValueFormatter {

    override fun getFormattedValue(value: Float, axis: AxisBase): String = array[value.toInt()]

}
