package insta.get.likes.instagram.followers.adapter

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T> constructor(dataList: List<T>) : RecyclerView.Adapter<BaseAdapter.VH>() {
    private var datalist: List<T> = dataList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH.set(parent, getLayout(viewType))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.mPosition = position
        convert(holder, datalist[position], position)
    }

    override fun getItemCount(): Int {
        return datalist.size
    }

    fun setsData(dataList: List<T>) {
        this.datalist = dataList
        notifyDataSetChanged()
    }

    protected abstract fun getLayout(viewType: Int): Int

    protected abstract fun convert(holder: VH, t: T, position: Int)


    class VH constructor(private val mContentView: View) : RecyclerView.ViewHolder(mContentView) {
        private val viewSparseArray: SparseArray<View> = SparseArray()
        var mPosition = 0
        fun setOnClickListener(
                clickListener: ListenerWithPosition.OnClickWithPositionListener<Any>,
                vararg viewId: Int
        ): VH {
            val listener = ListenerWithPosition(mPosition, this)
            listener.setOnClickListener(clickListener)
            viewId.forEach {
                val v = getView<View>(it)
                v?.setOnClickListener(listener)

            }
            return this
        }

        fun <T : View> getView(id: Int): T? {
            var v: View? = viewSparseArray.get(id)
            if (v == null) {
                v = mContentView.findViewById(id)
                viewSparseArray.put(id, v)
            }
            return v as T?
        }

        companion object {

            operator fun set(parent: ViewGroup, id: Int): VH {
                val view = LayoutInflater.from(parent.context).inflate(id, parent, false)
                return VH(view)
            }
        }
    }


}