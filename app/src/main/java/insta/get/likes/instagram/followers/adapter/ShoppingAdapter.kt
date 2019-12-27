package insta.get.likes.instagram.followers.adapter

import android.app.Activity
import android.content.Context
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.util.Pair
import insta.get.likes.instagram.followers.R
import insta.get.likes.instagram.followers.ui.ShoppingActivity
import insta.get.likes.instagram.followers.util.MyCommodity
import insta.get.likes.instagram.followers.util.Util.Companion.IAP
import insta.get.likes.instagram.followers.util.Util.Companion.LARGE
import insta.get.likes.instagram.followers.util.Util.Companion.MINI
import insta.get.likes.instagram.followers.util.Util.Companion.SUPER


class ShoppingAdapter<T> constructor(private var context: Context, dataList: List<T>) : BaseAdapter<T>(dataList) {
    private lateinit var myCommodity: MyCommodity

    override fun convert(holder: VH, t: T, position: Int) {
        val pair: Pair<*, *> = t as Pair<*, *>
        val coins = holder.getView<TextView>(R.id.shop_coin_num)
        val gold = holder.getView<TextView>(R.id.shop_gold_num)
        val shopItem = holder.getView<LinearLayout>(R.id.shop_item)
        coins?.text = context.getString(R.string.times, pair.first)
        gold?.text = pair.second.toString()
        val iapId = ArrayList<String>()
        iapId.add(MINI)
        iapId.add(LARGE)
        iapId.add(SUPER)
        shopItem?.setOnClickListener { _ ->
            if (position < iapId.size)
                myCommodity = MyCommodity(iapId[position], IAP, context.packageName)
            val activity: ShoppingActivity = context as ShoppingActivity
            activity.googlePay.startPay(myCommodity, context as Activity)
        }
    }

    override fun getLayout(viewType: Int): Int {
        return R.layout.shop_item
    }
}