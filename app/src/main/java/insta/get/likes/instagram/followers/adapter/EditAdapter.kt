package insta.get.likes.instagram.followers.adapter

import android.view.View
import android.widget.TextView
import insta.get.likes.instagram.followers.R
import insta.get.likes.instagram.followers.callback.TemplateCallback
import insta.get.likes.instagram.followers.data.EditBean


class EditAdapter<T> constructor(dataList: List<T>,
                                 templateCallback: TemplateCallback) : BaseAdapter<T>(dataList),
        ListenerWithPosition.OnClickWithPositionListener<Any> {

    private var callback: TemplateCallback = templateCallback
    override fun onClick(v: View?, position: Int, holder: Any) {
        callback.template(v, position)
    }

    override fun convert(holder: VH, t: T, position: Int) {
        val ta = t as EditBean
        val tx = holder.getView<TextView>(R.id.edit_txt)
        tx?.text = ta.str
        holder.setOnClickListener(this, R.id.delete_iv)

    }

    override fun getLayout(viewType: Int): Int {
        return R.layout.edit_item
    }
}