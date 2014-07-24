package com.ahandy.util;

import java.util.HashMap;

/**
 * A utilize class that get unique id.
 */
public class IdUtils {

    private static final int DEFAULT_VALUE = 0;

    private static HashMap<String, Integer> sIdMap = new HashMap<String, Integer>();

    private IdUtils() {
    }

    public static int getUniqueId(String tag) {
        if (!sIdMap.containsKey(tag)) {
            initId(tag);
            return DEFAULT_VALUE;
        }

        int id = sIdMap.get(tag) + 1;
        sIdMap.put(tag, id);
        return id;
    }

    private static void initId(String tag) {
        if(sIdMap.containsKey(tag)) {
            return;
        }

        sIdMap.put(tag, DEFAULT_VALUE);
    }

    public static void reset(String tag) {
        sIdMap.remove(tag);
    }

    public static void reset() {
        sIdMap.clear();
    }
}
