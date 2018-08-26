package com.synexoit.weatherapplication

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.test.runner.AndroidJUnitRunner
import com.squareup.rx2.idler.Rx2Idler
import io.reactivex.plugins.RxJavaPlugins


class TestRunner : AndroidJUnitRunner() {

    /*override fun onCreate(arguments: Bundle) {
        RxJavaPlugins.setInitComputationSchedulerHandler(Rx2Idler.create("RxJava 2.x Computation Scheduler"))
        super.onCreate(arguments)
        //TODO 18.05.2018 by Dawid Jamroży
        //RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        //RESTMockServerStarter.startSync(AndroidAssetsFileParser(context), AndroidLogger())
    }

    @Throws(InstantiationException::class, IllegalAccessException::class, ClassNotFoundException::class)
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, TestApplication::class.java.name, context)
    }*/
}