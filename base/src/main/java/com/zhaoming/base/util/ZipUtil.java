package com.zhaoming.base.util;

import com.zhaoming.base.constant.CommonConstant;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Zip压缩/解压缩工具类
 * 实现对目标路径及其子路径下的所有文件及空目录的压缩
 * 参考网上若干种实现，并修改其bug
 *
 * @version        v0.1, 17/09/04
 * @author         Kiwi Liu
 */
@Slf4j
@UtilityClass
public class ZipUtil {

    /** 缓冲器大小 */
    private static final int BUFFER = 512;

    /**
     * 取的给定源目录下的所有文件及空的子目录
     * 递归实现
     *
     * @param srcFile
     *
     * @return
     */
    public static List<File> getAllFiles(File srcFile) {
        List<File> fileList = new ArrayList<File>();
        File[]     tmp      = srcFile.listFiles();

        for (File aTmp : tmp) {

            if (aTmp.isFile()) {
                fileList.add(aTmp);
                log.info("add file: " + aTmp.getName());
            }

            if (aTmp.isDirectory()) {
                if (aTmp.listFiles().length != 0) {
                    //若不是空目录，则递归添加其下的目录和文件
                    fileList.addAll(getAllFiles(aTmp));
                } else {
                    //若是空目录，则添加这个目录到fileList
                    fileList.add(aTmp);
                    log.info("add empty dir: " + aTmp.getName());
                }
            }
        }    // end for

        return fileList;
    }

    /**
     * 取相对路径
     * 依据文件名和压缩源路径得到文件在压缩源路径下的相对路径
     *
     * @param dirPath 压缩源路径
     * @param file
     *
     * @return 相对路径
     */
    private static String getRelativePath(String dirPath, File file) {
        File   dir          = new File(dirPath);
        String relativePath = file.getName();

        while (true) {
            file = file.getParentFile();

            if (file == null) {
                break;
            }

            if (file.equals(dir)) {
                break;
            } else {
                relativePath = new StringBuilder().append(file.getName()).append(CommonConstant.PATH).append(relativePath).toString();
            }
        }    // end while

        return relativePath;
    }

    /**
     * 创建文件
     * 根据压缩包内文件名和解压缩目的路径，创建解压缩目标文件，
     * 生成中间目录
     * @param dstPath 解压缩目的路径
     * @param fileName 压缩包内文件名
     *
     * @return 解压缩目标文件
     *
     * @throws IOException
     */
    private static File createFile(String dstPath, String fileName) throws IOException {
        String[] dirs = fileName.split(CommonConstant.PATH);
        //将文件名的各级目录分解
        File file = new File(dstPath);

        if (dirs.length > 1) {
            //文件有上级目录
            for (int i = 0; i < dirs.length - 1; i++) {
                file = new File(file, dirs[i]);
                //依次创建文件对象知道文件的上一级目录
            }

            if (!file.exists()) {
                file.mkdirs();
                //文件对应目录若不存在，则创建
                log.info("mkdirs: " + file.getCanonicalPath());
            }

            file = new File(file, dirs[dirs.length - 1]);
            //创建文件

            return file;
        } else {
            if (!file.exists()) {
                file.mkdirs();//若目标路径的目录不存在，则创建
                log.info("mkdirs: " + file.getCanonicalPath());
            }

            file = new File(file, dirs[0]);
            //创建文件

            return file;
        }
    }

    /**
     * 解压缩方法
     *
     *
     * @param zipFile 压缩文件流
     * @param dstPath 解压目标路径
     *
     * @return
     */
    public static boolean unzip(InputStream zipFile, String dstPath) {
        log.info("zip uncompressing...");

        try {
            ZipInputStream zipInputStream = new ZipInputStream(zipFile);
            ZipEntry       zipEntry       = null;
            //缓冲器
            byte[]         buffer         = new byte[BUFFER];
            //每次读出来的长度
            int            readLength     = 0;

            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                if (zipEntry.isDirectory()) {
                    //若是zip条目目录，则需创建这个目录
                    File dir = new File(dstPath + CommonConstant.PATH + zipEntry.getName());

                    if (!dir.exists()) {
                        dir.mkdirs();
                        log.info("mkdirs: " + dir.getCanonicalPath());

                        //跳出
                        continue;
                    }
                }

                //若是文件，则需创建该文件
                File file = createFile(dstPath, zipEntry.getName());
                log.info("file created: " + file.getCanonicalPath());

                OutputStream outputStream = new FileOutputStream(file);

                while ((readLength = zipInputStream.read(buffer, 0, BUFFER)) != -1) {
                    outputStream.write(buffer, 0, readLength);
                }

                outputStream.close();
                log.info("file uncompressed: " + file.getCanonicalPath());
            }    // end while
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
            e.printStackTrace();
            log.error("unzip fail!");

            return false;
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
            log.error("unzip fail!");

            return false;
        }

        log.info("unzip success!");

        return true;
    }
    /**
     * 压缩方法
     * （可以压缩空的子目录）
     * @param srcPath 压缩源路径
     * @param zipFileName 目标压缩文件
     *
     * @return
     */
    public static boolean zip(String srcPath, String zipFileName) {
        log.info("zip compressing...");

        File       srcFile    = new File(srcPath);
        //所有要压缩的文件
        List<File> fileList   = getAllFiles(srcFile);
        //缓冲器
        byte[]     buffer     = new byte[BUFFER];
        ZipEntry   zipEntry   = null;
        //每次读出来的长度
        int        readLength = 0;

        try {
            ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFileName));

            for (File file : fileList) {
                if (file.isFile()){
                    //若是文件，则压缩这个文件
                    zipEntry = new ZipEntry(getRelativePath(srcPath, file));
                    zipEntry.setSize(file.length());
                    zipEntry.setTime(file.lastModified());
                    zipOutputStream.putNextEntry(zipEntry);

                    InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

                    while ((readLength = inputStream.read(buffer, 0, BUFFER)) != -1) {
                        zipOutputStream.write(buffer, 0, readLength);
                    }

                    inputStream.close();
                    log.info("file compressed: " + file.getCanonicalPath());
                }else {//若是目录（即空目录）则将这个目录写入zip条目
                    zipEntry = new ZipEntry(getRelativePath(srcPath, file)+CommonConstant.PATH);
                    zipOutputStream.putNextEntry(zipEntry);
                    log.info("dir compressed: " + file.getCanonicalPath()+CommonConstant.PATH);
                }

            }

            zipOutputStream.close();
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
            e.printStackTrace();
            log.error("zip fail!");

            return false;
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
            log.error("zip fail!");

            return false;
        }

        log.info("zip success!");

        return true;
    }
}