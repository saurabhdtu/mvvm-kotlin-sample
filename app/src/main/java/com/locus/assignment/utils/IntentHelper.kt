package com.locus.assignment.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.core.content.FileProvider
import com.locus.assignment.models.constants.RequestCodes
import java.io.File
import java.io.IOException

object IntentHelper {

    fun launchCamera(activity: Activity, fileDir: String): String? {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(activity.packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    /* if (ActivityCompat.checkSelfPermission(
                             context,
                             Manifest.permission.WRITE_EXTERNAL_STORAGE
                         ) != PackageManager.PERMISSION_GRANTED
                     ) {
                         return
                     }*/
                    FileHelper.createImageFile(fileDir)
                } catch (ex: IOException) {
                    null
                }
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        activity,
                        "com.locus.assignment.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
//                    takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)

                    activity.startActivityForResult(
                        takePictureIntent,
                        RequestCodes.CAMERA_REQUEST_CODE
                    )
                }
                return photoFile?.absolutePath
            }
        }
        return null
    }
}