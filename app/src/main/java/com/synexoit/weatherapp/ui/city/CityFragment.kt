package com.synexoit.weatherapp.ui.city

import android.annotation.SuppressLint
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
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
import com.synexoit.weatherapp.data.entity.DayDetails
import com.synexoit.weatherapp.data.entity.darksky.City
import com.synexoit.weatherapp.data.entity.darksky.DayData
import com.synexoit.weatherapp.data.exceptions.Failure
import com.synexoit.weatherapp.data.extensions.empty
import com.synexoit.weatherapp.data.extensions.failure
import com.synexoit.weatherapp.data.extensions.getViewModel
import com.synexoit.weatherapp.data.extensions.observe
import com.synexoit.weatherapp.databinding.FragmentCityBinding
import com.synexoit.weatherapp.ui.base.BaseFragment
import com.synexoit.weatherapp.ui.base.adapter.UniversalAdapter
import com.synexoit.weatherapp.ui.base.navigator.FragmentNavigator
import com.synexoit.weatherapp.ui.main.MainActivity
import com.synexoit.weatherapp.ui.settings.SettingsActivity
import com.synexoit.weatherapp.util.chart.AxisValueFormatter
import com.synexoit.weatherapp.util.chart.ValueFormatter
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Created by Dawid on 05.05.2018.
 */
@FragmentWithArgs
class CityFragment : BaseFragment<FragmentCityBinding>(), SwipeRefreshLayout.OnRefreshListener {

    companion object {
        private const val HOUR_FORMAT = "HH:mm"
        private const val SETTINGS_REQUEST_CODE = 10001
    }

    @Arg(required = true)
    lateinit var id: String

    @Inject
    protected lateinit var navigator: FragmentNavigator
    private lateinit var viewModel: CityViewModel
    private val dayRecyclerAdapter = UniversalAdapter()
    private val dayDetailsRecyclerAdapter = UniversalAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel(viewModelFactory, {
            observe(city, ::handleCity)
            observe(event, ::handleEvent)
            observe(dayDataList, ::handleDayData)
            observe(dayDetailsList, ::handleDayDetails)
            failure(failure, ::handleFailure)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel

        viewModel.loadCityFromDatabase(id)
        binding.swipeRefreshLayout.setOnRefreshListener(this)
        initRecyclerView()
    }

    override fun getScreenTitle(): String = String.empty()

    override fun getLayoutResId(): Int = R.layout.fragment_city

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
        list?.let {
            dayDetailsRecyclerAdapter.addNewList(it)
        }
    }

    private fun handleFailure(failure: Failure?) {
        failure?.let {
            setSwipeRefreshIndicator(false)
            //TODO 14.05.2018 by Dawid Jamroży
            showToast("ERROR")
        }
    }

    private fun handleEvent(event: Int?) {
        event?.let {
            when(it) {
                CityViewModel.OPEN_WEBSITE -> navigator.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.powered_by_dark_sky_website))))
                CityViewModel.OPEN_SETTINGS ->  {
                    navigator.startActivity(Intent(activity, SettingsActivity::class.java))
                    /*navigator.startActivityForResult(Intent(activity, SearchActivity::class.java), SETTINGS_REQUEST_CODE)*/
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(resultCode) {
            RESULT_OK -> (activity as MainActivity).refreshCities()
            RESULT_CANCELED -> Timber.d("onActivityResult(): ignore")
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