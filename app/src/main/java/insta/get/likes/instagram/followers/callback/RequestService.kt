package insta.get.likes.instagram.followers.callback

import insta.get.likes.instagram.followers.data.PopularData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RequestService {
    @GET("web/search/topsearch/?context=blended")
    fun getPopularData(@Query("query") name: String): Observable<PopularData>
}