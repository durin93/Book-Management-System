package com.durin93.bookmanagement.security;

import com.durin93.bookmanagement.domain.User;
import com.durin93.bookmanagement.exception.JwtAuthorizationException;
import com.durin93.bookmanagement.support.exception.ErrorManager;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@Component
public class JwtManager {

    private static final Logger logger = LoggerFactory.getLogger(JwtManager.class);

    private static final String ISSUER = "dubook.com";
    private static final String CLAIM_KEY_USER_ID = "userId";
    private static final String SALT = "duBookSugar";

    private int expireHour = 3600000;
    private int expireTime = 3 * expireHour;

    public String create(User user) {
        String jwt = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", HS256)
                .setIssuer(ISSUER)
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .claim(CLAIM_KEY_USER_ID, user.getUserId())
                .signWith(SignatureAlgorithm.HS256, generateKey())
                .compact();
        return jwt;
    }

    private byte[] generateKey() {
        byte[] key = null;
        try {
            key = SALT.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("jwt generate error ::: {}", e.getMessage());
        }
        return key;
    }


    public String decode() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String jwt = request.getHeader("Authorization");
        try {
            Jws<Claims> claims = parse(jwt);
            return claims.getBody().get(CLAIM_KEY_USER_ID).toString();
        } catch (Exception e) {
            throw new JwtAuthorizationException(ErrorManager.INVALID_TOKEN);
        }
    }

    public Jws<Claims> parse(String jwt){
        return Jwts.parser()
                .setSigningKey(generateKey())
                .parseClaimsJws(jwt);
    }

}
