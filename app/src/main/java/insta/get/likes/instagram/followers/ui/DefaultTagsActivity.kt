package insta.get.likes.instagram.followers.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.raizlabs.android.dbflow.kotlinextensions.delete
import com.raizlabs.android.dbflow.kotlinextensions.save
import com.raizlabs.android.dbflow.sql.language.Delete
import insta.get.likes.instagram.followers.R
import insta.get.likes.instagram.followers.adapter.DefaultTagAdapter
import insta.get.likes.instagram.followers.callback.TemplateCallback
import insta.get.likes.instagram.followers.data.FavoriteBean
import insta.get.likes.instagram.followers.data.FavoriteBean_Table
import insta.get.likes.instagram.followers.data.HomeTagBean
import insta.get.likes.instagram.followers.data.LikeData
import insta.get.likes.instagram.followers.util.Util
import kotlinx.android.synthetic.main.activity_default.*
import kotlinx.android.synthetic.main.title_bar.*

class DefaultTagsActivity : BaseActivity(), TemplateCallback, View.OnClickListener, Util.PayCoin {
    private lateinit var defaultTagAdapter: DefaultTagAdapter<HomeTagBean>
    private var homeBeans = ArrayList<HomeTagBean>()
    private val util = Util()
    private var currentContent: String? = null
    private var position: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_default)
        android.R.color.white.setStatusBarColors()
        title_back.setOnClickListener(this)
        position = intent.getIntExtra(HomeFragment.POSITION, 0)
        title_txt.text = LikeData.main_txt[position]
        homeBeans = LikeData.getHomeTags(position)
        defaultTagAdapter = DefaultTagAdapter(homeBeans, this)
        default_tag_rv.adapter = defaultTagAdapter
        default_tag_rv.layoutManager = LinearLayoutManager(this)
    }

    override fun template(v: View?, position: Int) {
        when (v?.id) {
            R.id.default_copy -> {
                util.payCoin(this, this, 0, 0)
                currentContent = homeBeans[position].res
            }
            R.id.default_favorite -> {
                homeBeans[position].isFavorite = !homeBeans[position].isFavorite
                defaultTagAdapter.setsData(homeBeans)
                if (homeBeans[position].isFavorite) {
                    val favoriteBean = FavoriteBean(position = this.position,
                            index = position, str = homeBeans[position].res)
                    favoriteBean.save()
                } else {
                    Delete.table(FavoriteBean::class.java,
                            FavoriteBean_Table.str.eq(homeBeans[position].res))
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.title_back -> {
                onBackPressed()
            }
        }
    }

    override fun payCoinsCallback(id: Int, position: Int) {
        when (id) {
            0 -> {
                currentContent?.let { util.copy(this, it) }
            }
        }
    }
}
