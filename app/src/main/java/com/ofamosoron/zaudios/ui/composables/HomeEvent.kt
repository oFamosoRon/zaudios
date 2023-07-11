package com.ofamosoron.zaudios.ui.composables

import android.content.Context

sealed class HomeEvent {
    object ReadFiles: HomeEvent()
    data class RequestPermission(val context: Context): HomeEvent()
    data class CheckPermission(val context: Context): HomeEvent()
}
