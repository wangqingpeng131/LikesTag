package insta.get.likes.instagram.followers.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.util.*

class PermissionUtil {

    fun openCamera(context: Context, activity: Activity) {
        if (ContextCompat.checkSelfPermission(context,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(context,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                            Manifest.permission.READ_CONTACTS)) {
                show(context, "", "Need camera permission to scan QR code!",
                        "OK",
                        DialogInterface.OnClickListener { _, _ ->
                            ActivityCompat.requestPermissions(activity,
                                    arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                                    PERMISSIONS_REQUEST_CAMERA
                            )
                        }
                        , "Cancel", null)
            } else {

                ActivityCompat.requestPermissions(activity,
                        arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        PERMISSIONS_REQUEST_CAMERA
                )
            }
        } else {
            toTakePhoto(context, activity)
        }
    }

    fun openAlbum(context: Context, activity: Activity) {
        if (ContextCompat.checkSelfPermission(context,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                            Manifest.permission.READ_CONTACTS)) {
                show(context, "", "Need camera permission to scan QR code!",
                        "OK",
                        DialogInterface.OnClickListener { _, _ ->
                            ActivityCompat.requestPermissions(activity,
                                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                                    PERMISSIONS_REQUEST_ALBUM
                            )
                        }
                        , "Cancel", null)
            } else {

                ActivityCompat.requestPermissions(activity,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        PERMISSIONS_REQUEST_ALBUM
                )
            }
        } else {
            toAlbum(activity)
        }
    }

    fun toSavePicture(context: Context, activity: Activity, view: ViewGroup) {
        if (ContextCompat.checkSelfPermission(context,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                show(activity,
                        "",
                        "Need file storage permission to save image!",
                        "OK",
                        DialogInterface.OnClickListener { _, _ ->
                            ActivityCompat.requestPermissions(activity,
                                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                                    PERMISSIONS_REQUEST_READ_WRITE_EXTERNAL_STORAGE
                            )
                        }
                        , "Cancel", null)
            } else {
                ActivityCompat.requestPermissions(activity,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        PERMISSIONS_REQUEST_READ_WRITE_EXTERNAL_STORAGE
                )
            }
        } else {
            SavePictureTask(context).execute(view)
        }
    }

    private class SavePictureTask internal constructor(context: Context) : AsyncTask<Any, Int, String>() {
        internal var context: WeakReference<Context>? = null

        init {
            this.context = WeakReference(context)
        }

        override fun doInBackground(vararg objects: Any): String? {
            try {
                var saveFilePath: String? = null
                if (null != context && null != context!!.get()) {
                    val file = context!!.get()?.let { getExternalFile(it) }
                    if (null != file) {
                        saveFilePath = file.path
                        val view = objects[0] as ViewGroup
                        /*for (int i = 0; i < 9; i++) {
                            ViewGroup view = (ViewGroup) objects[0];
                            savePicture(context.get(), view.getChildAt(i));
                        }*/
                        context!!.get()?.let { savePicture(it, getViewBp(view)) }
                    }
                }
                return saveFilePath
            } catch (e: Exception) {
                return null
            }

        }

        override fun onPostExecute(s: String?) {
            super.onPostExecute(s)
            if (null != context && null != context!!.get() && null != s) {
                Toast.makeText(context!!.get(), "Picture has been saved under path \'"
                        + s + "\'", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun toAlbum(activity: Activity) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_REQUEST)
    }

    private fun toTakePhoto(context: Context, activity: Activity) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val cameraSavePath = File(context.cacheDir.absolutePath + "A" + ".jpg")
        var contentUri = FileProvider.getUriForFile(context, Util.getFileProviderName(context), cameraSavePath)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        } else {
            contentUri = Uri.fromFile(cameraSavePath)
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri)
        activity.startActivityForResult(intent, CAMERA_REQUEST)
    }

    companion object {
        const val CAMERA_REQUEST = 52
        const val PICK_REQUEST = 53
        const val PERMISSIONS_REQUEST_CAMERA = 502
        const val PERMISSIONS_REQUEST_ALBUM = 503
        const val PERMISSIONS_REQUEST_READ_WRITE_EXTERNAL_STORAGE = 885

        fun shotActivity(ctx: Activity): Bitmap {
            val view = ctx.window.decorView
            view.isDrawingCacheEnabled = true
            view.buildDrawingCache()
            val bp = Bitmap.createBitmap(view.drawingCache, 0, 0, view.measuredWidth,
                    view.measuredHeight)
            view.isDrawingCacheEnabled = false
            view.destroyDrawingCache()
            return bp
        }
        /*fun getBitmapFromView(view: View, activity: Activity, callback: (Bitmap) -> Unit) {
            activity.window?.let { window ->
                val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
                val locationOfViewInWindow = IntArray(2)
                view.getLocationInWindow(locationOfViewInWindow)
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        PixelCopy.request(window, Rect(locationOfViewInWindow[0], locationOfViewInWindow[1], locationOfViewInWindow[0] + view.width, locationOfViewInWindow[1] + view.height), bitmap, { copyResult ->
                            if (copyResult == PixelCopy.SUCCESS) {
                                callback(bitmap)
                            }
                            // possible to handle other result codes ...
                        }, Handler())
                    }
                } catch (e: IllegalArgumentException) {
                    // PixelCopy may throw IllegalArgumentException, make sure to handle it
                    e.printStackTrace()
                }
            }
        }*/
        private fun getViewBp(v: View?): Bitmap? {
            if (null == v) {
                return null
            }
            v.isDrawingCacheEnabled = true
            v.buildDrawingCache()
            v.measure(View.MeasureSpec.makeMeasureSpec(v.width,
                    View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(
                    v.height, View.MeasureSpec.EXACTLY))
            v.layout(v.x.toInt(), v.y.toInt(),
                    v.x.toInt() + v.measuredWidth,
                    v.y.toInt() + v.measuredHeight)
            val b = Bitmap.createBitmap(v.drawingCache, 0, 0, v.measuredWidth, v.measuredHeight)
            v.isDrawingCacheEnabled = false
            v.destroyDrawingCache()
            return b
        }

        private fun savePicture(context: Context, bitmap: Bitmap?) {
            try {
                var mFile = getExternalFile(context)
                if (mFile != null) {
                    if (mFile.exists()) {
                        mFile = File(mFile, Calendar.getInstance().timeInMillis.toString() + ".png")
                    }
                    val saveFilePath = mFile.path
                    Util.saveBitmap(bitmap, saveFilePath)
                    Util.handleObtainedImage(context, mFile)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }



        private fun loadBitmapFromView(v: View): Bitmap {
            val w = v.width
            val h = v.height

            val bmp = Bitmap.createBitmap(w - 2, h - 2, Bitmap.Config.ARGB_8888)
            val c = Canvas(bmp)
            /* 如果不设置canvas画布为白色，则生成透明  */
            c.drawColor(Color.parseColor("#00FFB2BA"))
            v.draw(c)
            return bmp
        }


        private fun getExternalFile(context: Context): File? {
            val timeString = getCurTimeString(SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SS", Locale.getDefault()))
            val childName = "$timeString.png"

            val permission = ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (permission != PackageManager.PERMISSION_GRANTED) {
                val filesDir = context.getExternalFilesDir("file")
                if (filesDir != null) {
                    val path = filesDir.path
                    return getFile(childName, path)
                }
            } else {
                val filePath = (Environment.getExternalStorageDirectory().absolutePath + "/"
                        + context.packageName + "/")
                return getFile(childName, filePath)
            }
            return null
        }

        private fun getFile(childName: String, path: String): File {
            val mFile = File(path, childName)
            val pFile = mFile.parentFile
            if (!pFile.exists()) {

                pFile.mkdirs()
            }
            return mFile
        }

        private fun getCurTimeString(format: SimpleDateFormat): String {
            return date2String(Date(), format)
        }

        private fun date2String(time: Date, format: SimpleDateFormat): String {
            return format.format(time)
        }

        private fun show(
                context: Context, title: String, msg: String, positiveButtonText: String, positiveButtonListener: DialogInterface.OnClickListener, negativeButtonText: String, negativeButtonListener: DialogInterface.OnClickListener?

        ) {
            val alertDialog = AlertDialog.Builder(context)
                    .setTitle(title)
                    .setMessage(msg)
                    .setPositiveButton(positiveButtonText, positiveButtonListener)
                    .setNegativeButton(negativeButtonText, negativeButtonListener)
                    .create()
            alertDialog.show()
        }
    }
}
