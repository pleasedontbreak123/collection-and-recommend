package org.example.collectionandrecommend.demos.web.utils.Util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5Util {
    public static String md5Encrypt(String input) {
        try {
            // 创建MessageDigest对象
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 将输入字符串转换为字节数组并计算MD5哈希值
            byte[] messageDigest = md.digest(input.getBytes());

            // 将字节数组转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5加密失败", e);
        }
    }
}
