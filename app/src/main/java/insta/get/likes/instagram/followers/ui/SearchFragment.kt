package insta.get.likes.instagram.followers.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.raizlabs.android.dbflow.kotlinextensions.save
import com.raizlabs.android.dbflow.sql.language.SQLite
import insta.get.likes.instagram.followers.R
import insta.get.likes.instagram.followers.adapter.ItemDecoration
import insta.get.likes.instagram.followers.adapter.SearchAdapter
import insta.get.likes.instagram.followers.callback.MCallBack
import insta.get.likes.instagram.followers.callback.RequestIs
import insta.get.likes.instagram.followers.callback.TemplateCallback
import insta.get.likes.instagram.followers.data.FavoriteBean
import insta.get.likes.instagram.followers.data.FavoriteBean_Table
import insta.get.likes.instagram.followers.data.SearchBean
import insta.get.likes.instagram.followers.util.InputMethod
import insta.get.likes.instagram.followers.util.Util
import insta.get.likes.instagram.followers.util.toDp

class SearchFragment : Fragment(), TemplateCallback, View.OnClickListener, MCallBack<Any>, Util.PayCoin {

    private var mActivity: Activity? = null
    private lateinit var searchEd: EditText
    private lateinit var searchPay: ConstraintLayout
    private lateinit var searchCopy: ImageView
    private lateinit var searchFavorite: ImageView
    private lateinit var loadBar: ProgressBar
    private val util = Util()
    private var searchBeans = ArrayList<SearchBean>()
    private lateinit var searchAdapter: SearchAdapter<SearchBean>
    private var positionList = ArrayList<Int>()
    private var currentContent: String? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as Activity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_search, container, false)
        val searchRv = root.findViewById<RecyclerView>(R.id.search_rv)
        searchPay = root.findViewById(R.id.search_pay)
        searchEd = root.findViewById(R.id.search_edit)
        searchCopy = root.findViewById(R.id.search_copy)
        searchFavorite = root.findViewById(R.id.search_favorite)
        loadBar = root.findViewById(R.id.load_pb)
        val searchBtn = root.findViewById<ImageView>(R.id.search_btn)
        searchBtn.setOnClickListener(this)
        searchCopy.setOnClickListener(this)
        searchFavorite.setOnClickListener(this)
        searchEd.setHintTextColor(ContextCompat.getColor(mActivity as Context, R.color.pink))
        searchRv.layoutManager = LinearLayoutManager(mActivity as Context)
        searchAdapter = SearchAdapter(searchBeans, this)
        searchEd.isFocusable = true
        searchEd.isFocusableInTouchMode = true
        InputMethod.openInputMethod(searchEd)
        searchRv.addItemDecoration(ItemDecoration(180.toDp()))
        searchRv.adapter = searchAdapter
        return root
    }

    override fun onResume() {
        super.onResume()
        InputMethod.openInputMethod(searchEd)
    }

    override fun template(v: View?, position: Int) {
        when (v?.id) {
            R.id.checkbox_group -> {
                searchBeans[position].isSelect = !searchBeans[position].isSelect
                if (searchBeans[position].isSelect) {
                    positionList.add(position)
                } else {
                    positionList.remove(position)
                }
                if (positionList.isEmpty()) {
                    searchPay.visibility = View.GONE
                } else {
                    searchPay.visibility = View.VISIBLE
                }
                searchAdapter.setsData(searchBeans)
            }

        }
    }

    override fun onDetach() {
        super.onDetach()
        mActivity = null
    }

    override fun mCallBack(num: Any?) {
        searchBeans = num as ArrayList<SearchBean>
        loadBar.visibility = View.GONE
        searchAdapter.setsData(searchBeans)
    }

    override fun mCallBack(num: Any?, name: String) {
        loadBar.visibility = View.GONE
        Log.i("dss", "name : $name")
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.search_btn -> {
                val name = searchEd.text.toString()
                if (name.isNotEmpty()) {
                    RequestIs(this).getName(name)
                }
                InputMethod.closeInputMethod(mActivity as Context, searchEd)
                loadBar.visibility = View.VISIBLE
            }
            R.id.search_copy -> {
                val stringBuilder = StringBuilder()
                for (e in positionList) {
                    stringBuilder.append("${searchBeans[e].text} ")
                }
                currentContent = stringBuilder.toString()
                util.payCoin(mActivity as Context, this, 0, 0)
            }
            R.id.search_favorite -> {
                val stringBuilder = StringBuilder()
                for (e in positionList) {
                    stringBuilder.append("${searchBeans[e].text} ")
                }
                currentContent = stringBuilder.toString()
                util.payCoin(mActivity as Context, this, 1, 1)
            }
        }
    }

    override fun payCoinsCallback(id: Int, position: Int) {
        when (id) {
            0 -> {
                util.copy(mActivity as Context, currentContent!!)
            }
            1 -> {
                val list = SQLite.select()
                        .from(FavoriteBean::class.java)
                        .where(FavoriteBean_Table.str.eq(currentContent)).queryList()
                if (list.isEmpty()) {
                    val favoriteBean = FavoriteBean(position = -1, index = -1, str = currentContent!!)
                    favoriteBean.save()
                    Toast.makeText(mActivity, "Success", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(mActivity, "has exist", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}