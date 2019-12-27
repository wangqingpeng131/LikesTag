package insta.get.likes.instagram.followers.ui

import androidx.multidex.MultiDexApplication
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustConfig
import com.adjust.sdk.LogLevel
import insta.get.likes.instagram.followers.BuildConfig
import insta.get.likes.instagram.followers.util.SaveFavorite

class MyApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        val environment: String
        val logLevel: LogLevel
        // 打包时配置为 false
        if (BuildConfig.DEBUG) {
            environment = AdjustConfig.ENVIRONMENT_SANDBOX
            logLevel = LogLevel.VERBOSE
        } else {
            environment = AdjustConfig.ENVIRONMENT_PRODUCTION
            logLevel = LogLevel.SUPRESS
        }
        val token = "9h0uf1g374zk"
        val config = AdjustConfig(this, token, environment)
        config.setLogLevel(logLevel)
        config.setSendInBackground(true)
        Adjust.onCreate(config)
        SaveFavorite.setContext(this)
    }
}