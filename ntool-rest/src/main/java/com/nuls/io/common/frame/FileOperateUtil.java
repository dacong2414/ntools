/**
 *
 * @author geloin
 * @date 2012-5-5 下午12:05:57
 */
package com.nuls.io.common.frame;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.nuls.io.model.entity.UpdateConfig;

/**
 * 文件处理类
 * @author geloin
 * @date 2012-5-5 下午12:05:57
 */
@Component
public class FileOperateUtil {
    private static final String REALNAME    = "realName";
    private static final String STORENAME   = "storeName";
    private static final String SIZE        = "size";
    private static final String SUFFIX      = "suffix";
    private static final String CONTENTTYPE = "contentType";
    private static final String CREATETIME  = "createTime";
    @Value("${upload.file.root-path}")
    private String              UPLOADDIR;

    /**
     * 将上传的文件进行重命名
     *
     * @param name 文件名
     * @return String 重命名后的字符串 
     */
    private static String rename(String name) {

        Long now = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        Long random = (long) (Math.random() * now);
        String fileName = now + "" + random;

        if (name.indexOf(".") != -1) {
            fileName += name.substring(name.lastIndexOf("."));
        }

        return fileName;
    }

    /**
     * 压缩后的文件名
     *
     * @param name 文件名
     * @return String 压缩文件名
     */
    private static String zipName(String name) {
        String prefix = "";
        if (name.indexOf(".") != -1) {
            prefix = name.substring(0, name.lastIndexOf("."));
        } else {
            prefix = name;
        }
        return prefix + ".zip";
    }

    /**
     * 上传文件
     *
     * @param mFile 文件对象
     * @param updateConfig 更新配置
     * @throws IOException
     */

    public void upload(MultipartFile mFile, UpdateConfig updateConfig) throws IOException {
        Calendar calendar = Calendar.getInstance();
        //获取存储文件路径
        File file = new File(UPLOADDIR);
        if (!file.exists()) {
            file.mkdir();
        }
        String name = mFile.getOriginalFilename();
        String string = name.substring(0, (name.length() - 4));
        String fileName = string + "_" + calendar.getTimeInMillis()
                          + name.substring((name.length() - 4));
        FileOutputStream fos = null;
        InputStream is = mFile.getInputStream();
        String path = UPLOADDIR + fileName;
        updateConfig.setUpdatePath(fileName);//设置文件名
        fos = new FileOutputStream(path);
        byte[] buff = new byte[1024];
        int len = 0;
        while ((len = is.read(buff)) > 0) {
            fos.write(buff, 0, len);
        }
        fos.flush();
        fos.close();
        is.close();
    }

    /**
     * 
     *下载文件
     * @param request 请求request
     * @param response 响应response
     * @param fileName 下载文件名
     * @throws Exception
     */
    public void download(HttpServletRequest request, HttpServletResponse response, String fileName)
                                                                                                   throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String downLoadPath = UPLOADDIR + fileName;
        String contentType = "application/octet-stream";
        long fileLength = new File(downLoadPath).length();
        response.setContentType(contentType);
        response.setHeader("Content-disposition",
            "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
        response.setHeader("Content-Length", String.valueOf(fileLength));

        FileInputStream inputStream = new FileInputStream(downLoadPath);
        ServletOutputStream out = response.getOutputStream();
        byte[] buffer = new byte[512];
        int b = 0;
        while (-1 != (b = inputStream.read(buffer, 0, buffer.length))) {
            out.write(buffer, 0, b);
        }
        inputStream.close();
        out.close();
        out.flush();
    }

    /**
     * 上传文件
     * @param request 请求request
     * @param params 参数数组
     * @param values 自定义参数值对
     * @return List<Map<String, Object>>
     * @throws Exception
     */
    public List<Map<String, Object>> upload(HttpServletRequest request, String[] params,
                                            Map<String, Object[]> values) throws Exception {

        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        //获取文件
        MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = mRequest.getFileMap();
        //获取存储文件路径
        File file = new File(UPLOADDIR);

        if (!file.exists()) {
            file.mkdir();
        }

        String fileName = null;
        int i = 0;
        for (Iterator<Map.Entry<String, MultipartFile>> it = fileMap.entrySet().iterator(); it
            .hasNext(); i++) {

            Map.Entry<String, MultipartFile> entry = it.next();
            MultipartFile mFile = entry.getValue();

            fileName = mFile.getOriginalFilename();

            String storeName = rename(fileName);

            String noZipName = UPLOADDIR + storeName;
            String zipName = zipName(noZipName);

            // 上传成为压缩文件
            ZipOutputStream outputStream = new ZipOutputStream(new BufferedOutputStream(
                new FileOutputStream(zipName)), Charset.forName("gbk"));
            outputStream.putNextEntry(new ZipEntry(fileName));
            //outputStream.setEncoding("GBK");

            FileCopyUtils.copy(mFile.getInputStream(), outputStream);

            Map<String, Object> map = new HashMap<String, Object>();
            // 固定参数值对
            map.put(FileOperateUtil.REALNAME, zipName(fileName));
            map.put(FileOperateUtil.STORENAME, zipName(storeName));
            map.put(FileOperateUtil.SIZE, new File(zipName).length());
            map.put(FileOperateUtil.SUFFIX, "zip");
            map.put(FileOperateUtil.CONTENTTYPE, "application/octet-stream");
            map.put(FileOperateUtil.CREATETIME, new Date());

            // 自定义参数值对
            for (String param : params) {
                map.put(param, values.get(param)[i]);
            }

            result.add(map);
        }
        return result;
    }

    /**
     * 下载
     * @param request 请求request
     * @param response 响应response
     * @param storeName 存储名称
     * @param contentType 设置response返回的类型
     * @param realName 真实名称
     * @throws Exception
     */

    public void download(HttpServletRequest request, HttpServletResponse response,
                         String storeName, String contentType, String realName) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        String downLoadPath = UPLOADDIR + storeName;

        long fileLength = new File(downLoadPath).length();

        response.setContentType(contentType);
        response.setHeader("Content-disposition",
            "attachment; filename=" + new String(realName.getBytes("utf-8"), "ISO8859-1"));
        response.setHeader("Content-Length", String.valueOf(fileLength));

        bis = new BufferedInputStream(new FileInputStream(downLoadPath));
        bos = new BufferedOutputStream(response.getOutputStream());
        byte[] buff = new byte[2048];
        int bytesRead;
        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
            bos.write(buff, 0, bytesRead);
        }
        bis.close();
        bos.close();
    }
}
