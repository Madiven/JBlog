package com.jblog.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class FileUtil {
    private static final String UTF_8 = "UTF-8";

    private static final String Unicode = "Unicode";

    private static final String UTF_16BE = "UTF-16BE";

    private static final String ANSI_ASCII = "ANSI|ASCII";

    private static final String GBK = "GBK";

    /**
     * 自动根据文件编码格式读取文件
     * @param filePath
     * @return
     * @throws Exception
     */
    public static BufferedReader readFile(String filePath) throws Exception {
        return new BufferedReader(new InputStreamReader(new FileInputStream(filePath), codeString(filePath)));
    }

    public static String codeString(String fileName) throws Exception {
        BufferedInputStream bin = new BufferedInputStream(
                new FileInputStream(fileName));
        int p = (bin.read() << 8) + bin.read();
        String code;
        //其中的 0xefbb、0xfffe、0xfeff、0x5c75这些都是这个文件的前面两个字节的16进制数
        switch (p) {
            case 0xefbb:
                code = UTF_8;
                break;
            case 0xfffe:
                code = Unicode;
                break;
            case 0xfeff:
                code = UTF_16BE;
                break;
            case 0x5c75:
                code = ANSI_ASCII;
                break;
            default:
                code = GBK;
        }

        return code;

    }
}
