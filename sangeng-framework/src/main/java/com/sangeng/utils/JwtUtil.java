package com.sangeng.utils;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class JwtUtil {

    public static final Long JWT_TTL = 24*60*60*1000L;
    public static final String JWT_KEY = "sangeng";

    public static String getUUID(){
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        return token;
    }

    public static String createJWT(String id, String sub, Long ttlMillis){
        JwtBuilder builder = getJwtBuilder(id,sub,ttlMillis);
        return builder.compact();
    }

    public static SecretKey generalKey(){
        byte[] encodeKey = Base64.getDecoder().decode(JWT_KEY);
        SecretKey key = new SecretKeySpec(encodeKey, 0, encodeKey.length, "AES");
        return key;
    }

    public static String createJWT(String sub){
        JwtBuilder builder = getJwtBuilder(getUUID(), sub, null);
        return builder.compact();
    }

    private static JwtBuilder getJwtBuilder(String id, String sub, Long ttlMillis) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if (ttlMillis == null || ttlMillis <= 0) {
            ttlMillis = JWT_TTL;
        }
        long expMillis = nowMillis + ttlMillis;
        Date exp = new Date(expMillis);

        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setSubject(sub)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, JWT_KEY);

        return builder;
    }
}
