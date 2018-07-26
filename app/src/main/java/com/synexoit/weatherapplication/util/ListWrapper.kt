package com.synexoit.weatherapplication.util

/**
 * Created by dawidjamrozy on 09.05.2018.
 */
sealed class ListStatus {
    class New :ListStatus()
    class Refresh: ListStatus()
}

class ListWrapper<T> (val status: ListStatus, val list: MutableList<T>)