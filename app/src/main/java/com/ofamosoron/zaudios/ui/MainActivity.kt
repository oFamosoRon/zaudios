package com.ofamosoron.zaudios.ui

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.ui.PlayerView
import com.ofamosoron.zaudios.ui.composables.home.Home
import com.ofamosoron.zaudios.ui.composables.home.HomeEvent
import com.ofamosoron.zaudios.ui.composables.home.HomeViewModel
import com.ofamosoron.zaudios.ui.theme.ZaudiosTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ZaudiosTheme {
                val viewModel: HomeViewModel = hiltViewModel()
                val state = viewModel.state.collectAsState()

                if (!hasPermission(context = this)) {
                    RequestPermission { viewModel.onEvent(HomeEvent.ReadFiles) }
                } else {
                    viewModel.onEvent(HomeEvent.ReadFiles)
                }

                AndroidView(
                    factory = { context ->
                        PlayerView(context).also {
                            it.player = viewModel.player
                        }
                    },
                    update = { },
                )

                Home(
                    isPlaying = state.value.isPlaying,
                    audioTrack = state.value.currentAudioTrack,
                    filesList = state.value.files,
                    playClick = { viewModel.onEvent(HomeEvent.PlayTrack) },
                    pauseClick = { viewModel.onEvent(HomeEvent.StopTrack) },
                    nextClick = { /*TODO*/ },
                    previousClick = { /*TODO*/ },
                    onItemClick = { file ->
                        viewModel.onEvent(HomeEvent.SetAudioTrack(file))
                    },
                )
            }
        }
    }

    @Composable
    private fun RequestPermission(
        onPermissionGranted: () -> Unit,
    ) {
        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    onPermissionGranted()
                } else {
                    // TODO
                }
            }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            requestPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        } else {
            val uri = Uri.fromParts("package", this.packageName, null)
            val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri)
            this.startActivity(intent)
        }
    }

    private fun hasPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            val permission = android.Manifest.permission.READ_EXTERNAL_STORAGE
            val hasPermission = ContextCompat.checkSelfPermission(context, permission)
            hasPermission == PackageManager.PERMISSION_GRANTED
        }
    }

}