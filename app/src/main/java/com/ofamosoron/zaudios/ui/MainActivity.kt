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
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.core.content.ContextCompat
import com.ofamosoron.zaudios.ui.composables.Home
import com.ofamosoron.zaudios.ui.composables.HomeEvent
import com.ofamosoron.zaudios.ui.composables.HomeViewModel
import com.ofamosoron.zaudios.ui.theme.ZaudiosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ZaudiosTheme {
                val viewModel by viewModels<HomeViewModel>()
                val state = viewModel.state.collectAsState()

                if (!hasPermission(context = this)) {
                    RequestPermission { viewModel.onEvent(HomeEvent.ReadFiles) }
                } else {
                    viewModel.onEvent(HomeEvent.ReadFiles)
                }

                Home(state.value.files)
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