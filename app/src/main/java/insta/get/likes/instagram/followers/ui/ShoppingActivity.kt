package insta.get.likes.instagram.followers.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import insta.get.likes.instagram.followers.R
import insta.get.likes.instagram.followers.adapter.ShoppingAdapter
import insta.get.likes.instagram.followers.util.GooglePay
import insta.get.likes.instagram.followers.util.Util.Companion.HAPPY
import insta.get.likes.instagram.followers.util.Util.Companion.LARGE
import insta.get.likes.instagram.followers.util.Util.Companion.MINI
import insta.get.likes.instagram.followers.util.Util.Companion.NUM_HAPPY
import insta.get.likes.instagram.followers.util.Util.Companion.NUM_ONE
import insta.get.likes.instagram.followers.util.Util.Companion.NUM_POND
import insta.get.likes.instagram.followers.util.Util.Companion.NUM_STACK
import insta.get.likes.instagram.followers.util.Util.Companion.NUM_STANDARD
import insta.get.likes.instagram.followers.util.Util.Companion.NUM_TEAM
import insta.get.likes.instagram.followers.util.Util.Companion.NUM_THREE
import insta.get.likes.instagram.followers.util.Util.Companion.NUM_TWO
import insta.get.likes.instagram.followers.util.Util.Companion.POND
import insta.get.likes.instagram.followers.util.Util.Companion.STACK
import insta.get.likes.instagram.followers.util.Util.Companion.STANDARD
import insta.get.likes.instagram.followers.util.Util.Companion.SUPER
import insta.get.likes.instagram.followers.util.Util.Companion.TEAM
import insta.get.likes.instagram.followers.util.Util.Companion.buyCoins
import insta.get.likes.instagram.followers.util.Util.Companion.convert
import insta.get.likes.instagram.followers.util.Util.Companion.getCoins
import kotlinx.android.synthetic.main.activity_shop.*
import kotlinx.android.synthetic.main.title_bar.*

class ShoppingActivity : BaseActivity(), GooglePay.Callback, View.OnClickListener {
    private lateinit var shopRv: RecyclerView
    private var templateData = ArrayList<Pair<Int, String>>()
    lateinit var googlePay: GooglePay

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)
        initData()
        val mainShop = main_shop
        android.R.color.white.setStatusBarColors()
        title_txt.text = "Store"
        coins_group.visibility = View.VISIBLE
        view_stub.visibility = View.GONE
        title_back.setOnClickListener(this)
        mainShop.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white))
        shopRv = shop_rv
        val shopAdapter = ShoppingAdapter(this, templateData)
        shopRv.adapter = shopAdapter
        shopRv.layoutManager = LinearLayoutManager(this)
        googlePay = GooglePay.getGooglePay(this)
        googlePay.initGooglePay(this)
    }

    override fun onResume() {
        super.onResume()
        coins_txt.text = getCoins()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.title_back -> {
                onBackPressed()
            }
        }
    }

    private fun initData() {
        googlePay = GooglePay.getGooglePay(this)
        googlePay.initGooglePay(this)
        val pair1 = Pair(NUM_ONE, getString(R.string.num_one))
        val pair2 = Pair(NUM_TWO, getString(R.string.num_two))
        val pair3 = Pair(NUM_THREE, getString(R.string.num_three))
        val pair4 = Pair(NUM_HAPPY, getString(R.string.num_happy))
        val pair5 = Pair(NUM_STACK, getString(R.string.num_stack))
        val pair6 = Pair(NUM_STANDARD, getString(R.string.num_standard))
        val pair7 = Pair(NUM_TEAM, getString(R.string.num_team))
        val pair8 = Pair(NUM_POND, getString(R.string.num_pond))
        templateData.add(pair1)
        templateData.add(pair2)
        templateData.add(pair3)
        templateData.add(pair4)
        templateData.add(pair5)
        templateData.add(pair6)
        templateData.add(pair7)
        templateData.add(pair8)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        googlePay.onActivityResult(requestCode, resultCode, data, this)
    }

    override fun onDestroy() {
        super.onDestroy()
        googlePay.destroyService(this)
    }

    override fun callBack(id: String) {
        val num: String
        when (id) {
            MINI -> {
                num = buyCoins(NUM_ONE)
                coins_txt.text = convert(num.toInt())
            }
            LARGE -> {
                num = buyCoins(NUM_TWO)
                coins_txt.text = convert(num.toInt())
            }
            SUPER -> {
                num = buyCoins(NUM_THREE)
                coins_txt.text = convert(num.toInt())
            }
            HAPPY -> {
                num = buyCoins(NUM_HAPPY)
                coins_txt.text = convert(num.toInt())
            }
            STACK -> {
                num = buyCoins(NUM_STACK)
                coins_txt.text = convert(num.toInt())
            }
            STANDARD -> {
                num = buyCoins(NUM_STANDARD)
                coins_txt.text = convert(num.toInt())
            }
            TEAM -> {
                num = buyCoins(NUM_TEAM)
                coins_txt.text = convert(num.toInt())
            }
            POND -> {
                num = buyCoins(NUM_POND)
                coins_txt.text = convert(num.toInt())
            }
        }
    }
}
