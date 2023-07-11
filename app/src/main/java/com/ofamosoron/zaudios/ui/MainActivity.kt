package com.ofamosoron.zaudios.ui

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
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.ofamosoron.zaudios.ui.composables.Home
import com.ofamosoron.zaudios.ui.composables.HomeEvent
import com.ofamosoron.zaudios.ui.composables.HomeViewModel
import com.ofamosoron.zaudios.ui.theme.ZaudiosTheme
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ZaudiosTheme {
                val viewModel by viewModels<HomeViewModel>()
                val state = viewModel.state.collectAsState()

                viewModel.onEvent(HomeEvent.CheckPermission(context = this))

                if (!state.value.hasPermission) {
                    requestPermission(
                        onPermissionGranted = {
                            viewModel.onEvent(HomeEvent.CheckPermission(context = this))
                        }
                    )
                }
                Home(state.value.files)
            }
        }
    }

    private fun requestPermission(
        onPermissionGranted: () -> Unit,
        onPermissionDenied: () -> Unit = { }
    ) {
        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    onPermissionGranted()
                } else {
                    onPermissionDenied()
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

}