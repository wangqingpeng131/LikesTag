package insta.get.likes.instagram.followers.util

import android.content.Context

class SaveFavorite {
    companion object {
        private lateinit var context: Context
        fun getContext(): Context {
            return context
        }

        fun setContext(context: Context) {
            SaveFavorite.context = context
        }
    }
}