package com.sunspot.common;

import java.io.File;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
 
public class FileUtils
{
    /**
     * 获取新的文件名
     * 
     * @param fileName
     *            文件名
     * @return
     */
    public static String getNewFileName(String fileName)
    {
        StringBuffer name = new StringBuffer();
        return name.append((UUID.randomUUID().toString()).replaceAll("-", ""))
                .append(fileName.substring(fileName.lastIndexOf(".")))
                .toString();
    }

    /**
     * 获取服务器文件路径
     * 
     * @param request
     * @return
     */
    public static String getServerFilePath(HttpServletRequest request,
            String inDBPath)
    {

        /** 得到文件保存目录的真实路径* */
        String serverFilePath = request.getSession().getServletContext()
                .getRealPath(inDBPath);

        /** 根据真实路径创建目录* */
        File serverFile = new File(serverFilePath);
        if (!serverFile.exists())
        {
            serverFile.mkdirs();
        }
        return serverFilePath;
    }

    /**
     * 删除文件
     * 
     * @param fullFileName
     */
    public static void removeFile(String fullFileName)
    {
        File file = new File(fullFileName);
        if (file.exists())
        {
            file.delete();
        }
    }

    public static String getServerPath(HttpServletRequest request)
    {
        StringBuffer path = MyUtil.getStringBuffer();
        path.append(request.getServletContext().getRealPath(""))
                .append("/uploadFile/").append(DateUtil.getCurrentDate());
        String savePath = path.toString();
        File newFile = new File(savePath);
        if (!newFile.exists())
        {
            newFile.mkdirs();
        }
        return savePath;
    }

}
