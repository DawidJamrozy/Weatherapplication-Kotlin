package com.synexoit.weatherapp.util

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * Created by dawidjamrozy on 09.05.2018.
 */
class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    private val fragmentList = ArrayList<Fragment>()

    override fun getItem(position: Int): Fragment = fragmentList[position]

    override fun getCount(): Int = fragmentList.size

    fun addFragment(fragment: Fragment) {
        fragmentList.add(fragment)
    }
}