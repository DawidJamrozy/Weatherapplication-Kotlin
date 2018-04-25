package com.synexoit.weatherapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.f2prateek.dart.HensonNavigable

@HensonNavigable
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
