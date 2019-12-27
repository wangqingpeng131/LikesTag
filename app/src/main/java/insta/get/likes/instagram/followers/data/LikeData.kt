package insta.get.likes.instagram.followers.data

import insta.get.likes.instagram.followers.R

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
        fun getHomeBean(): ArrayList<HomeBean> {
            val arrayList = ArrayList<HomeBean>()
            for (e in main_res) {
                val homeBean = HomeBean(e)
                arrayList.add(homeBean)
            }
            return arrayList
        }
    }
}