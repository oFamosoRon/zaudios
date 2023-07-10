package com.ofamosoron.zaudios

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.ofamosoron.zaudios.ui.theme.ZaudiosTheme
import java.io.File

private const val SYSTEM_URL =
    "/storage/emulated/0/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Audio"

class MainActivity : ComponentActivity() {

    private fun checkPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            val permission = android.Manifest.permission.READ_EXTERNAL_STORAGE
            val hasPermission = ContextCompat.checkSelfPermission(this, permission)
            hasPermission == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestPermission(
        onPermissionGranted: () -> Unit,
        onPermissionDenied: () -> Unit
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ZaudiosTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val permissionGranted = remember { mutableStateOf(checkPermission()) }

                    if (permissionGranted.value) {
                        val directoryPath = SYSTEM_URL
                        val directory = File(directoryPath)

                        val filesList = mutableListOf<File>()

                        directory.walkTopDown().forEach { file ->
                            if (file.isFile) {
                                filesList.add(file)
                            }
                        }
                    } else {
                        requestPermission(
                            onPermissionGranted = { permissionGranted.value = true },
                            onPermissionDenied = { /* Handle permission denied */ }
                        )
                    }
                }
            }
        }
    }
}