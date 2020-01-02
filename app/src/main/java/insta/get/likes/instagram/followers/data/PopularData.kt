package insta.get.likes.instagram.followers.data

data class PopularData(
        val users: List<Any>,
        val places: List<Any>,
        val hashtags: List<Hashtag>,
        val has_more: Boolean,
        val rank_token: String,
        val clear_client_cache: Boolean,
        val status: String
) {
    data class Hashtag(
        val position: Int,
        val hashtag: Hashtag
    ) {
        data class Hashtag(
            val name: String,
            val id: Long,
            val media_count: Int,
            val use_default_avatar: Boolean,
            val profile_pic_url: String,
            val search_result_subtitle: String
        )
    }
}