package com.ofamosoron.zaudios.ui.composables.top_bar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.ofamosoron.zaudios.R
import retrofit2.http.Header

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header() {
    TopAppBar(
        title  = {
            Text(text = stringResource(id = R.string.app_name))
        },
    )
}