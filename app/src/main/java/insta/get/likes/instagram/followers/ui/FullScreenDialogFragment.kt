package insta.get.likes.instagram.followers.ui

import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment

import insta.get.likes.instagram.followers.R
import insta.get.likes.instagram.followers.util.InputMethod


class FullScreenDialogFragment : DialogFragment(), View.OnClickListener, DialogInterface {
    override fun cancel() {

    }

    private var enoughCoins: Boolean = true
    private var listener: OnClickListener? = null

    interface OnClickListener {

        fun onClick(string: String?)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.edit_m_ok -> {
                this.listener?.onClick("#${editText.text}")
                context?.let { InputMethod.closeInputMethod(it,editText) }
            }
            R.id.edit_m_cancel -> {
                dismiss()
                context?.let { InputMethod.closeInputMethod(it,editText) }
            }
        }
    }

    fun setOkButton(listener: OnClickListener) {
        this.listener = listener
    }

    private lateinit var editText: EditText
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.edit_dialog, container, false)
        val close = view.findViewById<ImageView>(R.id.edit_m_cancel)
        val ok = view.findViewById<ImageView>(R.id.edit_m_ok)
        editText = view.findViewById(R.id.dialog_edit)
        close.setOnClickListener(this)
        ok.setOnClickListener(this)
        InputMethod.openInputMethod(editText)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        super.onActivityCreated(savedInstanceState)
        dialog?.window?.setBackgroundDrawable(context?.let { ContextCompat.getColor(it, R.color.di_bl_bg) }?.let { ColorDrawable(it) })
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)

    }

}