package me.lily.movie.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@Slf4j
public class Sha256Utils {
    public static String getSha256ByBytes(byte[] bytes) {
        return org.apache.commons.codec.digest.DigestUtils.sha256Hex(bytes);
    }

    public static String getSha256ByStr(String str) {
        return org.apache.commons.codec.digest.DigestUtils.sha256Hex(str);
    }


    /**
     * 利用java原生的类实现SHA256加密
     *
     * @param str
     * @return
     */
    public static String getSHA256(String str) {
        MessageDigest messageDigest;
        String encodestr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodestr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            log.error("Sha256Utils getSHA256 方法调用失败 {}", e.getMessage());
            log.error("error info is {}", e.getStackTrace().length > 0 ? e.getStackTrace()[0] : "no stack trace");
        } catch (UnsupportedEncodingException e) {
            log.error("Sha256Utils getSHA256 方法调用失败 {}", e.getMessage());
            log.error("error info is {}", e.getStackTrace().length > 0 ? e.getStackTrace()[0] : "no stack trace");
        }
        return encodestr;
    }


    //15 转16进制

    /**
     * 将byte转为16进制
     *
     * @param bytes
     * @return
     */
    public static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

}
