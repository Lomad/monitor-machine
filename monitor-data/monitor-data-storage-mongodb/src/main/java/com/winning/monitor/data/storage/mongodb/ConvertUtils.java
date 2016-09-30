package com.winning.monitor.data.storage.mongodb;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by nicholasyan on 16/9/29.
 */
public class ConvertUtils {

    public static String getStringValue(Object value) {
        return value == null ? null : value.toString();
    }

    public static int getIntValue(Object value) {
        return value == null ? -1 : Integer.parseInt(value.toString());
    }

    public static Set<String> getStringSetValue(Object value) {
        if (value instanceof List) {
            LinkedHashSet<String> set = new LinkedHashSet<>();
            for (Object s : ((List) value)) {
                set.add(getStringValue(s));
            }
        }
        return null;
    }

}
