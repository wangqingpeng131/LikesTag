package insta.get.likes.instagram.followers.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import insta.get.likes.instagram.followers.R
import insta.get.likes.instagram.followers.callback.TemplateCallback
import insta.get.likes.instagram.followers.data.HomeTagBean



class DefaultTagAdapter<T> constructor( dataList: List<T>,
                                       templateCallback: TemplateCallback) : BaseAdapter<T>(dataList),
        ListenerWithPosition.OnClickWithPositionListener<Any> {

    private var callback: TemplateCallback = templateCallback
    override fun onClick(v: View?, position: Int, holder: Any) {
        callback.template(v, position)
    }

    override fun convert(holder: VH, t: T, position: Int) {
        val ta = t as HomeTagBean
        val favoriteIv = holder.getView<ImageView>(R.id.default_favorite_ic)
        val defaultTag = holder.getView<TextView>(R.id.default_txt)
        defaultTag?.text = ta.res
        if (ta.isFavorite) {
            favoriteIv?.setImageResource(R.drawable.heart_s_ic)
        } else {
            favoriteIv?.setImageResource(R.drawable.heart_n_ic)
        }
        holder.setOnClickListener(this, R.id.default_copy)
        holder.setOnClickListener(this, R.id.default_favorite)

    }

    override fun getLayout(viewType: Int): Int {
        return R.layout.default_tag_item
    }
}