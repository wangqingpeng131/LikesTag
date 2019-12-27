package insta.get.likes.instagram.followers.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import insta.get.likes.instagram.followers.R
import insta.get.likes.instagram.followers.adapter.HomeAdapter
import insta.get.likes.instagram.followers.callback.TemplateCallback
import insta.get.likes.instagram.followers.data.LikeData

class SearchFragment : Fragment(), TemplateCallback {

    private var mActivity: Activity? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as Activity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val homeRv = root.findViewById<RecyclerView>(R.id.home_rv)
        val homeBeans = LikeData.getHomeBean()
        homeRv.layoutManager = GridLayoutManager(mActivity, 2)
        homeRv.adapter = HomeAdapter(homeBeans, this)
        return root
    }


    override fun template(v: View?, position: Int) {
    }
    override fun onDetach() {
        super.onDetach()
        mActivity = null
    }
}