package insta.get.likes.instagram.followers.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Afra55
 * @date 2019-09-26
 * A smile is the best business card.
 */
class RedeemItemDecoration(private val headSpaceSize: Int, private val spaceSize: Int, private val leftAndRight: Int) : RecyclerView.ItemDecoration() {


    override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
    ) {
        val childAdapterPosition = parent.getChildAdapterPosition(view)
        if (childAdapterPosition == 0) {
            outRect.top = spaceSize
        }
        if (childAdapterPosition == 1) {
            outRect.top = headSpaceSize
        }
        if (childAdapterPosition % 2 == 0) {
            outRect.left = leftAndRight
        } else {
            outRect.right = leftAndRight
        }
        outRect.bottom = spaceSize
    }
}