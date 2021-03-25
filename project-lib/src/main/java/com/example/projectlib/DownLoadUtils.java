package com.example.projectlib;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by libo on 2017/3/13.
 *
 * 下载文件工具类
 */
public class DownLoadUtils {

    /**
     * 带最大值，进度值，结果的下载
     * @param url
     * @param maxLen
     * @param progress
     * @param result
     */
    public static void downloadFile(final String url, final CallBackimpl maxLen, final CallBackimpl progress, final CallBackimpl result){
        new Thread(){
            @Override
            public void run() {
                try {
                    HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
                    if(conn.getResponseCode() == 200){
                        InputStream inputStream = conn.getInputStream();
                        byte[] bytes = new byte[1024 * 500];
                        int len = -1;
                        maxLen.callBack(conn.getContentLength());
                        int currentLen = 0;  //当前进度
                        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        while((len = inputStream.read(bytes)) != -1){
                            baos.write(bytes,0,len);
                            currentLen+=len;
                            progress.callBack(currentLen);
                        }
                        result.callBack(baos.toByteArray());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 下载方法
     * @param url  地址
     * @param callBackimpl  下载完成回调
     */
    public static void downloadFile(final String url, final String fileName, final CallBackimpl callBackimpl){
        new Thread(){
            @Override
            public void run() {
                try {
                    HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
                    if(conn.getResponseCode() == 200){
                        InputStream inputStream = conn.getInputStream();
                        byte[] bytes = new byte[1024];
                        int len = -1;
                        File file = new File(fileName);
                        if(!file.getParentFile().exists()){
                            file.getParentFile().mkdirs();
                        }
                        FileOutputStream fos = new FileOutputStream(fileName);
                        while((len = inputStream.read(bytes)) != -1){
                            fos.write(bytes, 0, len);
                        }
                        fos.close();
                        callBackimpl.confirmHandle();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
