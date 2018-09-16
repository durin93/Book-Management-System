package com.durin93.bookmanagement.domain;

import com.durin93.bookmanagement.support.JwtManager;
import com.durin93.bookmanagement.support.domain.Level;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class JwtManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();


    private JwtManager jwtManager = new JwtManager();

    private User user;

    @Before
    public void setup() {
        user = new User("user", "password", "테스터", Level.USER);
    }

    @Test
    public void create(){

        String jwt = jwtManager.create(user);

        Jws<Claims> claims = jwtManager.parse(jwt);
        assertTrue(jwtManager.isUsable(jwt));
        assertThat(claims.getBody().get("userId"), is("user"));
    }

    @Test
    public void expireJwt() {
        String jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJkdWJvb2suY29tIiwiZXhwIjoxNTM3MDgwNDM5LCJ1c2VySWQiOiJkdXJpbjkzIn0.7OgAOWCIG1OYLttPvhotXoH_5q0AW9srQXWFrmiCh20";
        thrown.expect(ExpiredJwtException.class);
        Jws<Claims> claims = jwtManager.parse(jwt);
        fail();
    }

}
