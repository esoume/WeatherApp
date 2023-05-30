package com.esoume.coding.weatherapp.domain.util

import android.content.Context
import android.location.Geocoder
import java.util.*
import javax.inject.Inject

@Suppress("DEPRECATION")
class LocationInfo @Inject constructor(
    private val context: Context
) {

    fun getCityName(
        latitude: Double,
        longitude: Double
    ):String {
        val geoCoder = Geocoder(context, Locale.getDefault())
        val addresses = geoCoder.getFromLocation(latitude, longitude, 1)
        addresses?.size.let {
            val locality = addresses!![0].locality
            val getAddressLine0 = addresses[0].getAddressLine(0)
            if (getAddressLine0 != null) {
                val strs = getAddressLine0.split(",").toTypedArray()
                if (strs.count() == 1) {
                    if (locality != null && locality != "") {
                        return "$locality, ${strs[0]}"
                    }
                    return strs[0]
                }
                for (str in strs) {
                    var tempStr = str.replace("postalCode", "")
                    if (tempStr != str) {
                        tempStr = tempStr.trim()
                        if (locality != null && locality != "" && locality != tempStr) {
                            return "$locality, $tempStr"
                        }
                        return tempStr
                    }
                }
            }
            if (locality != null) {
                return locality
            }
            return ""
        }
    }
}