package com.ofamosoron.zaudios.ui.composables.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ofamosoron.zaudios.ui.theme.ZaudiosTheme
import com.ofamosoron.zaudios.R

@Composable
fun AudioItem(
    audioName: String,
    date: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(8.dp)
            .clickable {
                onClick()
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_sound),
                tint = MaterialTheme.colorScheme.background,
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.tertiary)
                    .padding(16.dp)
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Column {
                Text(
                    text = audioName,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 18.sp
                )
                Text(text = date, color = MaterialTheme.colorScheme.onBackground, fontSize = 14.sp)
            }
        }
    }
}

@Preview
@Composable
fun PreviewAudioItem() {
    ZaudiosTheme {
        AudioItem(audioName = "WAV-123-123.mp3", "11/06/2023", { })
    }
}