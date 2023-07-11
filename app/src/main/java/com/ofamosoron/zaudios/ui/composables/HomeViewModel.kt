package com.ofamosoron.zaudios.ui.composables

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.ofamosoron.zaudios.utils.SYSTEM_URL
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File

class HomeViewModel : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.ReadFiles -> handleReadFilesEvent()
            is HomeEvent.CheckPermission -> handleCheckPermissionEvent(context = event.context)
            is HomeEvent.RequestPermission -> handleRequestPermissionEvent()
        }
    }

    private fun handleCheckPermissionEvent(context: Context) {
        val hasPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            val permission = android.Manifest.permission.READ_EXTERNAL_STORAGE
            val hasPermission = ContextCompat.checkSelfPermission(context, permission)
            hasPermission == PackageManager.PERMISSION_GRANTED
        }
        _state.value = _state.value.copy(hasPermission = hasPermission)
    }

    private fun handleRequestPermissionEvent() {

    }

    private fun handleReadFilesEvent() {
        val directoryPath = SYSTEM_URL
        val directory = File(directoryPath)

        val filesList = mutableListOf<File>()

        directory.walkTopDown().forEach { file ->
            if (file.isFile) {
                filesList.add(file)
            }
        }
    }
}