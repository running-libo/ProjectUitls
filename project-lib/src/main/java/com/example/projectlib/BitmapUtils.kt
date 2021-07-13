package com.example.projectlib

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.widget.ImageView
import java.io.*
import java.nio.ByteBuffer
import java.nio.IntBuffer

/**
 * Created by libo on 2017/11/25.
 */
object BitmapUtils {
    /**
     * 将bitmap保存为文件
     * @param bitmap
     * @param file
     * @return
     */
    fun bitmapToFile(bitmap: Bitmap, file: File): Boolean {
        try {
            if (!file.exists()) {
                file.createNewFile()
            }
            val out = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            return true
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 将byteBuffer转为Bitmap
     * @param byteBuffer
     * @param width
     * @param height
     * @return
     */
    fun byteBufferToBitmap(byteBuffer: ByteBuffer, width: Int, height: Int): Bitmap {
        val intBuffer = byteBuffer.asIntBuffer()
        val ret = IntArray(byteBuffer.limit() / 4)
        val retBuffer = IntBuffer.wrap(ret)
        retBuffer.put(intBuffer)
        for (i in ret.indices) {
            val src = ret[i]
            val dest = src and 0xFF shl 24 or (src and -0x1000000 shr 8) or (src and 0xFF0000 shr 8) or (src and 0xFF00 shr 8)
            ret[i] = dest
        }
        return Bitmap.createBitmap(ret, width, height, Bitmap.Config.ARGB_8888)
    }

    /**
     * 高效率Bitmap高斯模糊
     * 还需在build.gradle加入 defaultConfig {
     * renderscriptTargetApi 19
     * renderscriptSupportModeEnabled true
     * }
     * @param context
     * @param ivBlurBg
     * @param bitmap
     * param scaleRatio  bitmap分辨率缩小比例，计算速度更快,范围 1-10
     */
    fun bitmapBlur(context: Context?, ivBlurBg: ImageView, bitmap: Bitmap?, scaleRatio: Int) {
        val x = ivBlurBg.x.toInt()
        val y = ivBlurBg.y.toInt()
        val bitmapX = bitmap!!.width
        val bitmapY = bitmap.height
        val bitmapNew = Bitmap.createBitmap(bitmap, x, y, bitmapX - x, bitmapY - y)
        if (bitmap != null) {
            var overlay = Bitmap.createScaledBitmap(bitmapNew, bitmapNew.width / scaleRatio, bitmapNew.height / scaleRatio, false)
            overlay = handleGlassblur(context, overlay, 15)
            ivBlurBg.setImageBitmap(overlay)
        }
        bitmap.recycle()
    }

    fun handleGlassblur(context: Context?, originBitmap: Bitmap?, radius: Int): Bitmap? {
        val renderScript = RenderScript.create(context)
        val input = Allocation.createFromBitmap(renderScript, originBitmap)
        val output = Allocation.createTyped(renderScript, input.type)
        val scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))
        scriptIntrinsicBlur.setRadius(radius.toFloat())
        scriptIntrinsicBlur.setInput(input)
        scriptIntrinsicBlur.forEach(output)
        output.copyTo(originBitmap)
        return originBitmap
    }

    /**
     * 根据指定的宽、高，对图片进行二次采样
     * @param bytes
     * @return
     */
    fun ScaleBitmap(bytes: ByteArray, width: Int, height: Int): Bitmap {
        //获取图片的解码参数设置
        val options = BitmapFactory.Options()
        //设置为true仅仅解码图片的边缘
        options.inJustDecodeBounds = true
        //对图片进行解码,如果指定了inJustDecodeBounds=true，decodeByteArray将返回为空
        BitmapFactory.decodeByteArray(bytes, 0, bytes.size, options)
        val outWidth = options.outWidth
        val outHeight = options.outHeight
        val sampleSizeX = width / outWidth
        val sampleSizeY = height / outHeight
        val simpleSize = if (sampleSizeX < sampleSizeY) sampleSizeX else sampleSizeY
        //设置inJustDecodeBounds为false重新将图片读进内存中
        options.inJustDecodeBounds = false
        //实际要进行缩放的比例
        options.inSampleSize = simpleSize
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size, options)
    }

    /**
     * 图片质量压缩
     * @param bitmap  需要质量压缩的图片
     * @param size    指定最大要压缩成的大小，单位为k
     * @return
     */
    fun compressBitmap(bitmap: Bitmap, size: Int): Bitmap? {
        val bos = ByteArrayOutputStream()
        //将压缩后的数据放入bos中
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
        var quality = 100
        while (bos.toByteArray().size / 1024 > size) {
            //循环判断如果压缩后的图片大于100k，则清空bos，质量压缩比减小10%
            bos.reset()
            quality -= 10
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos)
        }
        val bis = ByteArrayInputStream(bos.toByteArray())
        //通过字节输入流转为bitmap
        return BitmapFactory.decodeStream(bis, null, null)
    }
}