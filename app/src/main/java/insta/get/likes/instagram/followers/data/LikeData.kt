package insta.get.likes.instagram.followers.data

//import com.google.gson.Gson
import com.google.gson.Gson
import com.raizlabs.android.dbflow.sql.language.SQLite
import insta.get.likes.instagram.followers.R
import insta.get.likes.instagram.followers.util.Util

class LikeData {
    companion object {
        private val main_res = arrayListOf(
                R.drawable.food_ic,
                R.drawable.coffee_ic,
                R.drawable.social_ic,
                R.drawable.family_ic,
                R.drawable.fitness_ic,
                R.drawable.books_ic,
                R.drawable.popular_ic,
                R.drawable.pet_ic
        )
        val main_txt = arrayListOf(
                "Food",
                "Coffee",
                "Social",
                "Family",
                "Fitness",
                "Books",
                "Popular",
                "Pet"
        )

        fun getEditBeans(): MutableList<EditBean> {
            return SQLite.select().from(EditBean::class.java).queryList()
        }

        fun getFavorites(): MutableList<FavoriteBean> {
            return SQLite.select().from(FavoriteBean::class.java).orderBy(FavoriteBean_Table.id, false).queryList()
        }

        fun getHomeTags(position: Int): ArrayList<HomeTagBean> {
            val homeTagBeans = ArrayList<HomeTagBean>()

            val list = SQLite.select()
                    .from(FavoriteBean::class.java)
                    .where(FavoriteBean_Table.position.eq(position)).queryList()
            val defaultbeans = Gson().fromJson(Util.readTextFromSDcard(), DefaultTags::class.java)
            for ((i, e) in defaultbeans.tclassification[position].tcontent.withIndex()) {
                val homeBean = HomeTagBean()
                homeBean.res = e.ttag
                if (i < list.size) {
                    if (list[i].index == i) {
                        homeBean.isFavorite = true
                    }
                }
                homeTagBeans.add(homeBean)
            }
            return homeTagBeans
        }

        fun getHomeBean(): ArrayList<HomeBean> {
            val arrayList = ArrayList<HomeBean>()
            for (e in main_res) {
                val homeBean = HomeBean(e)
                arrayList.add(homeBean)
            }
            return arrayList
        }

        fun getSearch(list: ArrayList<String>): ArrayList<SearchBean> {
            var arrayList = ArrayList<SearchBean>()
            for ((i, e) in list.withIndex()) {
                val searchBean = SearchBean()

                searchBean.text = e
                arrayList.add(searchBean)
            }
            return arrayList
        }
    }
}