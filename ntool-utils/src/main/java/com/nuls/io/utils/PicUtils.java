/**
 * Ambition Inc.
 * Copyright (c) 2006-2016 All Rights Reserved.
 */
package com.nuls.io.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 图片。图像相关工具类
 * @author wxh
 * @version $Id: PicUtils.java, v 0.1 2016年11月15日 下午1:46:53 wxh Exp $
 */
public class PicUtils {
    private static final Logger logger = LoggerFactory.getLogger(PicUtils.class);

    /**
     * 将文件拷贝到指定目录
     *
     * @param fileName 文件名
     * @param Path 如  "student" + "/head/" 
     */
    public static void copyPic(String fileName, String Path) {
        Calendar calendar = Calendar.getInstance();
        String dateString = DateUtils.changeDateToStr(calendar.getTime(), "yyyy-MM-dd");
        String storePath = Path + dateString + "/" + fileName;

        //保存图片路径  是相对路径
        String photoPath = StringUtils.getPropByName("photoPath", "/filePath/path.properties");
        String pathString = photoPath + storePath;
        //临时文件路劲
        String photoTemporaryPath = StringUtils.getPropByName("photoTemporaryPath",
            "/filePath/path.properties");
        String TemporaryPath = photoTemporaryPath + dateString + "/" + fileName;
        FileOutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            //读取临时文件
            File file = new File(TemporaryPath);
            if (file.exists()) {
                File file1 = new File(photoPath + Path + dateString);
                if (!file1.exists()) {
                    file1.mkdirs();
                }
                outputStream = new FileOutputStream(pathString);
                inputStream = new FileInputStream(file);
                byte[] buff = new byte[1024];
                int len = 0;
                while ((len = inputStream.read(buff)) > 0) {
                    outputStream.write(buff, 0, len);
                }
                inputStream.close();
                outputStream.flush();
                outputStream.close();
                outputStream = null;
                inputStream = null;
            }

        } catch (IOException e) {
            logger.error("图片保存信息" + StringUtils.outPutException(e));
        }
    }
}
