package insta.get.likes.instagram.followers.adapter

import android.view.View
import android.widget.ImageView
import insta.get.likes.instagram.followers.R
import insta.get.likes.instagram.followers.callback.TemplateCallback
import insta.get.likes.instagram.followers.data.HomeBean


class HomeAdapter<T> constructor(dataList: List<T>,
                                 templateCallback: TemplateCallback) : BaseAdapter<T>(dataList),
        ListenerWithPosition.OnClickWithPositionListener<Any> {

    private var callback: TemplateCallback = templateCallback
    override fun onClick(v: View?, position: Int, holder: Any) {
        callback.template(v, position)
    }

    override fun convert(holder: VH, t: T, position: Int) {
        val ta = t as HomeBean
        val iv = holder.getView<ImageView>(R.id.main_iv)
        iv?.setImageResource(ta.res)
        holder.setOnClickListener(this, R.id.main_iv)

    }

    override fun getLayout(viewType: Int): Int {
        return R.layout.main_item
    }
}