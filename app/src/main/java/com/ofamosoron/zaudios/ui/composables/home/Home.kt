package com.ofamosoron.zaudios.ui.composables.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.media3.common.Player
import com.ofamosoron.zaudios.ui.composables.utils.AudioItem
import com.ofamosoron.zaudios.ui.composables.top_bar.Header
import com.ofamosoron.zaudios.ui.composables.player.Player
import java.io.File
import java.time.Instant

@Composable
fun Home(
    filesList: List<File>,
    audioTrack: File?,
    onItemClick: (file: File) -> Unit,
    isPlaying: Boolean = false,
    playClick: () -> Unit,
    pauseClick: () -> Unit,
    nextClick: () -> Unit,
    previousClick: () -> Unit,
) {

    Scaffold(
        topBar = { Header() },
        bottomBar = {
            Player(
                playClick = { playClick() },
                pauseClick = { pauseClick() },
                nextClick = { nextClick() },
                previousClick = { previousClick() },
                audioTrack = audioTrack,
                isPlaying = isPlaying,
            )
        }
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            if (filesList.isEmpty()) {
                Text(text = "No files")
            } else {
                LazyColumn {
                    items(items = filesList) { file ->
                        AudioItem(
                            audioName = file.name,
                            date = Instant.ofEpochMilli(file.lastModified()).toString()
                        ) {
                            onItemClick(file)
                        }
                    }
                }
            }
        }
    }
}