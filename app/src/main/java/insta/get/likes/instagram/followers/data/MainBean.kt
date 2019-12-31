package insta.get.likes.instagram.followers.data


data class HomeBean(var res: Int = 0)
data class HomeTagBean(var id: Int = 0,
                       var res: String = "", var isFavorite: Boolean = false)
data class SearchBean(var isSelect: Boolean = false, var res: Int = 0, var text: String? = null)
