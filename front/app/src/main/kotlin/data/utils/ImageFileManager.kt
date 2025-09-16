package com.manager1700.soccer.data.utils

import android.content.Context
import android.net.Uri
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.UUID

class ImageFileManager(private val context: Context) {

    companion object {
        private const val IMAGES_FOLDER = "player_images"
        private const val TAG = "ImageFileManager"
    }

    /**
     * Save image from URI to internal storage and return the local file path
     */
    suspend fun saveImageFromUri(uri: Uri): String? {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            if (inputStream == null) {
                Log.e(TAG, "Could not open input stream for URI: $uri")
                return null
            }

            // Create images directory if it doesn't exist
            val imagesDir = File(context.filesDir, IMAGES_FOLDER)
            if (!imagesDir.exists()) {
                imagesDir.mkdirs()
            }

            // Generate unique filename
            val fileName = "player_${UUID.randomUUID()}.jpg"
            val imageFile = File(imagesDir, fileName)

            // Copy file to internal storage
            val outputStream = FileOutputStream(imageFile)
            inputStream.copyTo(outputStream)
            outputStream.close()
            inputStream.close()

            Log.d(TAG, "Image saved to: ${imageFile.absolutePath}")
            imageFile.absolutePath
        } catch (e: Exception) {
            Log.e(TAG, "Error saving image from URI: $uri", e)
            null
        }
    }

    /**
     * Delete image file from internal storage
     */
    fun deleteImage(imagePath: String?) {
        if (imagePath.isNullOrEmpty()) return
        
        try {
            val imageFile = File(imagePath)
            if (imageFile.exists()) {
                val deleted = imageFile.delete()
                Log.d(TAG, "Image deletion result: $deleted for path: $imagePath")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error deleting image: $imagePath", e)
        }
    }

    /**
     * Check if image file exists
     */
    fun imageExists(imagePath: String?): Boolean {
        if (imagePath.isNullOrEmpty()) return false
        return File(imagePath).exists()
    }

    /**
     * Get URI for local file path
     */
    fun getFileUri(imagePath: String?): Uri? {
        if (imagePath.isNullOrEmpty()) return null
        val file = File(imagePath)
        return if (file.exists()) {
            Uri.fromFile(file)
        } else {
            null
        }
    }
}

