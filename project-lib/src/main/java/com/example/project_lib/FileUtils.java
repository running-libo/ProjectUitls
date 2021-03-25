package com.example.project_lib;

import android.content.Context;
import android.os.Environment;
import android.text.format.Formatter;
import android.widget.Toast;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by libo on 2017/11/25.
 */

public class FileUtils {

    /**
     * 判断sd卡是否可用
     * @return
     */
    public static boolean isHaveSDcard(){
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 设置App缓存根目录
     * @return
     */
    public static String getCacheDir(){
        String sdDir = null;
        if(isHaveSDcard()){
            sdDir = Environment.getExternalStorageDirectory().getPath() + "/AppName/";
        }
        return sdDir;
    }

    /**
     * 将data数据写入指定文件里
     * @param data
     * @param fileName
     * @throws IOException
     */
    public static void saveFileToSDcard(byte[] data,String fileName){
        String filePath = getCacheDir();
        File dir = new File(filePath);
        if(!dir.exists()){
            dir.mkdirs();
        }
        File file = new File(filePath+"/"+fileName);
        try {
            if(!file.exists()){
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                bos.write(data);
                bos.flush();
                bos.close();
                fos.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 递归累计获取文件/文件夹大小
     * @param f
     * @return
     * @throws Exception
     */
    public static long getFileSize(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSize(flist[i]);
            } else {
                size = size + flist[i].length();
            }
        }
        return size;
    }

    /**
     * 递归删除文件/文件夹
     * @param file
     */
    public static void delFile(File file){
        if(file.isFile()){
            file.delete();
        }else if(file.isDirectory()){
            File file1s[] = file.listFiles();
            for(int i=0;i<file1s.length;i++){
                delFile(file1s[i]);
            }
            file.delete();
        }
    }

    /**
     * 从raw资源文件中获取
     *
     * @return
     */
    private String getAddress(Context context,int rawRes) {
        StringBuilder sb = new StringBuilder();
        try {
            InputStream inputStream = context.getResources().openRawResource(rawRes);
            byte[] buffer = new byte[1024];
            while (inputStream.read(buffer) != -1) {
                sb.append(new String(buffer, "UTF-8"));
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 是否有足够的空间
     * @param size in B
     * @return
     */
    public boolean haveSpace(Context context, int size){
        //判断sdcard存储空间是否满足文件的存储
        File sdcard_filedir = Environment.getExternalStorageDirectory();//得到sdcard的目录作为一个文件对象
        long usableSpace = sdcard_filedir.getUsableSpace();//获取文件目录对象剩余空间
        long totalSpace = sdcard_filedir.getTotalSpace();

        //将一个long类型的文件大小格式化成用户可以看懂的M，G字符串
        String usableSpace_str = Formatter.formatFileSize(context, usableSpace);
        String totalSpace_str = Formatter.formatFileSize(context, totalSpace);

        if(usableSpace < size+20*1024*1024){//判断剩余空间是否小于size
            Toast.makeText(context, "sdcard剩余空间不足,无法满足下载；剩余空间为："+usableSpace_str,Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }

}
