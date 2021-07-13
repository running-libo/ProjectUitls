package com.example.projectlib

import android.content.Context
import android.os.Environment
import android.text.format.Formatter
import android.widget.Toast
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * Created by libo on 2017/11/25.
 */
class FileUtils {
    /**
     * 从raw资源文件中获取
     *
     * @return
     */
    private fun getAddress(context: Context, rawRes: Int): String {
        val sb = StringBuilder()
        try {
            val inputStream = context.resources.openRawResource(rawRes)
            val buffer = ByteArray(1024)
            while (inputStream.read(buffer) != -1) {
                sb.append(String(buffer, "UTF-8"))
            }
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return sb.toString()
    }

    /**
     * 是否有足够的空间
     * @param size in B
     * @return
     */
    fun haveSpace(context: Context?, size: Int): Boolean {
        //判断sdcard存储空间是否满足文件的存储
        val sdcard_filedir = Environment.getExternalStorageDirectory() //得到sdcard的目录作为一个文件对象
        val usableSpace = sdcard_filedir.usableSpace //获取文件目录对象剩余空间
        val totalSpace = sdcard_filedir.totalSpace

        //将一个long类型的文件大小格式化成用户可以看懂的M，G字符串
        val usableSpace_str = Formatter.formatFileSize(context, usableSpace)
        val totalSpace_str = Formatter.formatFileSize(context, totalSpace)
        return if (usableSpace < size + 20 * 1024 * 1024) { //判断剩余空间是否小于size
            Toast.makeText(context, "sdcard剩余空间不足,无法满足下载；剩余空间为：$usableSpace_str", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }

    companion object {
        /**
         * 判断sd卡是否可用
         * @return
         */
        val isHaveSDcard: Boolean
            get() = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

        /**
         * 设置App缓存根目录
         * @return
         */
        val cacheDir: String?
            get() {
                var sdDir: String? = null
                if (isHaveSDcard) {
                    sdDir = Environment.getExternalStorageDirectory().path + "/AppName/"
                }
                return sdDir
            }

        /**
         * 将data数据写入指定文件里
         * @param data
         * @param fileName
         * @throws IOException
         */
        fun saveFileToSDcard(data: ByteArray?, fileName: String) {
            val filePath = cacheDir
            val dir = File(filePath)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            val file = File("$filePath/$fileName")
            try {
                if (!file.exists()) {
                    file.createNewFile()
                    val fos = FileOutputStream(file)
                    val bos = BufferedOutputStream(fos)
                    bos.write(data)
                    bos.flush()
                    bos.close()
                    fos.close()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        /**
         * 递归累计获取文件/文件夹大小
         * @param f
         * @return
         * @throws Exception
         */
        @Throws(Exception::class)
        fun getFileSize(f: File): Long {
            var size: Long = 0
            val flist = f.listFiles()
            for (i in flist.indices) {
                size = if (flist[i].isDirectory) {
                    size + getFileSize(flist[i])
                } else {
                    size + flist[i].length()
                }
            }
            return size
        }

        /**
         * 递归删除文件/文件夹
         * @param file
         */
        fun delFile(file: File) {
            if (file.isFile) {
                file.delete()
            } else if (file.isDirectory) {
                val file1s = file.listFiles()
                for (i in file1s.indices) {
                    delFile(file1s[i])
                }
                file.delete()
            }
        }
    }
}