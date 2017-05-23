package com.kotlin.loyal.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.kotlin.loyal.R
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

object ResUtil {
    /**
     * @param variableName video
     * *
     * @param c            mipmap.class
     */
    fun getResId(variableName: String, c: Class<*>): Int {
        try {
            val idField = c.getDeclaredField(variableName)
            return idField.getInt(idField)
        } catch (e: Exception) {
            return -1
        }

    }

    /**
     * @param fileName json/index.json
     */
    fun openAssetFile(context: Context, fileName: String): InputStream? {
        try {
            return context.assets.open(fileName)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

    }

    fun getStrFromRes(`is`: InputStream?): String {
        if (`is` == null)
            return ""
        var bufferedReader: BufferedReader? = null
        try {
            val buffer = StringBuilder()
            bufferedReader = BufferedReader(InputStreamReader(`is`, "utf-8"))
            while (bufferedReader.readLine() != null) {
                buffer.append(bufferedReader.readLine())
            }
            return buffer.toString()
        } catch (e: IOException) {
            return ""
        } finally {
            IOUtil.closeStream(bufferedReader)
            IOUtil.closeStream(`is`)
        }
    }

    /**
     * [.openAssetFile]
     */
    fun getStrFromRes(context: Context, fileName: String): String {
        val `is` = openAssetFile(context, fileName) ?: return ""
        try {
            return getStrFromRes(`is`)
        } catch (e: IOException) {
            return ""
        } finally {
            IOUtil.closeStream(`is`)
        }
    }

    @JvmOverloads fun getBackground(context: Context, resId: Int = R.mipmap.img_blue_background, scale: Boolean = true): Drawable {
        val bitmap = BitmapFactory.decodeResource(context.resources, resId)
        if (!scale)
            return BitmapDrawable(context.resources, bitmap)
        else {
            val newBitmap = createScaledBitmap(bitmap, true)
            bitmap?.recycle()
            return BitmapDrawable(context.resources, newBitmap)
        }
    }

    private fun createScaledBitmap(src: Bitmap, filter: Boolean): Bitmap {
        val scaleRatio = 10
        val blurRadius = 8
        val scaledBitmap = Bitmap.createScaledBitmap(src,
                src.width / scaleRatio,
                src.height / scaleRatio,
                filter)
        return ImageUtil.doBlur(scaledBitmap, blurRadius, true)!!
    }
}
