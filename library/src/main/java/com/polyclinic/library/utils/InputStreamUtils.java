package com.polyclinic.library.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * @author Lxg
 * @create 2020/4/1
 * @Describe
 */
public class InputStreamUtils {
public static String  InputStreamToString(InputStream inputStream){
    InputStreamReader inputStreamReader = null;
    try {
        inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
    } catch (UnsupportedEncodingException e1) {
        e1.printStackTrace();
    }
    BufferedReader reader = new BufferedReader(inputStreamReader);
    StringBuffer sb = new StringBuffer("");
    String line;
    try {
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return sb.toString();
}
}
