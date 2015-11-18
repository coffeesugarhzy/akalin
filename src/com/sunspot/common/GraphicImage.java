package com.sunspot.common;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.ImageIcon;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 重绘图片
 * @author QinKeChun
 *
 */
public class GraphicImage {
	
	public static int imageWidth =640;
	public static int imageHeight =388;
	
	public static void graphicsGeneration (int imageWidth,int imageHeight,String imgurl){
		

		BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = image.getGraphics();
		graphics.fillRect(0, 0, imageWidth, imageHeight);
		
		ImageIcon imageIcon = new ImageIcon(imgurl);
	    int iconWidth=imageIcon.getIconWidth();
		int iconHeight=imageIcon.getIconHeight();

		float cWidth=(imageHeight*iconWidth)/iconHeight;
	    graphics.drawImage(imageIcon.getImage(),(int)((imageWidth-cWidth)/2),0,(int)cWidth,imageHeight, null);
	    
		graphics.dispose();
		File file = new File(imgurl); 
		file.delete(); 
		try
		{
			FileOutputStream fos = new FileOutputStream(imgurl);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
			encoder.encode(image);
			bos.close();
		} 
		catch (Exception e){
			e.printStackTrace();
		}
	}
}
