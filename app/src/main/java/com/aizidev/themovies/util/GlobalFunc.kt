package com.aizidev.themovies.util

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import android.text.format.Formatter
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.aizidev.themovies.R
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import java.util.*

object GlobalFunc {

    @SuppressLint("HardwareIds")
    fun GET_DEVICE_ID(ctx: Context): String {
        return Settings.Secure.getString(
            ctx.applicationContext.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }

    @SuppressLint("HardwareIds")
    fun GET_IP_ADDRESS(mContext: Context): String {
        val wm = mContext.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        return Formatter.formatIpAddress(wm.connectionInfo.ipAddress)
    }

    fun GET_DEVICE_NAME(): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.startsWith(manufacturer)) {
            CAPITALIZE(model)
        } else CAPITALIZE(manufacturer) + " " + model
    }

    private fun CAPITALIZE(str: String): String {
        if (TextUtils.isEmpty(str)) {
            return str
        }
        val arr = str.toCharArray()
        var capitalizeNext = true

        val phrase = StringBuilder()
        for (c in arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c))
                capitalizeNext = false
                continue
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true
            }
            phrase.append(c)
        }
        return phrase.toString()
    }

    fun GET_PREFFERENCE(context: Context?, preffName: String?, preffKey: String?): String? {
        var value: String? = ""
        if (context != null && preffName != null && preffKey != null) {
            val settings = context.getSharedPreferences(preffName, Context.MODE_PRIVATE)
            value = settings.getString(preffKey, "")
        }
        return value
    }

    @SuppressLint("ApplySharedPref", "CommitPrefEdits")
    fun DELETE_PREFFERENCE(mContext: Context?, preffName: String?, preffKey: String?) {
        if (mContext != null && preffName != null && preffKey != null) {
            val settings = mContext.getSharedPreferences(preffName, Context.MODE_PRIVATE)
            val editor = settings.edit()
            editor.remove(preffKey)
            editor.commit()
        }
    }

    @SuppressLint("ApplySharedPref", "CommitPrefEdits")
    fun ADD_PREFFERENCE(
        mContext: Context?,
        preffName: String?,
        preffKey: String?,
        preffVal: String
    ) {
        if (mContext != null && preffName != null && preffKey != null) {
            val settings = mContext.getSharedPreferences(preffName, Context.MODE_PRIVATE)
            val editor = settings.edit()
            editor.putString(preffKey, preffVal)
            editor.commit()
        }
    }

    fun IS_NETWORK_AVAILABLE(mContext: Context?): Boolean {
        if (mContext != null) {
            val connectivityManager =
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo

            return networkInfo != null && networkInfo.isConnected
        }
        return false
    }

    fun SHOW_MESSAGE_ERROR_CONNECTION(mContext: Activity?) {
        if (mContext != null) {
            val view = mContext.window.decorView.rootView
            val snackbar =
                Snackbar.make(view, "Connection error occurred", Snackbar.LENGTH_INDEFINITE)
            snackbar.setAction("Please try again") { snackbar.dismiss() }
            snackbar.show()
        }
    }

    fun SHOW_SNACK_BAR_DEFAULT(context: Context?, message: String, constraints: ConstraintLayout) {
        val snackbar =
            Snackbar.make(constraints, message, Snackbar.LENGTH_INDEFINITE)
        snackbar.setAction("Close", View.OnClickListener {
            snackbar.dismiss()
        })
        if (context != null) {
            snackbar.setActionTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
            val view = snackbar.view
            val text = view.findViewById<TextView>(R.id.snackbar_text)
            text.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
        }
        snackbar.show()
    }

    fun GET_CURENT_LOCATION(context: Context?, activity: Activity): List<Address> {
        var address: List<Address> = arrayListOf()

        // GET MY CURRENT LOCATION
        val mFusedLocation = LocationServices.getFusedLocationProviderClient(activity)
        mFusedLocation.lastLocation.addOnSuccessListener(activity) { location ->

            val geocode = Geocoder(context, Locale.getDefault())
            val addresses = geocode.getFromLocation(
                location.latitude,
                location.longitude,
                1 // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            )
            address = addresses

            return@addOnSuccessListener

//            val address: String = addresses[0]
//                .getAddressLine(0)
            // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

//            val city: String = addresses[0].locality
//            val state: String = addresses[0].adminArea
//            val country: String = addresses[0].countryName
//            val knownName: String = addresses[0].featureName
//            val postalCode: String = addresses[0].postalCode
//
//            val token: String = GlobalFunc.GET_PREFFERENCE(
//                context, KEY_PREFF_AUTHENTICATION, KEY_PREFF_TOKEN
//            ).toString()
//            academyViewModel.getByPosCode(KEY_API_TYPE + token, postalCode)

            // Display in Toast
//            Toast.makeText(
//                context,
//                "Lat : ${location?.latitude} Long : ${location?.longitude}\n"
//                        + "Loction : $address",
//                Toast.LENGTH_LONG
//            ).show()
        }

        return address
    }

}
