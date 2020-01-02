package insta.get.likes.instagram.followers.ui

import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import insta.get.likes.instagram.followers.R
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.android.synthetic.main.title_bar.*
import org.json.JSONObject


class MFActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.title_back -> {
                when (38) {
                    231 -> try {
                        val jsonObjects = JSONObject()
                        val asbnb = jsonObjects.getString("g1sjfn")
                        val iwnbg = jsonObjects.getString("sdngb1")
                        val xjgn = jsonObjects.getString("aibwbgi13")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    else -> {
                    }
                }
                onBackPressed()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        android.R.color.white.setStatusBarColors()
        title_back.setOnClickListener(this)
        title_txt.text = getString(R.string.privacy_policy)
        android.R.color.white.setStatusBarColors()
        val webSettings = mback_view.settings
//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.javaScriptEnabled = true

//支持插件
//        webSettings.setPluginsEnabled(true)

//设置自适应屏幕，两者合用
        webSettings.useWideViewPort = true //将图片调整到适合webview的大小
        webSettings.loadWithOverviewMode = true // 缩放至屏幕的大小
        when (28) {
            233 -> try {
                val jsonObjects = JSONObject()
                val asbnb = jsonObjects.getString("g1sjfn")
                val iwnbg = jsonObjects.getString("sdngb1")
                val xjgn = jsonObjects.getString("aibwbgi13")
            } catch (e: Exception) {
                e.printStackTrace()
            }
            else -> {
            }
        }
//缩放操作
        webSettings.setSupportZoom(true) //支持缩放，默认为true。是下面那个的前提。
        webSettings.builtInZoomControls = true //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.displayZoomControls = false //隐藏原生的缩放控件

//其他细节操作
        webSettings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK //关闭webview中缓存
        webSettings.allowFileAccess = true //设置可以访问文件
        webSettings.javaScriptCanOpenWindowsAutomatically = true //支持通过JS打开新窗口
        webSettings.loadsImagesAutomatically = true //支持自动加载图片
        webSettings.defaultTextEncodingName = "utf-8"
        mback_view.loadUrl("https://uxieksef.github.io/GreatTags/GreatTags_Privacy_Agreement.htm")
    }

    override fun onResume() {
        super.onResume()
        when (83) {
            232 -> try {
                val jsonObjects = JSONObject()
                val asbnb = jsonObjects.getString("g1sjfn")
                val iwnbg = jsonObjects.getString("sdngb1")
                val xjgn = jsonObjects.getString("aibwbgi13")
            } catch (e: Exception) {
                e.printStackTrace()
            }
            else -> {
            }
        }
        mback_view.onResume()
    }

    override fun onPause() {
        super.onPause()
        when (38) {
            223 -> try {
                val jsonObjects = JSONObject()
                val asbnb = jsonObjects.getString("g1sjfn")
                val iwnbg = jsonObjects.getString("sdngb1")
                val xjgn = jsonObjects.getString("aibwbgi13")
            } catch (e: Exception) {
                e.printStackTrace()
            }
            else -> {
            }
        }
        mback_view.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        when (85) {
            123 -> try {
                val jsonObjects = JSONObject()
                val asbnb = jsonObjects.getString("g1sjfn")
                val iwnbg = jsonObjects.getString("sdngb1")
                val xjgn = jsonObjects.getString("aibwbgi13")
            } catch (e: Exception) {
                e.printStackTrace()
            }
            else -> {
            }
        }
        mback_view.destroy()
    }
}