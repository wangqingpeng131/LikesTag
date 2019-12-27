package insta.get.likes.instagram.followers.util


import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import android.widget.Toast
import com.android.vending.billing.IInAppBillingService
import org.json.JSONException
import org.json.JSONObject

class GooglePay private constructor(private var callback: Callback?) {
    private lateinit var mServiceConn: android.content.ServiceConnection

    //初始化
    fun initGooglePay(context: Context) {

        mServiceConn = object : android.content.ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName) {
                mService = null
            }

            override fun onServiceConnected(name: ComponentName,
                                            service: IBinder) {
                mService = IInAppBillingService.Stub.asInterface(service)
            }
        }
        val serviceIntent = Intent("com.android.vending.billing.InAppBillingService.BIND")
        serviceIntent.setPackage("com.android.vending")
        context.bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE)
    }

    //销毁
    fun destroyService(context: Context) {
        if (mService != null) {
            context.unbindService(mServiceConn)
        }
        callback = null
    }

    //启动支付
    fun startPay(commodity: Commodity, activity: Activity) {

        val buyIntentBundle: Bundle
        var pendingIntent: PendingIntent? = null
        var a = 0
        if (mService == null) {

            Toast.makeText(activity, "Google Play connection failed. Please turn on Google Play service on your device.", Toast.LENGTH_SHORT).show()
            return
        }
        try {
            buyIntentBundle = mService!!.getBuyIntent(3, commodity.packName,
                    commodity.id, "inapp", commodity.payKey)
            if (null != buyIntentBundle) {
                pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT")
                a = buyIntentBundle.getInt("RESPONSE_CODE")
            }

            if (a == 7) {
                val ownedItems = mService!!.getPurchases(3, commodity.packName, "inapp", null)
                val code = ownedItems.getInt("RESPONSE_CODE")
                Log.e("code", code.toString() + "")
                val arrayList = ownedItems.getStringArrayList("INAPP_PURCHASE_DATA_LIST")
                for (i in arrayList!!.indices) {
                    val jo = JSONObject(arrayList[i])
                    mService!!.consumePurchase(3, jo.getString("packageName"), jo.optString("purchaseToken"))

                }
                Log.e("Tag", arrayList.toString())
                activity.startIntentSenderForResult(pendingIntent!!.intentSender,
                        1001, Intent(), Integer.valueOf(0), Integer.valueOf(0),
                        Integer.valueOf(0))


            } else {
                activity.startIntentSenderForResult(pendingIntent!!.intentSender,
                        1001, Intent(), Integer.valueOf(0), Integer.valueOf(0),
                        Integer.valueOf(0))
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    //支付成功回调
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?, activity: Activity) {
        if (requestCode == 1001) {
            if (data == null) {
                Toast.makeText(activity, "Google Play connection failed. Please turn on Google Play service on your device.", Toast.LENGTH_SHORT).show()
                return
            }
            val purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA")
            if (purchaseData == null) {
                Toast.makeText(activity, "Google Play connection failed. Please turn on Google Play service on your device.", Toast.LENGTH_SHORT).show()
                return
            }

            if (resultCode == RESULT_OK) {
                try {
                    val jo = JSONObject(purchaseData)
                    val sku = jo.getString("productId")
                    callback!!.callBack(sku)
                    //runOnUiThread();
                    Toast.makeText(activity, "Pay Success", Toast.LENGTH_SHORT).show()
                    activity.runOnUiThread {
                        try {
                            mService!!.consumePurchase(3, jo.getString("packageName"), jo.optString("purchaseToken"))
                        } catch (e: RemoteException) {
                            e.printStackTrace()
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }


                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            } else {
                Toast.makeText(activity, "Pay failure", Toast.LENGTH_SHORT).show()

            }
        }
    }

    interface Callback {
        fun callBack(id: String)
    }

    companion object {
        private var mService: IInAppBillingService? = null
        private var instance: GooglePay? = null
        //获取支付工具对象
        fun getGooglePay(callback: Callback): GooglePay {
            if (instance == null) {
                synchronized(GooglePay::class) {
                    if (instance == null) {
                        instance = GooglePay(callback)
                    }
                }
            }
            return instance!!
        }
    }
}
