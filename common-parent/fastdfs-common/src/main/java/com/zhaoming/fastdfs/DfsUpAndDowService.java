package com.zhaoming.fastdfs;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author zm
 */
public interface DfsUpAndDowService {
    /**
     * 文件上传
     * 最后返回fastDFS中的文件名称;group1/M00/01/04/CgMKrVvS0geAQ0pzAACAAJxmBeM793.doc
     *
     * @param file     文件输入流
     * @param fileName 文件名
     * @return 文件相对url
     */
    String uploadFile(InputStream file, String fileName);

    /**
     * 文件下载
     *
     * @param fileUrl 当前对象文件名称
     * @return 文件数组
     * @throws IOException 异常
     */
    byte[] downloadFile(String fileUrl) throws IOException;

    /**
     * 删除文件
     *
     * @param fileUrl 当前对象文件名称
     * @return 是否删除成功
     */
    boolean deleteFile(String fileUrl);
}
