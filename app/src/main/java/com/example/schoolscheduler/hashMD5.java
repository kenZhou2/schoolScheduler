package com.example.schoolscheduler;

import java.security.MessageDigest;

public class hashMD5 {
    public static byte[] hash(byte[] data) throws Exception{
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(data);
        return md5.digest();
    }
}
