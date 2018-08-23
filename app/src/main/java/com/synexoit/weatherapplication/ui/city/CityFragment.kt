package com.synexoit.weatherapplication.ui.city

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.hannesdorfmann.fragmentargs.annotation.Arg
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import com.synexoit.weatherapplication.R
import com.synexoit.weatherapplication.data.entity.DayData
import com.synexoit.weatherapplication.data.entity.DayDetails
import com.synexoit.weatherapplication.data.entity.darksky.City
import com.synexoit.weatherapplication.data.exceptions.Failure
import com.synexoit.weatherapplication.data.extensions.empty
import com.synexoit.weatherapplication.data.extensions.failure
import com.synexoit.weatherapplication.data.extensions.getViewModel
import com.synexoit.weatherapplication.data.extensions.observe
import com.synexoit.weatherapplication.databinding.FragmentCityBinding
import com.synexoit.weatherapplication.ui.base.BaseFragment
import com.synexoit.weatherapplication.ui.base.adapter.UniversalAdapter
import com.synexoit.weatherapplication.ui.settings.SettingsActivity
import com.synexoit.weatherapplication.util.chart.AxisValueFormatter
import com.synexoit.weatherapplication.util.chart.ValueFormatter
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Dawid on 05.05.2018.
 */
@FragmentWithArgs
class CityFragment : BaseFragment<FragmentCityBinding>(), SwipeRefreshLayout.OnRefreshListener {

    companion object {
        private const val HOUR_FORMAT = "HH:mm"
    }

    override val layoutResId: Int
        get() =  R.layout.fragment_city

    override val screenTitle: String
        get() =  String.empty()

    @Arg(required = true) lateinit var id: String

    private lateinit var viewModel: CityViewModel

    private val dayRecyclerAdapter = UniversalAdapter()
    private val dayDetailsRecyclerAdapter = UniversalAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = getViewModel(viewModelFactory) {
            observe(city, ::handleCity)
            observe(onClickEvent, ::handleOnClick)
            observe(dayDataList, ::handleDayData)
            observe(dayDetailsList, ::handleDayDetails)
            failure(failure, ::handleFailure)
        }

        binding.vm = viewModel

        viewModel.loadCityFromDatabase(id)
        binding.swipeRefreshLayout.setOnRefreshListener(this)
        initRecyclerView()
    }

    override fun onRefresh() {
        setSwipeRefreshIndicator(true)
        viewModel.refreshWeatherData()
    }

    private fun initRecyclerView() {
        binding.dayRecyclerView.run {
            adapter = dayRecyclerAdapter
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL))
        }
        binding.detailsRecyclerView.run {
            adapter = dayDetailsRecyclerAdapter
            layoutManager = GridLayoutManager(this.context, 2, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun handleCity(city: City?) {
        city?.let {
            setSwipeRefreshIndicator(false)
            setChart(it)
        }
    }

    private fun handleDayData(list: MutableList<DayData>?) {
        list?.let { dayRecyclerAdapter.addNewList(it) }
    }

    private fun handleDayDetails(list: MutableList<DayDetails>?) {
        list?.let { dayDetailsRecyclerAdapter.addNewList(it) }
    }

    private fun handleFailure(failure: Failure?) {
        failure?.run {
            setSwipeRefreshIndicator(false)
            //TODO 14.05.2018 by Dawid Jamroży
            showToast("ERROR")
        }
    }

    private fun handleOnClick(event: Int?) {
        event?.let {
            when (it) {
                CityViewModel.OPEN_WEBSITE -> navigator.openWebsite(getString(R.string.powered_by_dark_sky_website))
                CityViewModel.OPEN_SETTINGS -> navigator.startActivity(Intent(activity, SettingsActivity::class.java))
            }
        }
    }

    private fun setSwipeRefreshIndicator(isRefreshing: Boolean) {
        binding.swipeRefreshLayout.isRefreshing = isRefreshing
    }

    private fun setChart(city: City) {
        val entries = mutableListOf<Entry>()
        val temperatureList = mutableListOf<Int>()
        val hours = mutableListOf<String>()

        @SuppressLint("SimpleDateFormat")
        val sdf = SimpleDateFormat(HOUR_FORMAT)
        sdf.timeZone = TimeZone.getTimeZone(city.timezone)

        for (i in 0..24) {
            val data = city.hourly!!.data!![i]
            //temp - add data to calculate min temperature for chart axis
            temperatureList.add(data.temperature.toInt())
            //entries - add data to display temp for every hour
            entries.add(Entry(i.toFloat(), data.temperature.toFloat()))
            //hours - add data to display time in chart
            hours.add(sdf.format(Date(data.time * 1000L)))
        }

        val lineDataSet = LineDataSet(entries, "Label")
        customizeLineDataSet(lineDataSet)

        with(binding.lineChart) {
            setYAxis(axisLeft, temperatureList)
            setYAxis(axisRight, temperatureList)
            setXAxis(xAxis, hours)
            customizeLineChart(this, LineData(lineDataSet))
        }
    }

    private fun customizeLineDataSet(lineDataSet: LineDataSet) {
        with(lineDataSet) {
            valueTextSize = 12f
            circleHoleRadius = 2.5f
            circleRadius = 4f
            valueFormatter = ValueFormatter()
            color = R.color.colorAccent
            valueTextColor = R.color.colorPrimary
        }
    }

    private fun customizeLineChart(lineChart: LineChart, lineData: LineData) {
        val emptyDescription = Description()
        emptyDescription.text = ""
        with(lineChart) {
            data = lineData
            legend.isEnabled = false
            setTouchEnabled(false)
            description = emptyDescription
            canScrollHorizontally(1)
            invalidate()
            notifyDataSetChanged()
        }
    }

    private fun setXAxis(axis: XAxis, hours: MutableList<String>) {
        with(axis) {
            labelCount = 25
            setDrawGridLines(false)
            position = XAxis.XAxisPosition.BOTTOM
            valueFormatter = AxisValueFormatter(hours)
        }
    }

    private fun setYAxis(axis: YAxis, temp: MutableList<Int>) {
        with(axis) {
            setDrawGridLines(false)
            setDrawLabels(false)
            axisMinimum = (Collections.min(temp) - 2).toFloat()
            axisMaximum = (Collections.max(temp) + 2).toFloat()
        }
    }
}