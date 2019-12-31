package insta.get.likes.instagram.followers.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.library.flowlayout.FlowLayoutManager
import com.library.flowlayout.SpaceItemDecoration
import com.raizlabs.android.dbflow.kotlinextensions.delete
import com.raizlabs.android.dbflow.kotlinextensions.save
import insta.get.likes.instagram.followers.R
import insta.get.likes.instagram.followers.adapter.EditAdapter
import insta.get.likes.instagram.followers.callback.TemplateCallback
import insta.get.likes.instagram.followers.data.EditBean
import insta.get.likes.instagram.followers.data.LikeData
import insta.get.likes.instagram.followers.util.Util
import insta.get.likes.instagram.followers.util.toDp
import java.lang.StringBuilder

class EditFragment : Fragment(), TemplateCallback, View.OnClickListener, Util.PayCoin {
    private lateinit var addTags: ConstraintLayout
    private lateinit var editAdapter: EditAdapter<EditBean>
    private lateinit var editbeans: MutableList<EditBean>
    private lateinit var editRv: RecyclerView
    private lateinit var editPlay: ConstraintLayout
    private var mActivity: Activity? = null
    private val util = Util()
    private var currentContent: String? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as Activity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_edit, container, false)
        addTags = root.findViewById(R.id.add_tag_group)
        editRv = root.findViewById(R.id.edit_rv)
        editPlay = root.findViewById(R.id.edit_pay)
        addTags.setOnClickListener(this)
        editbeans = LikeData.getEditBeans()
        if (editbeans.isEmpty()) {
            editPlay.visibility = View.GONE
            editRv.visibility = View.GONE
        }
        editRv.layoutManager = FlowLayoutManager()
        editAdapter = EditAdapter(editbeans, this)
        editRv.adapter = editAdapter
        editRv.addItemDecoration(SpaceItemDecoration(7.toDp()))
        return root
    }


    override fun template(v: View?, position: Int) {
        when (v?.id) {
            R.id.delete_iv -> {
                editbeans[position].delete()
                editbeans.removeAt(position)
                if (editbeans.isEmpty()) {
                    editPlay.visibility = View.GONE
                    editRv.visibility = View.GONE
                }
                editAdapter.setsData(editbeans)
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        mActivity = null
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.add_tag_group -> {
                val fullScreenDialogFragment = FullScreenDialogFragment()
                fullScreenDialogFragment.setOkButton(object : FullScreenDialogFragment.OnClickListener {
                    override fun onClick(string: String?) {
                        val editBean = EditBean()
                        editBean.str = string
                        editBean.save()
                        editbeans.add(editBean)
                        editAdapter.setsData(editbeans)
                        editPlay.visibility = View.VISIBLE
                        editRv.visibility = View.VISIBLE
                        fullScreenDialogFragment.dismiss()
                    }
                })
                fullScreenDialogFragment.show((context as AppCompatActivity).supportFragmentManager, "d")
            }
            R.id.edit_copy -> {
                util.payCoin(mActivity as Context, this, 0, 0)
                val stringBuilder = StringBuilder()
                for (e in editbeans) {
                    stringBuilder.append(e.str)
                }
                currentContent = stringBuilder.toString()
            }
            R.id.edit_favorite -> {
            }
        }
    }
    override fun payCoinsCallback(id: Int, position: Int) {
        when (id) {
            0 -> {
                util.copy(mActivity as Context, currentContent!!)
            }
        }
    }
}