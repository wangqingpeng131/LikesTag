package insta.get.likes.instagram.followers.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustEvent
import com.mopub.common.MoPub
import com.mopub.common.SdkConfiguration
import com.mopub.common.SdkInitializationListener
import com.mopub.common.logging.MoPubLog
import com.mopub.mobileads.MoPubErrorCode
import com.mopub.mobileads.MoPubInterstitial
import insta.get.likes.instagram.followers.BuildConfig
import insta.get.likes.instagram.followers.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_bar.*

class MainActivity : BaseActivity(), MoPubInterstitial.InterstitialAdListener, RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    private lateinit var mInterstitial: MoPubInterstitial
    private var loadNum: Int = 0
    private val fragments = ArrayList<Fragment>()
    override fun onInterstitialLoaded(interstitial: MoPubInterstitial?) {
    }

    override fun onInterstitialShown(interstitial: MoPubInterstitial?) {
    }

    override fun onInterstitialFailed(interstitial: MoPubInterstitial?, errorCode: MoPubErrorCode?) {
        loadNum += 1
        if (loadNum < 5) {
            mInterstitial.load()
        }
    }

    override fun onInterstitialDismissed(interstitial: MoPubInterstitial?) {
        loadNum += 1
        if (loadNum < 5) {
            mInterstitial.load()
        }
    }

    override fun onInterstitialClicked(interstitial: MoPubInterstitial?) {
    }

    private fun initSdkListener(): SdkInitializationListener {
        return SdkInitializationListener {
            mInterstitial = MoPubInterstitial(this, "24534e1901884e398f1253216226017e")
            mInterstitial.interstitialAdListener = this
        }
    }

    private fun setMoPub() {
        val configBuilder = SdkConfiguration.Builder("24534e1901884e398f1253216226017e")
        if (BuildConfig.DEBUG) {
            configBuilder.withLogLevel(MoPubLog.LogLevel.DEBUG)
        } else {
            configBuilder.withLogLevel(MoPubLog.LogLevel.INFO)
        }
        MoPub.initializeSdk(this, configBuilder.build(), initSdkListener())

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        android.R.color.white.setStatusBarColors()
        main_title.text = "LIKE TAG"
        val event = AdjustEvent("vhme61")
        Adjust.trackEvent(event)
        setMoPub()
        addFragments()
        switchFragment(0)
        main_radio_group.setOnCheckedChangeListener(this)
        main_set.setOnClickListener(this)
        main_store.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        if (::mInterstitial.isInitialized) {
            if (mInterstitial.isReady) {
                mInterstitial.show()
            } else {
                mInterstitial.load()
            }
        }
        MoPub.onResume(this)
        try {
            Adjust.onResume()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.main_set -> {
            }
            R.id.main_store -> {
                startActivity(Intent(this, ShoppingActivity::class.java))
            }
        }
    }

    private fun addFragments() {
        fragments.add(HomeFragment())
        fragments.add(SearchFragment())
        fragments.add(EditFragment())
        fragments.add(FavoriteFragment())
    }

    private fun switchFragment(position: Int) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        for ((i, e) in fragments.withIndex()) {
            if (i == position) {
                if (e.isAdded) {
                    fragmentTransaction.show(e)
                } else {
                    fragmentTransaction.add(R.id.main_fl, e)
                }
            } else {
                if (e.isAdded) {
                    fragmentTransaction.hide(e)
                }
            }
        }
        fragmentTransaction.commit()
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            R.id.main_home -> {
                switchFragment(0)
            }
            R.id.main_search -> {
                switchFragment(1)
            }
            R.id.main_edit -> {
                switchFragment(2)
            }
            R.id.main_favorite -> {
                switchFragment(3)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        try {
            loadNum = 0
            Adjust.onPause()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
