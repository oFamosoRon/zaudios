package com.ofamosoron.zaudios.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import java.io.File

@Composable
fun Home(
    filesList: List<File>
) {
    Scaffold(
        topBar = { Header() },
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues.calculateTopPadding())
        ) {
            if (filesList.isEmpty()) {

            } else {
                LazyColumn {
                    items(items = filesList) {
                        AudioItem(audioName = "Teste")
                    }
                }
            }
        }
    }
}