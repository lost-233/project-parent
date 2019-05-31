package com.zhaoming.base.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author hhx
 */
@Deprecated
public class HttpUtil {

    /**
     * 将图片输入流转换为输出流
     * @param imageUrl
     * @param outputStream
     * @return
     */
    public static boolean getImageByteByUrl(String imageUrl, OutputStream outputStream){
        InputStream inputStream = null;
        HttpURLConnection httpUrl = null;
        try {
            URL url = new URL(imageUrl);
            httpUrl = (HttpURLConnection) url.openConnection();
            httpUrl.connect();
            inputStream = httpUrl.getInputStream();
            byte[] buffer = new byte[8192];
            int len;
            while((len = inputStream.read(buffer)) != -1){
                outputStream.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if(inputStream != null){
                    inputStream.close();
                }
                if(httpUrl != null){
                    httpUrl.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
