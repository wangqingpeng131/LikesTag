package insta.get.likes.instagram.followers.data

import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table

@Table(database = FavoriteDatabase::class)
data class FavoriteBean(@PrimaryKey(autoincrement = true) var id: Int = 0,
                        @Column var position: Int = 0, @Column var index: Int = 0, @Column var str: String = "")