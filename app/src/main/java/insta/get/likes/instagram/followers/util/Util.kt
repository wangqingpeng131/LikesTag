package insta.get.likes.instagram.followers.util

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.text.TextUtils
import android.widget.Toast
import insta.get.likes.instagram.followers.BuildConfig
import insta.get.likes.instagram.followers.ui.ShoppingActivity
import java.io.*
import java.text.DecimalFormat


fun Int.toDp(): Int {
    val scale = SaveFavorite.getContext().resources.displayMetrics.density
    return (this * scale + 0.5f).toInt()
}

class Util {
    private var coins: Int = 0
    fun copy(context: Context, text: String) {
        val mClipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val mClipData = ClipData.newPlainText("Label", text)
        mClipboardManager.primaryClip = mClipData
        Toast.makeText(context, "Success", Toast.LENGTH_LONG).show()
    }

    fun payCoin(context: Context, payCoin: PayCoin, id: Int, position: Int) {
        val sp = context.getSharedPreferences(GOLD, Context.MODE_PRIVATE)
        coins = sp.getInt(COIN, DEFAULT_COINS)
        lateinit  var str :String
        val defaultCoins: Int = if (position == 0) {
            str = "Copy the tag spend $COPY_COINS coins."
            COPY_COINS
        } else {
            str = "… will cost $FAVORITE_COINS coins, please confirm to continue."
            FAVORITE_COINS
        }
        if (coins < defaultCoins) {
            val diaLog = AlertDialog.Builder(context)
            diaLog.setMessage("Looks like your coins not enough, would you like to buy more?")
            diaLog.setPositiveButton("OK"
            ) { _, _ -> context.startActivity(Intent(context, ShoppingActivity::class.java)) }
            diaLog.setNegativeButton("Cancel"
            ) { dialog, _ -> dialog.dismiss() }
            diaLog.show()
        } else {
            AlertDialog.Builder(context)
                    .setTitle(str)
                    .setPositiveButton("OK"
                    ) { dialog, _ ->
                        coins -= defaultCoins
                        payCoin.payCoinsCallback(id, position)
                        sp.edit().putInt(COIN, coins).apply()
                        dialog.dismiss()
                    }
                    .setNegativeButton("CANCEL"
                    ) { dialog, _ -> dialog.dismiss() }.show()
        }
    }

    interface PayCoin {
        fun payCoinsCallback(id: Int, position: Int)
    }

