package com.ofamosoron.zaudios.ui.composables.home

import java.io.File

sealed class HomeEvent {
    object ReadFiles : HomeEvent()
    object PlayTrack : HomeEvent()
    object StopTrack : HomeEvent()
    data class SetAudioTrack(val file: File) : HomeEvent()
}
