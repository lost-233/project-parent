package com.zhaoming.fastdfs.util;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * FastDFS客户端包装类
 *
 * @author zm
 * @date 2018/10/26
 */
@Slf4j
@Component
public class FastdfsClientWrapper {


    private final FastFileStorageClient fastFileStorageClient;

    @Autowired
    public FastdfsClientWrapper(FastFileStorageClient fastFileStorageClient) {
        this.fastFileStorageClient = fastFileStorageClient;
    }

    /**
     * 文件上传
     * 最后返回fastDFS中的文件名称;group1/M00/01/04/CgMKrVvS0geAQ0pzAACAAJxmBeM793.doc
     *
     * @param bytes     文件字节
     * @param fileSize  文件大小
     * @param extension 文件扩展名
     * @return fastDfs路径
     */
    public String uploadFile(byte[] bytes, long fileSize, String extension) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        StorePath storePath = fastFileStorageClient.uploadFile(byteArrayInputStream, fileSize, extension, null);
        log.info(storePath.getGroup() + "==" + storePath.getPath() + "======" + storePath.getFullPath());
        return storePath.getFullPath();
    }

    /**
     * 下载文件
     * 返回文件字节流大小
     *
     * @param fileUrl 文件URL
     * @return 文件字节
     * @throws IOException yc
     */
    public byte[] downloadFile(String fileUrl) throws IOException {
        String group = fileUrl.substring(0, fileUrl.indexOf("/"));
        String path = fileUrl.substring(fileUrl.indexOf("/") + 1);
        DownloadByteArray downloadByteArray = new DownloadByteArray();
        return fastFileStorageClient.downloadFile(group, path, downloadByteArray);
    }

    /**
     * 删除文件
     *
     * @param fileUrl 文件URL
     * @return 文件字节
     */
    public boolean deleteFile(String fileUrl){
        try {
            fastFileStorageClient.deleteFile(fileUrl);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
