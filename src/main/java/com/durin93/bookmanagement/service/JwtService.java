package com.durin93.bookmanagement.service;

import com.durin93.bookmanagement.exception.JwtAuthorizationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class JwtService {

    private static final Logger log = LoggerFactory.getLogger(JwtService.class);

    private static final String SALT = "duBookSugar";

    private int expireHour = 1;

    private int expireTime = 1000 * 60 * 60 * expireHour;


    public <T> String create(String key, T data) {
        String jwt = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setIssuer("dubook.com")
                .setSubject("authentication")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .claim(key, data)
                .signWith(SignatureAlgorithm.HS256, generateKey())
                .compact();
        return jwt;
    }


    private byte[] generateKey() {
        byte[] key = null;
        try {
            key = SALT.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("Making JWT Key Error ::: {}", e.getMessage());
        }
        return key;
    }

    public boolean isUsable(String jwt) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(generateKey())
                    .parseClaimsJws(jwt);
            return true;
        } catch (Exception e) {
            log.debug(e.toString() + "," + e.getMessage());
            throw new JwtAuthorizationException();
        }
    }

    public Map<String, Object> get(String key) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String jwt = request.getHeader("Authorization");
        Jws<Claims> claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SALT.getBytes("UTF-8"))
                    .parseClaimsJws(jwt);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new JwtAuthorizationException();
        }
        Map<String, Object> value = (LinkedHashMap<String, Object>) claims.getBody().get(key);
        return value;
    }

    public String getUserId() {
        return String.valueOf(get("userInfo").get("userId"));
    }

}
