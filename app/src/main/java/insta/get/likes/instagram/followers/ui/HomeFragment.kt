package insta.get.likes.instagram.followers.ui

//import com.raizlabs.android.dbflow.kotlinextensions.save
//import insta.get.likes.instagram.followers.data.HomeTagBean
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import insta.get.likes.instagram.followers.R
import insta.get.likes.instagram.followers.adapter.HomeAdapter
import insta.get.likes.instagram.followers.adapter.RedeemItemDecoration
import insta.get.likes.instagram.followers.callback.TemplateCallback
import insta.get.likes.instagram.followers.data.LikeData
import insta.get.likes.instagram.followers.util.toDp

class HomeFragment : Fragment(), TemplateCallback {
    companion object {
        const val POSITION = "position"
    }

    private var mActivity: Activity? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as Activity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val homeRv = root.findViewById<RecyclerView>(R.id.home_rv)
        val homeBeans = LikeData.getHomeBean()
        homeRv.addItemDecoration(RedeemItemDecoration(46.toDp(), 25.toDp(), 25.toDp()))
        homeRv.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        homeRv.adapter = HomeAdapter(homeBeans, this)
        return root
    }


    override fun template(v: View?, position: Int) {
        when (v?.id) {
            R.id.default_iv -> {
                val intent = Intent(mActivity, DefaultTagsActivity::class.java)
                intent.putExtra(POSITION, position)
                startActivity(intent)
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        mActivity = null
    }
}