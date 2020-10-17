package android.androidtest1

import android.app.Application

class AndroidTest1Application : Application() {

    override fun onCreate() {
        super.onCreate()
        ListItemRepository.initialize(this)
    }
}