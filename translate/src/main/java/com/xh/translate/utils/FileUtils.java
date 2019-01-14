package com.xh.translate.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileUtils {

    public static String readFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            throw new RuntimeException("File does not exist: " + path);
        }
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();

        try {
            FileInputStream reader = new FileInputStream(file);
            byte[] buff = new byte[0x1000];
            int i = 0;
            while ((i = reader.read(buff, 0, 0x1000)) > 0) {
                byteArray.write(buff, 0, i);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteArray.toString();
    }

}
