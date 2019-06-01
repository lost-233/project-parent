package com.zhaoming.fastdfs.impl;

import com.zhaoming.fastdfs.DfsUpAndDowService;
import com.zhaoming.fastdfs.util.FastdfsClientWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author zm
 * @date 2018/10/26
 */
@Slf4j
@Service
public class DfsUpAndDowServiceImpl implements DfsUpAndDowService {

    @Autowired
    private FastdfsClientWrapper fastDFSClientWrapper;


    /**
     * 文件上传
     * 最后返回fastDFS中的文件名称;group1/M00/01/04/CgMKrVvS0geAQ0pzAACAAJxmBeM793.doc
     *
     * @param file 页面提交时文件
     * @return 链接
     */
    @Override
    public String uploadFile(InputStream file, String fileName) {
        byte[] bytes = null;
        try {
            bytes = new byte[file.available()];
            file.read(bytes);
        } catch (IOException e) {
            log.error("获取文件错误");
            e.printStackTrace();
        }
        if (null == bytes) {
            log.info("要上传的文件为空");
            return null;
        }
        //获取文件后缀--.doc
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        //获取文件大小
        long fileSize = bytes.length;
        log.info(fileName + "==" + fileSize + "==" + extension + "==" + bytes.length);
        return fastDFSClientWrapper.uploadFile(bytes, fileSize, extension);
    }

    /**
     * 文件下载
     *
     * @param fileUrl 当前对象文件名称
     * @return 文件数组
     * @throws IOException 异常
     */
    @Override
    public byte[] downloadFile(String fileUrl) throws IOException {
        return fastDFSClientWrapper.downloadFile(fileUrl);
    }

    /**
     * 删除文件
     *
     * @param fileUrl 当前对象文件名称
     * @return 是否删除成功
     */
    @Override
    public boolean deleteFile(String fileUrl) {
        return fastDFSClientWrapper.deleteFile(fileUrl);
    }
}
