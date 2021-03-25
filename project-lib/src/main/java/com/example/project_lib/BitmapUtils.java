package com.example.project_lib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.widget.ImageView;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * Created by libo on 2017/11/25.
 */

public class BitmapUtils {

    /**
     * 将bitmap保存为文件
     * @param bitmap
     * @param file
     * @return
     */
    public static boolean bitmapToFile(Bitmap bitmap, File file) {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 将byteBuffer转为Bitmap
     * @param byteBuffer
     * @param width
     * @param height
     * @return
     */
    public static Bitmap byteBufferToBitmap(ByteBuffer byteBuffer, int width, int height) {
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        final int[] ret = new int[byteBuffer.limit() / 4];
        IntBuffer retBuffer = IntBuffer.wrap(ret);
        retBuffer.put(intBuffer);

        for (int i = 0; i < ret.length; i++) {
            int src = ret[i];
            int dest = (src & 0xFF) << 24 | (src & 0xFF000000) >> 8 | (src & 0xFF0000) >> 8 | (src & 0xFF00) >> 8;
            ret[i] = dest;
        }

        Bitmap bitmap = Bitmap.createBitmap(ret, width, height, Bitmap.Config.ARGB_8888);
        return bitmap;
    }

    /**
     * 高效率Bitmap高斯模糊
     * 还需在build.gradle加入 defaultConfig {
        renderscriptTargetApi 19
        renderscriptSupportModeEnabled true
     }
     * @param context
     * @param ivBlurBg
     * @param bitmap
     * param scaleRatio  bitmap分辨率缩小比例，计算速度更快,范围 1-10
     *
     */
    public static void bitmapBlur(Context context, ImageView ivBlurBg, Bitmap bitmap, int scaleRatio){
        int x = (int) ivBlurBg.getX();
        int y = (int) ivBlurBg.getY();
        int bitmapX = bitmap.getWidth();
        int bitmapY = bitmap.getHeight();
        Bitmap bitmapNew = Bitmap.createBitmap(bitmap,x,y,bitmapX-x,bitmapY-y);

        if(bitmap != null){
            Bitmap overlay = Bitmap.createScaledBitmap(bitmapNew, bitmapNew.getWidth() / scaleRatio, bitmapNew.getHeight() / scaleRatio, false);
            overlay = handleGlassblur(context,overlay,15);
            ivBlurBg.setImageBitmap(overlay);
        }
        bitmap.recycle();
    }

    public static Bitmap handleGlassblur(Context context, Bitmap originBitmap, int radius){
        RenderScript renderScript = RenderScript.create(context);
        Allocation input = Allocation.createFromBitmap(renderScript,originBitmap);
        Allocation output = Allocation.createTyped(renderScript,input.getType());
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        scriptIntrinsicBlur.setRadius(radius);
        scriptIntrinsicBlur.setInput(input);
        scriptIntrinsicBlur.forEach(output);
        output.copyTo(originBitmap);

        return originBitmap;
    }

    /**
     * 根据指定的宽、高，对图片进行二次采样
     * @param bytes
     * @return
     */
    public static Bitmap ScaleBitmap(byte[] bytes,int width,int height){
        //获取图片的解码参数设置
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置为true仅仅解码图片的边缘
        options.inJustDecodeBounds = true;
        //对图片进行解码,如果指定了inJustDecodeBounds=true，decodeByteArray将返回为空
        BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;

        int sampleSizeX = width/outWidth;
        int sampleSizeY = height/outHeight;
        int simpleSize = sampleSizeX < sampleSizeY ? sampleSizeX : sampleSizeY;
        //设置inJustDecodeBounds为false重新将图片读进内存中
        options.inJustDecodeBounds = false;
        //实际要进行缩放的比例
        options.inSampleSize = simpleSize;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    /**
     * 图片质量压缩
     * @param bitmap  需要质量压缩的图片
     * @param size    指定最大要压缩成的大小，单位为k
     * @return
     */
    public static Bitmap compressBitmap(Bitmap bitmap,int size){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //将压缩后的数据放入bos中
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,bos);
        int quality = 100;
        while(bos.toByteArray().length / 1024 > size){
            //循环判断如果压缩后的图片大于100k，则清空bos，质量压缩比减小10%
            bos.reset();
            quality -= 10;
            bitmap.compress(Bitmap.CompressFormat.JPEG,quality,bos);
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        //通过字节输入流转为bitmap
        return BitmapFactory.decodeStream(bis,null,null);
    }

}
