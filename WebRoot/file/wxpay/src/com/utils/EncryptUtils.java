package com.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
 
 
/**
 * 3des 加密　解密　 
 * @author LuoAnDong
 * 
 */
public class EncryptUtils
{
     
    /**
     * 定义 加密算法,可用<br/>
     * DES,DESede,Blowfish
     */
    private static final String Algorithm = "DESede"; 
    
    public static String Decrypt3DES(String value, String key) throws Exception
    {
        byte[] b = decryptMode(GetKeyBytes(key), SJBase64Encoder.decode(value));
        return new String(b);
    }
 
    /**
     * 3des加密
     * @param value 待加密字符串
     * @param key 原始密钥字符串
     * @return
     * @throws Exception
     */
    public static String Encrypt3DES(String value, String key) throws Exception
    {
        String str = byte2Base64(encryptMode(GetKeyBytes(key), value.getBytes()));
        return str;

    }
 
    /**
     * 计算24位长的密码byte值,首先对原始密钥做MD5算hash值，再用前8位数据对应补全后8位
     * @param strKey
     * @return
     * @throws Exception
     */
    public static byte[] GetKeyBytes(String strKey) throws Exception
    {
        if (null == strKey || strKey.length() < 1)
            throw new Exception("key is null or empty!");
        java.security.MessageDigest alg = java.security.MessageDigest
                .getInstance("MD5");
        alg.update(strKey.getBytes());
        byte[] bkey = alg.digest();
        int start = bkey.length;
        byte[] bkey24 = new byte[24];
        for (int i = 0; i < start; i++)
        {
            bkey24[i] = bkey[i];
        }
        for (int i = start; i < 24; i++)
        { 
            bkey24[i] = bkey[i - start];
        }
        return bkey24;
    }
     
    /**
     * keybyte为加密密钥，长度为24字节
     * src为被加密的数据缓冲区（源）  
     * @param keybyte
     * @param src
     * @return
     */
    public static byte[] encryptMode(byte[] keybyte, byte[] src) {

        try {
            //生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm); //加密 
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(src);
       } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }

        return null;

    }

    /**
     * keybyte为加密密钥，长度为24字节  
     * src为加密后的缓冲区
     * @param keybyte
     * @param src
     * @return
     */
    public static byte[] decryptMode(byte[] keybyte, byte[] src) {
        try { //生成密钥   
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
            //解密     
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }
 
    /**
     * 转换成base64编码
     * @param b
     * @return
     */
    public static String byte2Base64(byte[] b) {
        return SJBase64Encoder.encode(b);

    } 
    
    /**
     * 转换成十六进制字符串  
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
            if (n < b.length - 1)
                hs = hs + ":";
        }
        return hs.toUpperCase();
    }

}
