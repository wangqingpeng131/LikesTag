package insta.get.likes.instagram.followers.data

data class DefaultTags(
        val tclassification: List<Classification>
) {
    data class Classification(
            val tname: String,
            val tcontent: List<Content>
    ) {
        data class Content(
                val tcounty: String?,
                val ttag: String
        )
    }
}