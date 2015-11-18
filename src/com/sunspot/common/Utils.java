package com.sunspot.common;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.google.gson.Gson;

/**
 * 工具类
 * 
 * @author LuoAnDong
 * 
 */
public class Utils
{
	/**
	 * 对象转换成Json
	 * 
	 * @param object
	 * @return
	 */
	public static String ObjectToJson(Object object)
	{
		Gson gson = new Gson();
		java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<Object>>()
		{
		}.getType();
		String objectJson = gson.toJson(object, type);
		return objectJson;
	}

	/**
	 * 得到当前时间　
	 * 
	 * @return
	 * @author LuoAnDong
	 */
	public static String getNowDate()
	{
		return getCurDate("yyyy-MM-dd HH:mm:ss");
	}

	// 前置编号
	private static int PRECODE = 0;

	/**
	 * 获取当前日期
	 * 
	 * @param partten
	 *            日期格式
	 * @return 字符串日期
	 */
	public static String getCurDate(String partten)
	{
		SimpleDateFormat simple = new SimpleDateFormat(partten);
		return simple.format(new Date());
	}

	/**
	 * 获取星期几 日：0 一：1 二：2 三：3 四：4 五：5 六：6
	 * 
	 * @return
	 */
	public static Integer getWeekDay()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
		{
			w = 0;
		}
		return w;
	}

	// 获取我的订单 号
	public static String getMyOrderCode()
	{
		UUID uuid = UUID.randomUUID();
		String uuidStr = uuid.toString().replaceAll("-", "");
		if (PRECODE >= 9999)
		{
			PRECODE = 0;
		}
		else
		{
			PRECODE++;
		}
		return uuidStr.substring(10, 18) + String.valueOf(PRECODE);
	}

	/**
	 * 获取短信验证码
	 * 
	 * @return 字符串
	 */
	public static String smsCheckCode()
	{
		String curDateMills = String.valueOf(System.currentTimeMillis());
		return curDateMills.substring(curDateMills.length() - 6);
	}

	/**
	 * 对象转字符串
	 * 
	 * @param object
	 *            对象
	 * @return String
	 */
	public static String objToStr(Object object)
	{
		if (null == object)
		{
			return "";
		}
		return String.valueOf(object);
	}

	/**
	 * 对象转双精度
	 * 
	 * @param object
	 *            对象
	 * @return double
	 */
	public static double objToDouble(Object object)
	{
		if (null == object)
		{
			return 0.00;
		}
		try
		{
			return Double.valueOf(String.valueOf(object));
		}
		catch (Exception e)
		{
			return 0.00;
		}
	}

	/**
	 * 对象转int
	 * 
	 * @param object
	 *            对象
	 * @return int
	 */
	public static int objToInt(Object object)
	{
		if (null == object)
		{
			return 0;
		}
		try
		{
			return Integer.valueOf(String.valueOf(object));
		}
		catch (Exception e)
		{
			return 0;
		}
	}

	public static int objToIntSp(Object object)
	{
		if (null == object)
		{
			return -1;
		}
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
		StringBuffer stringBuffer = new StringBuffer();
		return stringBuffer.append("%").append(string).append("%").toString();
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

	/**
	 * 获取cookie
	 * 
	 * @param cookieName
	 *            cookie名称
	 * @param request
	 *            请求句柄
	 * @return cookie值
	 */
	public static String getCookie(String cookieName, HttpServletRequest request)
	{
		Cookie[] cookies = request.getCookies();
		if(cookies == null) return null ; 
		for (Cookie cookie : cookies)
		{
			if (cookieName.equals(cookie.getName()))
			{
				return cookie.getValue();
			}
		}
		return null;
	}

	/**
	 * 删除cookie
	 * 
	 * @param cookieName
	 *            cookie名称
	 * @param request
	 *            请求句柄
	 * @return cookie值
	 */
	public static void delCookie(String cookieName, HttpServletRequest request,
	        HttpServletResponse response)
	{
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies)
		{
			if (cookieName.equals(cookie.getName()))
			{
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
		}
	}

	// 加码
	public static String escape(String src)
	{
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (i = 0; i < src.length(); i++)
		{
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j)
			        || Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256)
			{
				tmp.append("%");
				if (j < 16)
					tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			}
			else
			{
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}

	// 解码
	public static String unescape(String src)
	{
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length())
		{
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos)
			{
				if (src.charAt(pos + 1) == 'u')
				{
					ch = (char) Integer.parseInt(
					        src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				}
				else
				{
					ch = (char) Integer.parseInt(
					        src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			}
			else
			{
				if (pos == -1)
				{
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				}
				else
				{
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}

	/**
	 * 上传文件
	 * 
	 * @param request
	 *            请求句柄
	 * @return 入库路径
	 */
	public static final String uploadFile(HttpServletRequest request)
	{

		String inDBPath = "";
		int fileNum = 0;

		// 创建一个通用的多部分解析器
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
		        request.getSession().getServletContext());

		// 判断 request 是否有文件上传,即多部分请求
		if (multipartResolver.isMultipart(request))
		{
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

			// 取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			try
			{
				MultipartFile multipartFile;
				File localFile;
				String fileName;
				String newfileName;

				// 入库地址
				inDBPath = "/uploadfile/" + Utils.getCurDate("yyyy-MM-dd");

				// 本地地址
				String localPath = FileUtils.getServerFilePath(request,
				        inDBPath);
				while (iter.hasNext())
				{
					// 取得上传文件
					multipartFile = multiRequest.getFile(iter.next());

					if (null == multipartFile)
					{
						continue;
					}

					// 取得当前上传文件的文件名称
					fileName = multipartFile.getOriginalFilename();

					// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
					if (StringUtils.isNotBlank(fileName))
					{
						fileNum++;
						newfileName = "/" + FileUtils.getNewFileName(fileName);
						localFile = new File(localPath + newfileName);
						multipartFile.transferTo(localFile);
						inDBPath += newfileName;
					}

				}
			}
			catch (IllegalStateException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			finally
			{
				// 清除临时文件
				if (multiRequest != null)
				{
					multipartResolver.cleanupMultipart(multiRequest);
				}
			}
		}

		if (0 < fileNum)
		{
			return inDBPath;
		}
		else
		{
			return "";
		}
	}

	
	
	/**
	 * 上传验证信息
	 * @param request
	 * @param param
	 * @param preFix 商家ID
	 * @return
	 */
	public static String  uploadValidImages(HttpServletRequest request,String param,String filePath,String preFix){
		
		String inDBPath = "";
		
    	MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
        
    	//获得文件对象
    	CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile(param); 
    	
    	if(file==null){
    		   return "";
    	}
    	
    	if("valid".equals(filePath)){
			// 入库地址
			inDBPath = "/uploadfile/"+filePath+"/" + preFix;
    	}else{
			// 入库地址
			inDBPath = "/uploadfile/" + Utils.getCurDate("yyyy-MM-dd");
    	}
		// 本地地址
		String localPath = FileUtils.getServerFilePath(request,inDBPath);
		
		// 取得当前上传文件的文件名称
		String fileName = file.getOriginalFilename();
		
		String newfileName = "/" +param+ FileUtils.getNewFileName(fileName);
		File localFile = new File(localPath + newfileName);
		try {
			file.transferTo(localFile);
			inDBPath += newfileName;
		  } catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return inDBPath;
	}
}
