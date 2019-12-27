package insta.get.likes.instagram.followers.data

data class HomeBean(var res: Int = 0)
data class SearchBean(var isSelect: Boolean = false, var res: Int = 0, var text: String? = null)
data class EditBean(var str: String? = null)
data class FavoriteBean(var str: String)
