package com.esoume.coding.weatherapp.presentation

import android.content.Context
import android.net.Uri

interface DeeplinkHandler {

    fun isSatisfiedBy(data : Uri):Boolean;
    fun execute(context: Context, uri: Uri)
}