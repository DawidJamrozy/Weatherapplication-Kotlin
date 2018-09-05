package com.synexoit.weatherapplication

import android.support.test.runner.AndroidJUnitRunner
import com.squareup.rx2.idler.Rx2Idler
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers


class TestRunner : AndroidJUnitRunner() {

    override fun onStart() {
        RxJavaPlugins.setInitComputationSchedulerHandler(Rx2Idler.create("RxJava 2.x Computation Scheduler"))
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        super.onStart()
    }

}