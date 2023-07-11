package com.ofamosoron.zaudios.ui.composables

import java.io.File

data class HomeState(
    val files: List<File> = emptyList(),
    val hasPermission: Boolean = false
)
