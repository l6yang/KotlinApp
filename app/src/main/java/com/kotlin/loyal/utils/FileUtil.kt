package com.kotlin.loyal.utils

import android.os.Environment
import java.io.*

object FileUtil {
    // sd卡路径
    private val SD_PATH = Environment.getExternalStorageDirectory().path
    private val path_main = SD_PATH + File.separator + "com.kotlin.loyal" + File.separator
    // 本地保存文件
    val path_local = path_main + "local" + File.separator
    val path_icon = path_main + "icon" + File.separator
    val path_temp = path_main + "temp" + File.separator
    val path_voice = path_main + "voice" + File.separator
    val pic_UCrop = "UCrop.jpg"
    val pic_temp = "temp.jpg"
    val pic_tmp = "tmp.jpg"
    // apk更新下载的更新文件存放的目录
    val path_apk = path_main + "apk" + File.separator
    val path_share = path_main + "share" + File.separator
    val apkFileName = "kotlin.apk"

    fun fileCreated(path: String): Boolean {
        val file = File(path)
        return file.exists()
    }

    fun createFiles() {
        var file = File(path_local)
        createFiles(file)
        file = File(path_icon)
        createFiles(file)
        file = File(path_apk)
        createFiles(file)
        file = File(path_temp)
        createFiles(file)
        file = File(path_voice)
        createFiles(file)
        file = File(path_share)
        createFiles(file)
    }

    private fun createFiles(file: File): Boolean {
        return !file.exists() && file.mkdirs()
    }

    // 保存IP文件到本地 这里只需要把文件名传进去就行，不管保存的类型
    fun createFiles(path: String, fileName: String, content: String) {
        val file = File(path + fileName)
        val delete = deleteFile(file)
        if (delete)
            try {
                val fos = FileOutputStream(file)
                fos.write(content.toByteArray())
                fos.flush()
                fos.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

    }

    fun writeFile(`in`: InputStream, file: File) {
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(file)

            val buffer = ByteArray(1024 * 128)
            while (`in`.read(buffer) != -1) {
                fos.write(buffer, 0, `in`.read(buffer))
            }
            fos.flush()
            fos.close()
            `in`.close()
        } catch (e: IOException) {
            //
        } finally {
            IOUtil.closeStream(fos)
            IOUtil.closeStream(`in`)
        }
    }

    fun createIcons(path: String, fileName: String, inputStream: InputStream?): String {
        if (inputStream == null)
            return ""
        val iconName = "$fileName.png"
        val file = File(path + iconName)
        val delete = deleteFile(file)
        if (delete)
            try {
                val fos = FileOutputStream(file)
                val bytes = ByteArray(1024)
                while (inputStream.read(bytes) != -1) {
                    fos.write(bytes, 0, inputStream.read(bytes))
                }
                inputStream.close()
                fos.flush()
                fos.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        return file.path
    }

    // 将保存在文件的数据读载到所需要的控件中
    fun getFileContent(path: String, fileName: String): String {
        val f = File(path + "/" + fileName)
        try {
            return ResUtil.getStrFromRes(FileInputStream(f))
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
    }

    /**
     * @param file Eg:"F:\ss\新建文本文档.txt"
     * * @ 删除文件
     */
    fun deleteFile(file: File): Boolean {
        try {
            return !file.exists() || file.delete()
        } catch (e: Exception) {
            return false
        }

    }

    fun deleteFile(path: String): Boolean {
        return deleteFile(File(path))
    }

    fun deleteFile(path: String, fileName: String): Boolean {
        return deleteFile(File(path, fileName))
    }

    fun renameFile(resFile: File, goalFile: File): Boolean {
        try {
            return resFile.renameTo(goalFile)
        } catch (e: Exception) {
            return false
        }

    }

    fun renameFile(resFile: String, goalFile: String): Boolean {
        try {
            val res = File(resFile)
            val goal = File(goalFile)
            return renameFile(res, goal)
        } catch (e: Exception) {
            return false
        }

    }
}
