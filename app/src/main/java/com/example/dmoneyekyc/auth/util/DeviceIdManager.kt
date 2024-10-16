package com.example.dmoney.auth.util

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import androidx.core.app.ActivityCompat
import com.example.dmoney.auth.domain.model.FingerprintData
import com.example.dmoney.auth.domain.model.NetworkInfo
import com.example.dmoney.auth.domain.model.SystemDefault
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.Inet4Address
import java.net.NetworkInterface
import java.net.SocketException

class DeviceIdManager(private val context: Context) {

    /**
     * Returns the Android ID, which is a unique 64-bit number that's randomly generated when the user first boots their device.
     */
    fun getAndroidId(): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    /**
     * Returns the device name, which is a human-readable name for the device.
     */
    fun getDeviceName(): String {
        return Build.MANUFACTURER + " " + Build.MODEL
    }


    /**
     * Returns the OS version, which includes the version number and codename.
     */
    fun getOsVersion(): String {
        return "Android " + Build.VERSION.RELEASE + " (" + Build.VERSION.CODENAME + ")"
    }

    /**
     * Returns the app version, which includes the version name and code.
     */
    fun getAppVersion(): String {
        val packageName = context.packageName
        val packageManager = context.packageManager
        var appVersion = ""

        try {
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            appVersion = "v" + packageInfo.versionName + " (" + packageInfo.versionCode + ")"
        } catch (e: PackageManager.NameNotFoundException) {
            // Handle exception
        }

        return appVersion
    }


    /**
     * Returns the manufacturer of the device.
     */
    fun getManufacturer(): String {
        return Build.MANUFACTURER
    }

    /**
     * Returns the model of the device.
     */
    fun getModel(): String {
        return Build.MODEL
    }

    /**
     * Returns the IP address of the device.
     */
    fun getIpAddress(): String? {
        return try {
            val networkInterface = NetworkInterface.getByName("wlan0")
            if (networkInterface != null) {
                val inetAddresses = networkInterface.inetAddresses
                while (inetAddresses.hasMoreElements()) {
                    val inetAddress = inetAddresses.nextElement()
                    if (!inetAddress.isLoopbackAddress) {
                        return inetAddress.hostAddress
                    }
                }
                "No valid IP address found"
            } else {
                "Network interface not found"
            }
        } catch (e: SocketException) {
            // Handle exception
            "Failed to get IP address: ${e.message}"
        }
    }

    /**
     * Returns the Wi-Fi SSID of the device.
     */
    fun getWifiSsid(): String? {
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiManager.connectionInfo
        return wifiInfo.ssid
    }

    fun isGpsEnabled(): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }



    @SuppressLint("MissingPermission")
    fun getLastKnownLocation(
        onLocationReceived: (Location?) -> Unit,
    ) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            onLocationReceived(location)
        }


    }


    /**
     * Gathers all the necessary data and returns a SystemDefault object.
     */
    fun getSystemDefault(publicKey: String = "publicKey", clientId: String="clientId" ,
                         latitude:Double? = 0.0,
                         longitude:Double?=0.0): SystemDefault {


        val location = com.example.dmoney.auth.domain.model.Location(latitude, longitude)
        val networkInfo = NetworkInfo(
            networkOperator = "getNetworkOperator()",
            mobileNumber = "getMobileNumber()",
            ipAddress = getIpAddress()!!,
            wifiSsid = getWifiSsid()!!
        )
        val fingerprintData = FingerprintData(
            osVersion = getOsVersion(),
            appVersion = getAppVersion(),
            manufacturer = getManufacturer(),
            model = getModel()
        )

        return SystemDefault(
            deviceId = getAndroidId(),
            deviceName = getDeviceName(),
            publicKey = publicKey,
            fingerprintData = fingerprintData,
            networkInfo = networkInfo,
            location = location,
            client_id = clientId
        )
    }


}


