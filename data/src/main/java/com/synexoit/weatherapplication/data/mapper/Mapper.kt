package com.synexoit.weatherapplication.data.mapper

interface Mapper<C, E, D> {

    fun fromCache(type: C): D

    fun toCache(type: D): C

    fun fromRemote(type: E): D

}