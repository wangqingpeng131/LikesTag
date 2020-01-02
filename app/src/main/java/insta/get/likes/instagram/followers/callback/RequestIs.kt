package insta.get.likes.instagram.followers.callback

import insta.get.likes.instagram.followers.data.PopularData
import insta.get.likes.instagram.followers.data.SearchBean
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RequestIs(private var callback: MCallBack<Any>) {
    private val a = "https:"
    private val b = "//www"
    private val c = "st"
    private val d = "a"
    private val e = "gram"
    private val f = "om"

    private val DEFAULT_TIME_OUT: Long = 60
    private val loggingInterceptor = HttpLoggingInterceptor()
    private fun getRequestService(): RequestService {
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor)
                .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)//连接 超时时间
                .writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)//写操作 超时时间
                .readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)//读操作 超时时间
                .retryOnConnectionFailure(true).build()
        val mRetrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(client)
                .baseUrl("$a$b.in$c$d$e.c$f/").build()
        return mRetrofit.create(RequestService::class.java)
    }

    fun getName(name: String) {
        getRequestService().getPopularData("#" + name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DisposableObserver<PopularData>() {
                    override fun onError(e: Throwable) {
                        callback.mCallBack(null, e.message.toString())
                    }

                    override fun onNext(t: PopularData) {
                        val names = ArrayList<SearchBean>()
                        try {
                            for (e in t.hashtags) {
                                val namePhotoData = SearchBean()
                                namePhotoData.text = "#${e.hashtag.name} "
                                names.add(namePhotoData)
                            }
                            callback.mCallBack(names)
                        } catch (e: Exception) {
                        }
                    }

                    override fun onComplete() {
                    }
                })
    }
}