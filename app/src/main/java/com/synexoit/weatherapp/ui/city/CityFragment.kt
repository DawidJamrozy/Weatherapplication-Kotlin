package com.synexoit.weatherapp.ui.city

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.hannesdorfmann.fragmentargs.annotation.Arg
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import com.synexoit.weatherapp.R
import com.synexoit.weatherapp.data.entity.darksky.City
import com.synexoit.weatherapp.databinding.FragmentCityBinding
import com.synexoit.weatherapp.ui.base.BaseFragment
import com.synexoit.weatherapp.ui.base.adapter.UniversalAdapter
import com.synexoit.weatherapp.ui.base.navigator.FragmentNavigator
import com.synexoit.weatherapp.util.chart.AxisValueFormatter
import com.synexoit.weatherapp.util.chart.ValueFormatter
import com.synexoit.weatherapp.util.getViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Created by Dawid on 05.05.2018.
 */
@FragmentWithArgs
class CityFragment : BaseFragment<FragmentCityBinding>(), SwipeRefreshLayout.OnRefreshListener {

    @Arg(required = true)
    lateinit var id: String

    @Inject
    protected lateinit var navigator: FragmentNavigator
    private lateinit var viewModel: CityViewModel
    private val recyclerAdapter = UniversalAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel(CityViewModel::class.java, mViewModelFactory)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel

        viewModel.loadCityFromDatabase(id)
        binding.swipeRefreshLayout.setOnRefreshListener(this)
        registerObservers()
        initRecyclerView()
    }

    override fun getScreenTitle(): String = ""

    override fun getLayoutResId(): Int = R.layout.fragment_city

    override fun onRefresh() {
        setSwipeRefreshIndicator(true)
        viewModel.refreshWeatherData()
    }

    private fun initRecyclerView() {
        binding.recyclerView.run {
            adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL))
        }
    }

    private fun registerObservers() {
        viewModel.getCity().observe(this, Observer {
            it?.let {
                setSwipeRefreshIndicator(false)
                setChart(it)
            }
        })

        viewModel.getDayData().observe(this, Observer {
            it?.let {
                recyclerAdapter.addNewList(it)
            }
        })

        viewModel.getErrorObserver().observe(this, Observer {
            it?.let {
                setSwipeRefreshIndicator(false)
                showToast(it.message)
            }
        })
    }

    private fun setSwipeRefreshIndicator(isRefreshing: Boolean) {
        binding.swipeRefreshLayout.isRefreshing = isRefreshing
    }

    private fun setChart(city: City) {
        val entries = mutableListOf<Entry>()
        val temperatureList = mutableListOf<Int>()
        val hours = mutableListOf<String>()

        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone(city.timezone)
        //temp - add data to calculate min temperature for chart axis
        //entries - add data to display temp for every hour
        //hours - add data to display time in chart
        for (i in 0..24) {
            val data = city.hourly!!.data!![i]
            temperatureList.add(data.temperature.toInt())
            entries.add(Entry(i.toFloat(), data.temperature.toFloat()))
            hours.add(sdf.format(Date(data.time * 1000L)))
        }

        val leftAxis = binding.lineChart.axisLeft
        setYAxis(leftAxis, temperatureList)

        val rightAxis = binding.lineChart.axisRight
        setYAxis(rightAxis, temperatureList)

        val downAxis = binding.lineChart.xAxis
        setXAxis(downAxis, hours)

        val lineDataSet = LineDataSet(entries, "Label")
        customizeLineDataSet(lineDataSet)

        val lineData = LineData(lineDataSet)
        customizeLineChart(lineData)
    }

    private fun customizeLineDataSet(lineDataSet: LineDataSet) {
        lineDataSet.run {
            valueTextSize = 12f
            circleHoleRadius = 2.5f
            circleRadius = 4f
            valueFormatter = ValueFormatter()
            color = R.color.colorAccent
            valueTextColor = R.color.colorPrimary
        }
    }

    private fun customizeLineChart(lineData: LineData) {
        val emptyDescription = Description()
        emptyDescription.text = ""
        binding.lineChart.run {
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
        axis.run {
            labelCount = 25
            setDrawGridLines(false)
            position = XAxis.XAxisPosition.BOTTOM
            valueFormatter = AxisValueFormatter(hours)
        }
    }

    private fun setYAxis(axis: YAxis, temp: MutableList<Int>) {
        axis.run {
            setDrawGridLines(false)
            setDrawLabels(false)
            axisMinimum = (Collections.min(temp) - 2).toFloat()
            axisMaximum = (Collections.max(temp) + 2).toFloat()
        }
    }
}