package com.imooc.selldev.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019-02-01 14:06
 * @description ：com.imooc.selldev.utils
 * @function ：
 */
public class JsonUtil {

    public static String toJson(Object object) {

        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        final Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }
}
