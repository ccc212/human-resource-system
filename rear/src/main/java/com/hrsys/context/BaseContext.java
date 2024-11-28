package com.hrsys.context;

import io.jsonwebtoken.Claims;


public class BaseContext {

    public static ThreadLocal<Claims> claimsThreadLocal = new ThreadLocal<>();

    public static void setClaims(Claims claims) {
        claimsThreadLocal.set(claims);
    }

    public static Claims getClaims() {
        return claimsThreadLocal.get();
    }

    public static void removeClaims() {
        claimsThreadLocal.remove();
    }

}
