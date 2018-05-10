package com.synexoit.weatherapp.util.chart

import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.utils.ViewPortHandler
import java.text.DecimalFormat

/**
 * Created by dawidjamrozy on 10.05.2018.
 */
class ValueFormatter : IValueFormatter {

    private val DEGREE = "\u00b0"
    private val decimalFormat: DecimalFormat = DecimalFormat("###,###,##0")

    override fun getFormattedValue(value: Float, entry: Entry, dataSetIndex: Int, viewPortHandler: ViewPortHandler): String {
        return decimalFormat.format(value.toDouble()) + DEGREE
    }
}