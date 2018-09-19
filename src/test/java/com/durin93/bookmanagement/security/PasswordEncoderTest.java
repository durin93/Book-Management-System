package com.durin93.bookmanagement.security;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PasswordEncoderTest {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private String password;

    @Before
    public void setUp() {
        password = passwordEncoder.encode("1234");
    }

    @Test
    public void matches() {
        assertTrue(passwordEncoder.matches("1234",password));
    }

    @Test
    public void matches_fail() {
        assertFalse(passwordEncoder.matches("12345",password));
    }

}
