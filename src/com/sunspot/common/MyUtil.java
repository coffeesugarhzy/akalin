package com.sunspot.common;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
 
public class MyUtil
{

    public static int changeToInt(Object object)
    {
        return Integer.parseInt(object.toString());
    }

    public static String formatToString(Object object)
    {
        if (null == object) { return ""; }
        return String.valueOf(object);
    }

    public static double formatToDouble(Object object)
    {
        if (null == object) { return 0.00; }
        try
        {
            return Double.valueOf(String.valueOf(object));
        }
        catch (Exception e)
        {
            return 0.00;
        }
    }

    public static int formatToInt(Object object)
    {
        if (null == object) { return 0; }
        try
        {
            return Integer.valueOf(String.valueOf(object));
        }
        catch (Exception e)
        {
            return 0;
        }
    }

    public static int formatToIntSp(Object object)
    {
        if (null == object) { return -1; }
        try
        {
            return Integer.valueOf(String.valueOf(object));
        }
        catch (Exception e)
        {
            return -1;
        }
    }

    public static String getKeyword(String string)
    {
        StringBuffer stringBuffer = getStringBuffer();
        return stringBuffer.append("%").append(string).append("%").toString();
    }

    public static StringBuffer getStringBuffer()
    {
        return new StringBuffer();
    }

    public static String transformChar(String str)
    {
        try
        {
            str = new String(str.getBytes("iso-8859-1"), "UTF-8").trim();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            throw new RuntimeException("transformChar出错");
        }
        return str;
    }

    public static String delSpecialChar(String string)
    {
        String result = null;
        if (StringUtils.isNotBlank(string))
        {
            Pattern pattern = Pattern.compile("\\s*|\n|\r|\t");
            Matcher matcher = pattern.matcher(string.trim());
            result = matcher.replaceAll("");
        }
        return result;
    }

    public static void setSuccessUrl(Model model, String url)
    {
        model.addAttribute("url", url);
    }

    public static void removeFile(String fileName, HttpServletRequest request)
    {
        StringBuffer path = MyUtil.getStringBuffer();
        path.append(getDelFilePath(request)).append(fileName);
        File file = new File(path.toString());
        if (file.exists())
        {
            file.delete();
        }
    }

    public static String getUploadFileUrl(String currentDate, String newFileName)
    {
        StringBuffer url = MyUtil.getStringBuffer();
        return url.append("/uploadfile/").append(currentDate).append("/")
                .append(newFileName).toString();
    }

    public static String getDelFilePath(HttpServletRequest request)
    {
        StringBuffer path = MyUtil.getStringBuffer();
        path.append(request.getServletContext().getRealPath(""));
        return path.toString();
    }

    public static String getUploadFolderPath(HttpServletRequest request)
    {
        StringBuffer path = MyUtil.getStringBuffer();
        path.append(request.getServletContext().getRealPath(""))
                .append("/uploadfile/").append(DateUtil.getCurrentDate());
        String savePath = path.toString();
        File newFile = new File(savePath);
        if (!newFile.exists())
        {
            newFile.mkdirs();
        }
        return savePath;
    }

    public static String getNewFileName(String fileName)
    {
        StringBuffer name = MyUtil.getStringBuffer();
        return name.append(UUID.randomUUID().toString())
                .append(fileName.substring(fileName.lastIndexOf(".")))
                .toString();
    }

    public static CommonsMultipartResolver getCommonsMultipartResolver(
            HttpServletRequest request)
    {
        return new CommonsMultipartResolver(request.getServletContext());
    }

    public static String upload(MultipartHttpServletRequest request)
    {
        String uploadFileUrl = null;
        CommonsMultipartResolver commonsMultipartResolver = MyUtil
                .getCommonsMultipartResolver(request);
        try
        {
            List<MultipartFile> fileList = request.getFiles("file");
            if (fileList.size() > 0)
            {
                String savePath = MyUtil.getUploadFolderPath(request);
                String currentDate = DateUtil.getCurrentDate();
                for (MultipartFile multipartFile : fileList)
                {
                    String fileName = multipartFile.getOriginalFilename();
                    if (StringUtils.isNotBlank(fileName))
                    {
                        String newFileName = MyUtil.getNewFileName(fileName);
                        uploadFileUrl = MyUtil.getUploadFileUrl(currentDate,
                                newFileName);
                        File uploadFile = new File(savePath, newFileName);
                        FileUtils.copyInputStreamToFile(
                                multipartFile.getInputStream(), uploadFile);
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return uploadFileUrl;
        }
        finally
        {
            commonsMultipartResolver.cleanupMultipart(request);
        }
        return uploadFileUrl;
    }
}
