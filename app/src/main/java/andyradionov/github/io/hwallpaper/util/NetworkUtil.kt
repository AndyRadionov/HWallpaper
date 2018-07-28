package andyradionov.github.io.hwallpaper.util

import android.content.Context
import android.net.ConnectivityManager

/**
 * @author Andrey Radionov
 */

/**
 * Check if Internet is Available on device
 *
 * @param context of application
 * @return internet status
 */
fun isInternetAvailable(context: Context): Boolean {
    val mConMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    return mConMgr.activeNetworkInfo != null
            && mConMgr.activeNetworkInfo.isAvailable
            && mConMgr.activeNetworkInfo.isConnected
}