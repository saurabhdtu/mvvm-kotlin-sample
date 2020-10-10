package com.locus.assignment.utils

import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object FileHelper {

    //    @RequiresPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    @Throws(IOException::class)
    fun createImageFile(fileDir: String): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir= File(fileDir)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        )
    }
}