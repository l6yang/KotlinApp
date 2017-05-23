package com.kotlin.loyal.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.media.ExifInterface
import android.util.Log

import com.facebook.drawee.backends.pipeline.Fresco

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

object ImageUtil {

    internal fun doBlur(sentBitmap: Bitmap, radius: Int, canReuseInBitmap: Boolean): Bitmap? {
        val bitmap: Bitmap
        if (canReuseInBitmap) {
            bitmap = sentBitmap
        } else {
            bitmap = sentBitmap.copy(sentBitmap.config, true)
        }

        if (radius < 1) {
            return null
        }
        val w = bitmap.width
        val h = bitmap.height
        val pix = IntArray(w * h)
        bitmap.getPixels(pix, 0, w, 0, 0, w, h)
        val wm = w - 1
        val hm = h - 1
        val wh = w * h
        val div = radius + radius + 1
        val r = IntArray(wh)
        val g = IntArray(wh)
        val b = IntArray(wh)
        var rsum: Int
        var gsum: Int
        var bsum: Int
        var x: Int
        var y: Int
        var i: Int
        var p: Int
        var yp: Int
        var yi: Int
        var yw: Int
        val vmin = IntArray(Math.max(w, h))

        var divsum = div + 1 shr 1
        divsum *= divsum
        val dv = IntArray(256 * divsum)
        i = 0
        while (i < 256 * divsum) {
            dv[i] = i / divsum
            i++
        }
        yi = 0
        yw = yi
        val stack = Array(div) { IntArray(3) }
        var stackpointer: Int
        var stackstart: Int
        var sir: IntArray
        var rbs: Int
        val r1 = radius + 1
        var routsum: Int
        var goutsum: Int
        var boutsum: Int
        var rinsum: Int
        var ginsum: Int
        var binsum: Int
        y = 0
        while (y < h) {
            bsum = 0
            gsum = bsum
            rsum = gsum
            boutsum = rsum
            goutsum = boutsum
            routsum = goutsum
            binsum = routsum
            ginsum = binsum
            rinsum = ginsum
            i = -radius
            while (i <= radius) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))]
                sir = stack[i + radius]
                sir[0] = p and 0xff0000 shr 16
                sir[1] = p and 0x00ff00 shr 8
                sir[2] = p and 0x0000ff
                rbs = r1 - Math.abs(i)
                rsum += sir[0] * rbs
                gsum += sir[1] * rbs
                bsum += sir[2] * rbs
                if (i > 0) {
                    rinsum += sir[0]
                    ginsum += sir[1]
                    binsum += sir[2]
                } else {
                    routsum += sir[0]
                    goutsum += sir[1]
                    boutsum += sir[2]
                }
                i++
            }
            stackpointer = radius
            x = 0
            while (x < w) {
                r[yi] = dv[rsum]
                g[yi] = dv[gsum]
                b[yi] = dv[bsum]
                rsum -= routsum
                gsum -= goutsum
                bsum -= boutsum
                stackstart = stackpointer - radius + div
                sir = stack[stackstart % div]
                routsum -= sir[0]
                goutsum -= sir[1]
                boutsum -= sir[2]
                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm)
                }
                p = pix[yw + vmin[x]]
                sir[0] = p and 0xff0000 shr 16
                sir[1] = p and 0x00ff00 shr 8
                sir[2] = p and 0x0000ff
                rinsum += sir[0]
                ginsum += sir[1]
                binsum += sir[2]
                rsum += rinsum
                gsum += ginsum
                bsum += binsum
                stackpointer = (stackpointer + 1) % div
                sir = stack[stackpointer % div]
                routsum += sir[0]
                goutsum += sir[1]
                boutsum += sir[2]
                rinsum -= sir[0]
                ginsum -= sir[1]
                binsum -= sir[2]
                yi++
                x++
            }
            yw += w
            y++
        }
        x = 0
        while (x < w) {
            bsum = 0
            gsum = bsum
            rsum = gsum
            boutsum = rsum
            goutsum = boutsum
            routsum = goutsum
            binsum = routsum
            ginsum = binsum
            rinsum = ginsum
            yp = -radius * w
            i = -radius
            while (i <= radius) {
                yi = Math.max(0, yp) + x
                sir = stack[i + radius]
                sir[0] = r[yi]
                sir[1] = g[yi]
                sir[2] = b[yi]
                rbs = r1 - Math.abs(i)
                rsum += r[yi] * rbs
                gsum += g[yi] * rbs
                bsum += b[yi] * rbs
                if (i > 0) {
                    rinsum += sir[0]
                    ginsum += sir[1]
                    binsum += sir[2]
                } else {
                    routsum += sir[0]
                    goutsum += sir[1]
                    boutsum += sir[2]
                }
                if (i < hm) {
                    yp += w
                }
                i++
            }
            yi = x
            stackpointer = radius
            y = 0
            while (y < h) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = 0xff000000.toInt() and pix[yi] or (dv[rsum] shl 16) or (dv[gsum] shl 8) or dv[bsum]
                rsum -= routsum
                gsum -= goutsum
                bsum -= boutsum
                stackstart = stackpointer - radius + div
                sir = stack[stackstart % div]
                routsum -= sir[0]
                goutsum -= sir[1]
                boutsum -= sir[2]
                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w
                }
                p = x + vmin[y]
                sir[0] = r[p]
                sir[1] = g[p]
                sir[2] = b[p]

                rinsum += sir[0]
                ginsum += sir[1]
                binsum += sir[2]

                rsum += rinsum
                gsum += ginsum
                bsum += binsum

                stackpointer = (stackpointer + 1) % div
                sir = stack[stackpointer]

                routsum += sir[0]
                goutsum += sir[1]
                boutsum += sir[2]

                rinsum -= sir[0]
                ginsum -= sir[1]
                binsum -= sir[2]

                yi += w
                y++
            }
            x++
        }
        bitmap.setPixels(pix, 0, w, 0, 0, w, h)
        return bitmap
    }

    fun createScaledBitmap(src: Bitmap, dstWidth: Int, dstHeight: Int, filter: Boolean): Bitmap {
        val scaleRatio = 10
        val blurRadius = 8
        val scaledBitmap = Bitmap.createScaledBitmap(src,
                dstWidth / scaleRatio,
                dstHeight / scaleRatio,
                filter)
        return doBlur(scaledBitmap, blurRadius, true)!!
    }

    fun releasePics(path: String, baseSize: Int): Bitmap {
        // 图片参数
        val options = BitmapFactory.Options()
        // 只计算几何尺寸，不返回bitmap,不占内存.
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, options)
        // 得到图片的宽、高
        val w = options.outWidth
        val h = options.outHeight
        // 最小边
        val min = if (w < h) w else h
        // 压缩比。
        var rate = min / baseSize
        if (rate <= 0) {
            rate = 1
        }
        // 设置压缩参数。
        options.inSampleSize = rate
        options.inJustDecodeBounds = false
        // 压缩。
        return BitmapFactory.decodeFile(path, options)
    }

    fun saveToFile(fileFolderStr: String, croppedImage: Bitmap): String {
        var baos: ByteArrayOutputStream? = null
        var outputStream: FileOutputStream? = null
        try {
            val jpgFile = File(fileFolderStr)
            outputStream = FileOutputStream(jpgFile)
            baos = ByteArrayOutputStream()
            // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            croppedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            var options = 100
            // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            while (baos.toByteArray().size / 1024 > 80) {
                // 重置baos即清空baos
                baos.reset()
                // 每次都减少10
                options -= 10
                // 这里压缩options%，把压缩后的数据存放到baos中
                croppedImage.compress(Bitmap.CompressFormat.JPEG, options, baos)
            }
            outputStream.write(baos.toByteArray())
            return jpgFile.path
        } catch (e: Exception) {
            return ""
        } finally {
            IOUtil.closeStream(outputStream)
            IOUtil.closeStream(baos)
        }
    }

    fun saveToFile(jpgFile: File, inputStream: InputStream?): String {
        if (inputStream == null)
            return ""
        try {
            val bytes = ByteArray(1024)
            while (inputStream.read(bytes) != -1) {
                println("!=-1111")
                jpgFile.appendBytes(bytes)
            }
            return jpgFile.path
        } catch (e: Exception) {
            return ""
        } finally {
            IOUtil.closeStream(inputStream)
        }
    }

    fun getPictureDegree(picPath: String): Int {
        try {
            var degree = 0
            val exifInterface = ExifInterface(picPath)
            val orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> degree = 90
                ExifInterface.ORIENTATION_ROTATE_180 -> degree = 180
                ExifInterface.ORIENTATION_ROTATE_270 -> degree = 270
            }
            return degree
        } catch (e: IOException) {
            e.printStackTrace()
            return 0
        }

    }

    fun createPhotoWithText(bitmap: Bitmap?, text: String): Bitmap? {
        try {
            if (null == bitmap) return null
            val width = bitmap.width
            val height = bitmap.height
            val newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(newBitmap)
            canvas.drawBitmap(bitmap, 0f, 0f, null)
            bitmap.recycle()
            val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DEV_KERN_TEXT_FLAG)
            paint.color = Color.RED
            paint.typeface = Typeface.DEFAULT_BOLD
            //通过计算设置字体宽度，让水印总是占用图片宽度的80%
            val stringWidth = (width * 0.8).toInt()
            //字体高度
            val fm = paint.fontMetrics
            val fFontHeight = Math.ceil((fm.descent - fm.ascent).toDouble()).toInt()
            //每个字的宽度
            val perStringWidth = stringWidth / text.length * 2
            paint.textSize = perStringWidth.toFloat()
            //根据图片宽度设置文字文字，使文字居右底端显示
            val newStringWidth = paint.measureText(text).toInt()
            //计算文字坐标 预留20个像素
            val stringX = width - newStringWidth - 30
            val stringY = height - fFontHeight - 20
            canvas.drawText(text.toString(), stringX.toFloat(), stringY.toFloat(), paint)
            return newBitmap
        } catch (e: Exception) {
            return null
        }

    }

    fun clearFrescoTemp() {
        Fresco.getImagePipeline().clearCaches()
        Fresco.getImagePipeline().clearDiskCaches()
        Fresco.getImagePipeline().clearMemoryCaches()
    }
}