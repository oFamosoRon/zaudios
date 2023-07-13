package com.ofamosoron.zaudios.ui.composables.player

import android.net.Uri
import androidx.media3.common.MediaItem

data class TrackItem(
    val contentUri: Uri,
    val mediaItem: MediaItem,
    val name: String
)