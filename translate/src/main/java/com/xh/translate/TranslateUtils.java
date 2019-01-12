package com.xh.translate;


import android.graphics.Matrix;
import android.graphics.RectF;

import java.nio.charset.Charset;

public class TranslateUtils {

    public static void main(String[] args) {
//        testIOS();

        RectF rect = new RectF(100, 0, 200, 100);

        Matrix matrix = new Matrix();
        matrix.setScale(2,2, 100, 0);
        matrix.postTranslate(-100, 0);

        matrix.mapRect(rect);

        System.out.println(rect);

    }


    public static void testIOS() {

        for (int i = 127; i < 200; i++) {
            byte b = (byte) i;

            byte[] bytes = new byte[1];
            bytes[0] = b;
            String str = new String(bytes, Charset.forName("ISO-8859-2"));
            System.out.println(i + " :" + str + ";");
        }


    }


}
