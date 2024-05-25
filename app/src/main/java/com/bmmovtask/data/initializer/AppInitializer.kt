package com.bmmovtask.data.initializer

import android.app.Application

interface AppInitializer {
    fun init(application: Application)
}