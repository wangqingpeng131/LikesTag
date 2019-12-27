package insta.get.likes.instagram.followers.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import insta.get.likes.instagram.followers.R
import insta.get.likes.instagram.followers.adapter.HomeAdapter
import insta.get.likes.instagram.followers.adapter.SearchAdapter
import insta.get.likes.instagram.followers.callback.TemplateCallback
import insta.get.likes.instagram.followers.data.LikeData

class SearchFragment : Fragment(), TemplateCallback {

    private var mActivity: Activity? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as Activity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_search, container, false)
        val searchRv= root.findViewById<RecyclerView>(R.id.search_rv)
        val searchEd = root.findViewById<EditText>(R.id.search_edit)
        searchEd.setHintTextColor(ContextCompat.getColor(mActivity as Context, R.color.pink))
        val searchBeans = LikeData.getHomeBean()
        searchRv.layoutManager = LinearLayoutManager(mActivity as Context)
        searchRv.adapter = SearchAdapter(searchBeans, this)
        return root
    }


    override fun template(v: View?, position: Int) {
    }

    override fun onDetach() {
        super.onDetach()
        mActivity = null
    }
}