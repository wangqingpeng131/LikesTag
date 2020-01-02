package insta.get.likes.instagram.followers.adapter

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import insta.get.likes.instagram.followers.R
import insta.get.likes.instagram.followers.callback.TemplateCallback
import insta.get.likes.instagram.followers.data.SearchBean
import org.w3c.dom.Text


class SearchAdapter<T> constructor(dataList: List<T>,
                                   templateCallback: TemplateCallback) : BaseAdapter<T>(dataList),
        ListenerWithPosition.OnClickWithPositionListener<Any> {

    private var callback: TemplateCallback = templateCallback
    override fun onClick(v: View?, position: Int, holder: Any) {
        callback.template(v, position)
    }

    override fun convert(holder: VH, t: T, position: Int) {
        val ta = t as SearchBean
        val cb = holder.getView<ImageView>(R.id.search_label)
        val cb1 = holder.getView<ImageView>(R.id.search_label1)
        val text = holder.getView<TextView>(R.id.search_text)
        val select = holder.getView<ImageView>(R.id.search_select)
        if (position < 3) {
            cb?.visibility = View.VISIBLE
            cb1?.visibility = View.GONE
        }else {
            cb?.visibility = View.GONE
            cb1?.visibility = View.VISIBLE
        }
        if(ta.isSelect) {
            select?.setImageResource(R.drawable.select_ic)
        }else {
            select?.setImageResource(R.drawable.select_n_ic)
        }
        text?.text = ta.text
        holder.setOnClickListener(this, R.id.checkbox_group)

    }

    override fun getLayout(viewType: Int): Int {
        return R.layout.search_item
    }
}