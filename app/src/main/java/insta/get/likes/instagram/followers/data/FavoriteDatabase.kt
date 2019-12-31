package insta.get.likes.instagram.followers.data


import com.raizlabs.android.dbflow.annotation.Database
import insta.get.likes.instagram.followers.data.FavoriteDatabase.Companion.NAME
import insta.get.likes.instagram.followers.data.FavoriteDatabase.Companion.VERSION

@Database(name = NAME,version = VERSION)
class FavoriteDatabase{
    companion object {
        const val NAME = "LikesTag"
        const val VERSION = 1
    }
}
