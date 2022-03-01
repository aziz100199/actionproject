package com.unit.spinneractivity

import android.app.Application
import timber.log.Timber

class BaseApplicaton : Application() {
    override fun onCreate() {
        super.onCreate()
        hInitTimber()
    }

    private fun hInitTimber() {

        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun log(
                    priority: Int,
                    tag: String?,
                    message: String,
                    t: Throwable?,
                ) {
                    super.log(priority, String.format("result %s", tag), message, t)
                }
            })
        }
    }

}