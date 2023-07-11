package com.ofamosoron.zaudios.ui.composables.home

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
        }
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

        _state.value = _state.value.copy(files = filesList)
    }
}