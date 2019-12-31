package insta.get.likes.instagram.followers.adapter

import android.view.View
import android.widget.TextView
import insta.get.likes.instagram.followers.R
import insta.get.likes.instagram.followers.callback.TemplateCallback
import insta.get.likes.instagram.followers.data.FavoriteBean
import insta.get.likes.instagram.followers.data.HomeTagBean



class FavoriteAdapter<T> constructor(dataList: List<T>,
                                     templateCallback: TemplateCallback) : BaseAdapter<T>(dataList),
        ListenerWithPosition.OnClickWithPositionListener<Any> {

    private var callback: TemplateCallback = templateCallback
    override fun onClick(v: View?, position: Int, holder: Any) {
        callback.template(v, position)
    }

    override fun convert(holder: VH, t: T, position: Int) {
        val ta = t as FavoriteBean
        val favoriteTag = holder.getView<TextView>(R.id.favorite_txt)
        favoriteTag?.text = ta.str
        holder.setOnClickListener(this, R.id.favorite_copy)
        holder.setOnClickListener(this, R.id.favorite_remove)
    }

    override fun getLayout(viewType: Int): Int {
        return R.layout.favorite_item
    }
}