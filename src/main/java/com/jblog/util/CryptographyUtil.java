package com.jblog.util;

import org.apache.shiro.crypto.hash.Md5Hash;

public class CryptographyUtil {
    public static String md5(String str){
        return new Md5Hash(str,"java_jblog",3).toString();
    }

    public static void main(String[] args) {
        String password="123456";

        System.out.println("Md5加密："+ CryptographyUtil.md5(password));
    }
}
