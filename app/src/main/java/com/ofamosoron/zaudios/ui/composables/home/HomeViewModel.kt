package com.ofamosoron.zaudios.ui.composables.home

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.ofamosoron.zaudios.utils.SYSTEM_URL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val player: Player
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        player.prepare()
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.ReadFiles -> handleReadFilesEvent()
            is HomeEvent.PlayTrack -> handlePlayTrackEvent()
            is HomeEvent.StopTrack -> handleStopTrackEvent()
            is HomeEvent.SetAudioTrack -> handleSetAudioTrackEvent(file = event.file)
        }
    }

    private fun handleStopTrackEvent() {
        player.pause()
        _state.value = _state.value.copy(isPlaying = false)
    }

    private fun handlePlayTrackEvent() {
        player.play()
        _state.value = _state.value.copy(isPlaying = true)
    }

    private fun handleSetAudioTrackEvent(file: File) {
        val uri = Uri.fromFile(file)
        val mediaItem = MediaItem.fromUri(uri)
        player.setMediaItem(mediaItem)
        player.play()
        _state.value = _state.value.copy(currentAudioTrack = file, isPlaying = true)
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