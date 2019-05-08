package com.example.xxovek.foodkor;

import java.util.HashMap;

/**
 * Created by rockstar on 13/4/19.
 */

public class GetClassCode {
    static HashMap<String, String> codeHash = new HashMap<String, String>();

    static {
        init();
    }

    public static void init() {
        codeHash.put("key", "value");
        codeHash.put("key", "value");
        codeHash.put("key", "value");
        codeHash.put("key", "value");

    }

    public static String getCode(String param) {
        return codeHash.get(param);
    }
}
