package insta.get.likes.instagram.followers.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.raizlabs.android.dbflow.kotlinextensions.delete
import insta.get.likes.instagram.followers.R
import insta.get.likes.instagram.followers.adapter.FavoriteAdapter
import insta.get.likes.instagram.followers.callback.TemplateCallback
import insta.get.likes.instagram.followers.data.FavoriteBean
import insta.get.likes.instagram.followers.data.LikeData
import insta.get.likes.instagram.followers.util.Util

class FavoriteFragment : Fragment(), TemplateCallback, Util.PayCoin {

    private var mActivity: Activity? = null
    private lateinit var favoriteRv: RecyclerView
    private lateinit var promptingL: LinearLayout
    private lateinit var favoriteIv: RecyclerView
    private lateinit var favoriteAdapter: FavoriteAdapter<FavoriteBean>
    private lateinit var favoriteBeans: MutableList<FavoriteBean>
    private var currentContent: String? = null
    private val util = Util()
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as Activity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_favorite, container, false)
        favoriteRv = root.findViewById(R.id.favorite_rv)
        promptingL = root.findViewById(R.id.prompting_language_group)
        favoriteIv = root.findViewById(R.id.favorite_rv)
        return root
    }

    override fun onResume() {
        super.onResume()
        favoriteBeans = LikeData.getFavorites()
        if (favoriteBeans.isEmpty()) {
            promptingL.visibility = View.VISIBLE
            favoriteIv.visibility = View.GONE
        } else {
            promptingL.visibility = View.GONE
            favoriteIv.visibility = View.VISIBLE
        }
        favoriteAdapter = FavoriteAdapter(favoriteBeans, this)
        favoriteRv.layoutManager = LinearLayoutManager(mActivity as Context)
        favoriteRv.adapter = favoriteAdapter
    }

    override fun template(v: View?, position: Int) {
        when (v?.id) {
            R.id.favorite_copy -> {
                util.payCoin(mActivity as Context, this, 0, 0)
                currentContent = favoriteBeans[position].str
            }
            R.id.favorite_remove -> {
                favoriteBeans[position].delete()
                favoriteBeans.removeAt(position)
                favoriteAdapter.setsData(favoriteBeans)
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        mActivity = null
    }

    override fun payCoinsCallback(id: Int, position: Int) {
        when (id) {
            0 -> {
                util.copy(mActivity as Context, currentContent!!)
            }
        }
    }
}