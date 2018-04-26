package com.synexoit.weatherapp.ui.search

import com.jakewharton.rxrelay2.PublishRelay
import com.synexoit.weatherapp.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchViewModel @Inject constructor() : BaseViewModel() {

    private val relay = PublishRelay.create<String>()

    init {
        registerRelay()
    }

    fun onTextChanged(text: CharSequence) {
        relay.accept(text.toString())
    }

    private fun registerRelay() {
        addDisposable(relay.filter { it.isNotEmpty() }
                .debounce(400, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { Timber.d("registerRelay(): $it") },
                        { it.printStackTrace() }
                ))
    }
}