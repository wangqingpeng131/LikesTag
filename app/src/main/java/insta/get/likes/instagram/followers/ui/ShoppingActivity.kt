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
import insta.get.likes.instagram.followers.util.Util.Companion.LARGE
import insta.get.likes.instagram.followers.util.Util.Companion.MINI
import insta.get.likes.instagram.followers.util.Util.Companion.NUM_ONE
import insta.get.likes.instagram.followers.util.Util.Companion.NUM_THREE
import insta.get.likes.instagram.followers.util.Util.Companion.NUM_TWO
import insta.get.likes.instagram.followers.util.Util.Companion.SUPER
import insta.get.likes.instagram.followers.util.Util.Companion.buyCoins
import kotlinx.android.synthetic.main.activity_shop.*

class ShoppingActivity : BaseActivity(), GooglePay.Callback {
    private lateinit var shopRv: RecyclerView
    private var templateData = ArrayList<Pair<Int, String>>()
    lateinit var googlePay: GooglePay

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)
        initData()
        val mainShop = main_shop
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
    }

    override fun onClick(v: View?) {
    }

    private fun initData() {
        googlePay = GooglePay.getGooglePay(this)
        googlePay.initGooglePay(this)
        val pair1 = Pair(NUM_ONE, getString(R.string.num_one))
        val pair2 = Pair(NUM_TWO, getString(R.string.num_two))
        val pair3 = Pair(NUM_THREE, getString(R.string.num_three))
        templateData.add(pair1)
        templateData.add(pair2)
        templateData.add(pair3)
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
                num = buyCoins(this, NUM_ONE)
            }
            LARGE -> {
                num = buyCoins(this, NUM_TWO)
            }
            SUPER -> {
                num = buyCoins(this, NUM_THREE)
            }
        }
    }
}
