package insta.get.likes.instagram.followers.data

import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table

@Table (database = FavoriteDatabase::class)
data class EditBean(@PrimaryKey(autoincrement = true) var id: Int = 0, @Column var str: String? = null)