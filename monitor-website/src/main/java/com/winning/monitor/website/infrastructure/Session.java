package com.winning.monitor.website.infrastructure;

import javax.servlet.http.HttpSession;

/**
 * Created by Admin on 2016/9/7.
 */
public class Session {

    private static ThreadLocal<HttpSession> threadLocal = new ThreadLocal<HttpSession>();

    public static Object get(String key) {
        return threadLocal.get().getAttribute(key);
    }

    public static void set(String key, Object object) {
        threadLocal.get().setAttribute(key, object);
    }

    public static void setSession(HttpSession session) {
        threadLocal.set(session);
    }
    public static void removeSession() {
        threadLocal.get().removeAttribute("loginId");
    }

}

