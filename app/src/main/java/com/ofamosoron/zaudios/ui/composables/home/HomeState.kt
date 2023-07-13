package com.ofamosoron.zaudios.ui.composables.home

import java.io.File

data class HomeState(
    val files: List<File> = emptyList(),
    val currentAudioTrack: File? = null,
    val isPlaying: Boolean = false
)
