package insta.get.likes.instagram.followers.util

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.text.TextUtils
import insta.get.likes.instagram.followers.ui.ShoppingActivity
import java.io.*
import java.text.DecimalFormat

class Util {
    private var coins: Int = 0

    fun payCoin(context: Context, payCoin: PayCoin, id: Int, position: Int) {
        val sp = context.getSharedPreferences(GOLD, Context.MODE_PRIVATE)
        coins = sp.getInt(COIN, DEFAULT_COINS)
        if (coins < PAY_COINS) {
            val diaLog = AlertDialog.Builder(context)
            diaLog.setMessage("Not enough coins, would you like to get more?")
            diaLog.setPositiveButton("OK"
            ) { _, _ -> context.startActivity(Intent(context, ShoppingActivity::class.java)) }
            diaLog.setNegativeButton("Cancel"
            ) { dialog, _ -> dialog.dismiss() }
            diaLog.show()
        } else {
            AlertDialog.Builder(context)
                    .setTitle("This item will cost $PAY_COINS coins.")
                    .setPositiveButton("OK"
                    ) { dialog, _ ->
                        coins -= PAY_COINS
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
        const val MINI = "insta.get.likes.instagram.followers.small"
        const val LARGE = "insta.get.likes.instagram.followers.middle"
        const val SUPER = "insta.get.likes.instagram.followers.large"
        const val IAP = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAs7iTa8QlkieKq8Qc4QrwhuP1K512IGjy+PkD7CdvtNe4DqEg61fdlF6Qp7Er+xDO55krI1Iwm3+HaoF/K15kScoFg2ll87ZUM+q+DKj+vC0bV+PUIJUJSHGeMMDto9GP1zS/z/IellbPXs/g73zEGD5pwbL8hqprveqU9bfAKZYMy+DCJBfyzp4r6yl+Jg9BEs/iQYJKlS9sXlGSIEq1Pkr2WywvWa8xsWVjZWISNxkp0KkJz34W2vl5nRoveuyqEq7NxEkdp02tfBJvB2bF9DHbNlpyXVAgeN0k0uZ1tLsMzHzpphW8sDGSXi/6ZfE3pzevGt9eQ9wZIInfBBH4sQIDAQAB"
        const val NUM_ONE = 400
        const val NUM_TWO = 900
        const val NUM_THREE = 2000
        const val DEFAULT_COINS = 150
        private const val PAY_COINS = 50

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

        fun getCoins(context: Context): String {
            val shared = context.getSharedPreferences(GOLD, Context.MODE_PRIVATE)
            val num = shared.getInt(COIN, DEFAULT_COINS)
            return convert(num)
        }

        fun buyCoins(context: Context, coins: Int): String {
            val shared = context.getSharedPreferences(GOLD, Context.MODE_PRIVATE)
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