    companion object {
        const val GOLD = "gold"
        const val COIN = "coin"
        const val MINI = "insta.get.likes.instagram.followers.newhello"
        const val LARGE = "insta.get.likes.instagram.followers.newsmall"
        const val SUPER = "insta.get.likes.instagram.followers.newnormal"
        const val HAPPY = "insta.get.likes.instagram.followers.newstandard"
        const val STACK = "insta.get.likes.instagram.followers.newspecial"
        const val STANDARD = "insta.get.likes.instagram.followers.newlimited"
        const val TEAM = "insta.get.likes.instagram.followers.newextra"
        const val POND = "insta.get.likes.instagram.followers.newsuper"
        const val IAP = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArFlG1YXnjUPM9A02y8ScrauCQj8vihiq5s1V+8FT13KYHzkMXToB6ThuRvL4CRbLhq41fv+KJGjWxWhqkGc0C7dYGBveoKqopWSQiENl1+svyiWO1qIRdEeuPTWgut/Yu/cwWJPhxFQPidtuCTkwHQIVphPY0hX2OR6rcy6ZSQ+pDnTaMHN4WMKZR6nOm/co/wlSfw3Y/CCt/1PAE5YMDF9hUMD7wGOzKkxWzi/qIOkQMatS1CQaeHiJfCi0cgSPlG3JtAHIe41qxQsj7CqWq8NlXvR/I+XC3ADkIvrOaUuEl2L8KLE1TWLbAyA92bAbgL5wAg9NnmUgCCaaHFMFdwIDAQAB"
        const val NUM_ONE = 400
        const val NUM_TWO = 800
        const val NUM_THREE = 1500
        const val NUM_HAPPY = 3560
        const val NUM_STACK = 6720
        const val NUM_STANDARD = 8120
        const val NUM_TEAM = 11480
        const val NUM_POND = 14080
        private const val COPY_COINS = 30
        private const val FAVORITE_COINS = 20
        private var DEFAULT_COINS = if (BuildConfig.DEBUG || BuildConfig.ProductDebug) {
            300
        } else {
            100
        }

        fun convert(num: Int): String {
            val df = DecimalFormat()
            if (num < 1000) {
                return num.toString()
            }
            var convertNum = num.toDouble() / 1000
            return if (convertNum - 1000 >= 0) {
                convertNum /= 1000
                df.applyPattern("0.0M")
                df.format(convertNum)

            } else {
                df.applyPattern("0.0K")
                df.format(convertNum)
            }
        }

        fun getCoins(): String {
            val shared = SaveFavorite.getContext().getSharedPreferences(GOLD, Context.MODE_PRIVATE)
            val num = shared.getInt(COIN, DEFAULT_COINS)
            return convert(num)
        }

        fun buyCoins(coins: Int): String {
            val shared = SaveFavorite.getContext().getSharedPreferences(GOLD, Context.MODE_PRIVATE)
            var num = shared.getInt(COIN, DEFAULT_COINS)
            val editor = shared.edit()
            num += coins
            editor.putInt(COIN, num)
            editor.apply()
            return num.toString()
        }

        internal fun saveBitmap(bitmap: Bitmap?, path: String) {
            if (bitmap == null || TextUtils.isEmpty(path)) {
                return
            }

            var bos: BufferedOutputStream? = null
            var fos: FileOutputStream? = null
            try {
                fos = FileOutputStream(path)
                bos = BufferedOutputStream(fos)
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, bos)

            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } finally {
                try {
                    bos?.close()
                    if (null != fos) {
                        fos.flush()
                        fos.close()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                bitmap.recycle()
            }
        }

        internal fun handleObtainedImage(context: Context, file: File) {
            val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            val contentUri = Uri.fromFile(file)
            mediaScanIntent.data = contentUri
            context.sendBroadcast(mediaScanIntent)
        }

        /*fun getRealPathFromUri(context: Context, uri: Uri): String? {
            val sdkVersion = Build.VERSION.SDK_INT
            return if (sdkVersion >= 19) {
                getRealPathFromUriAboveApi19(context, uri)
            } else {
                getRealPathFromUriBelowAPI19(context, uri)
            }
        }*/

        /*private fun getRealPathFromUriBelowAPI19(context: Context, uri: Uri): String? {
            return getDataColumn(context, uri, null, null)
        }*/

        fun readTextFromSDcard(): String? {
            val inputStreamReader: InputStreamReader
            var resultString: String? = null
            try {
                inputStreamReader = InputStreamReader(SaveFavorite.getContext().assets.open("tag.json"), "UTF-8")
                val bufferedReader = BufferedReader(
                        inputStreamReader)
                var line: String?
                val stringBuilder = StringBuilder()
                while (bufferedReader.readLine().also { line = it } != null) {
                    stringBuilder.append(line)
                }
                inputStreamReader.close()
                bufferedReader.close()
                resultString = stringBuilder.toString()
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return resultString
        }

        internal fun getFileProviderName(context: Context): String {
            return context.packageName + ".fileprovider"
        }

        /*@TargetApi(Build.VERSION_CODES.KITKAT)
        private fun getRealPathFromUriAboveApi19(context: Context, uri: Uri): String? {
            var filePath: String? = null
            if (DocumentsContract.isDocumentUri(context, uri)) {
                val documentId = DocumentsContract.getDocumentId(uri)
                if (isMediaDocument(uri)) {
                    val id = documentId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
                    val selection = MediaStore.Images.Media._ID + "=?"
                    val selectionArgs = arrayOf(id)
                    filePath = getDataColumn(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, selectionArgs)
                } else if (isDownloadsDocument(uri)) {
                    val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(documentId))
                    filePath = getDataColumn(context, contentUri, null, null)
                }
            } else if ("content".equals(uri.scheme!!, ignoreCase = true)) {
                filePath = getDataColumn(context, uri, null, null)
            } else if ("file" == uri.scheme) {
                filePath = uri.path
            }
            return filePath
        }*/

        /* private fun isDownloadsDocument(uri: Uri): Boolean {
             return "com.android.providers.downloads.documents" == uri.authority
         }

         private fun isMediaDocument(uri: Uri): Boolean {
             return "com.android.providers.media.documents" == uri.authority
         }*/

        /*private fun getDataColumn(context: Context, uri: Uri, selection: String?, selectionArgs: Array<String>?): String? {
            var path: String? = null
            val projection = arrayOf(MediaStore.Images.Media.DATA)
            var cursor: Cursor? = null
            try {
                cursor = context.contentResolver.query(uri, projection, selection, selectionArgs, null)
                if (cursor != null && cursor.moveToFirst()) {
                    val columnIndex = cursor.getColumnIndexOrThrow(projection[0])
                    path = cursor.getString(columnIndex)
                }
            } catch (e: Exception) {
                cursor?.close()
            }

            return path
        }*/
    }
}
