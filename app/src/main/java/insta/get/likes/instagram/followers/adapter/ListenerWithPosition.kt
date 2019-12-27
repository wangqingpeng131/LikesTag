package insta.get.likes.instagram.followers.adapter

import android.view.View

class ListenerWithPosition(private var position: Int, private var holder: Any) : View.OnClickListener {
    lateinit var mOnClickListener: OnClickWithPositionListener<Any>

    fun setOnClickListener(mOnClickListener: OnClickWithPositionListener<Any>) {
        this.mOnClickListener = mOnClickListener
    }

    interface OnClickWithPositionListener<T> {
        fun onClick(v: View?, position: Int, holder: T)
    }

    override fun onClick(v: View?) {
        mOnClickListener.onClick(v, position, holder)
    }
}