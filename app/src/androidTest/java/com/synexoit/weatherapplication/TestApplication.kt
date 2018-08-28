package com.synexoit.weatherapplication

/*
class TestApplication : Application(), HasActivityInjector, HasServiceInjector {

    @Inject
    lateinit var mDispatchingAndroidActivityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var mDispatchingAndroidServiceInjector: DispatchingAndroidInjector<Service>

    private lateinit var applicationComponent: TestApplicationComponent

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerTestApplicationComponent.builder()
                .application(this)
                .context(this)
                .build()

        AppInjector.init(this)

        Timber.plant(Timber.DebugTree())

        RxJavaPlugins.setErrorHandler { e ->
            if (e is UndeliverableException) {
                Timber.d("WeatherApplication caught UndeliverableException : ${e.localizedMessage}")
            }
        }
    }


    override fun activityInjector(): AndroidInjector<Activity> = mDispatchingAndroidActivityInjector

    override fun serviceInjector(): AndroidInjector<Service> = mDispatchingAndroidServiceInjector
}*/
