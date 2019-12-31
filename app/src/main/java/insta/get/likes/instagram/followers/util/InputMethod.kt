package insta.get.likes.instagram.followers.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import java.util.*


class InputMethod {
    companion object {
        fun closeInputMethod(context: Context, view: View) {
            try {
                //获取输入法的服务
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                //判断是否在激活状态
                if (imm.isActive) {
                    //隐藏输入法!!,
                    imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
                }

            } catch (e: Exception) {
            } finally {
            }

        }

        fun openInputMethod(editText: EditText) {
            val timer = Timer()

            timer.schedule(object : TimerTask() {

                override fun run() {

                    val inputManager = editText

                            .context.getSystemService(

                            Context.INPUT_METHOD_SERVICE) as InputMethodManager

                    inputManager.showSoftInput(editText, 0)

                }

            }, 200)

        }
    }

}