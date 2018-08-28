package com.synexoit.weatherapplication.data.mapper

interface Mapper<CACHE, REMOTE, DATA> {

    fun fromCache(type: CACHE): DATA

    fun toCache(type: DATA): CACHE

    fun fromRemote(type: REMOTE): DATA

}