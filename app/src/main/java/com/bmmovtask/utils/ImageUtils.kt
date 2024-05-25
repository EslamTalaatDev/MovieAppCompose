package com.bmmovtask.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.camera.core.ImageProxy
import java.nio.ByteBuffer

data class Roi(
    val left: Float,
    val top: Float,
    val width: Float,
    val height: Float
)


fun Bitmap.getRoi(roi: Roi): Bitmap {
    return Bitmap.createBitmap(
        this,
        (roi.left * width).toInt(),
        (roi.top * height).toInt(),
        (roi.width * width).toInt(),
        (roi.height * height).toInt()
    )
}

fun ImageProxy.toBitmap(rotation: Float? = null): Bitmap {
    val buffer: ByteBuffer = planes[0].buffer
    val bytes = ByteArray(buffer.remaining()).also {
        buffer.get(it)
    }
    val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

    return rotation?.let { deg -> bitmap.rotate(deg) } ?: bitmap
}

fun Bitmap.rotate(degrees: Float): Bitmap {
    return Bitmap.createBitmap(
        this,
        0,
        0,
        width,
        height,
        android.graphics.Matrix().apply { postRotate(degrees) },
        true
    )
}

