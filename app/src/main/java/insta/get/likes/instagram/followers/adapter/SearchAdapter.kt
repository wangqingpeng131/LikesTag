package insta.get.likes.instagram.followers.adapter

import android.view.View
import android.widget.CheckBox
import insta.get.likes.instagram.followers.R
import insta.get.likes.instagram.followers.callback.TemplateCallback
import insta.get.likes.instagram.followers.data.SearchBean


class SearchAdapter<T> constructor(dataList: List<T>,
                                   templateCallback: TemplateCallback) : BaseAdapter<T>(dataList),
        ListenerWithPosition.OnClickWithPositionListener<Any> {

    private var callback: TemplateCallback = templateCallback
    override fun onClick(v: View?, position: Int, holder: Any) {
        callback.template(v, position)
    }

    override fun convert(holder: VH, t: T, position: Int) {
        val ta = t as SearchBean
        val cb = holder.getView<CheckBox>(R.id.checkbox_it)
        cb?.setCompoundDrawablesWithIntrinsicBounds(ta.res, 0, R.drawable.checkbox_ic, 0)
        cb?.text = t.text
        holder.setOnClickListener(this, R.id.checkbox_it)

    }

    override fun getLayout(viewType: Int): Int {
        return R.layout.search_item
    }
}