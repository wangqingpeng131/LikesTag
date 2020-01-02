package insta.get.likes.instagram.followers.callback



interface MCallBack<T> {
    fun mCallBack(num: T?, name: String){}
    fun mCallBack(num: T?){}
}