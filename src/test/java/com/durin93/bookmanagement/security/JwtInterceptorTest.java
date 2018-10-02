package com.durin93.bookmanagement.security;

import com.durin93.bookmanagement.exception.JwtAuthorizationException;
import com.durin93.bookmanagement.support.exception.ErrorManager;
import com.durin93.bookmanagement.support.test.MockitoTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class JwtInterceptorTest extends MockitoTest {

    private MockHttpServletRequest request = new MockHttpServletRequest();

    private MockHttpServletResponse response = new MockHttpServletResponse();

    @Mock
    private JwtManager jwtManager;

    @InjectMocks
    private JwtInterceptor jwtInterceptor;

    @Before
    public void setUp() {
        request.setRequestURI("/test");
        request.setMethod("GET");
    }

    @Test
    public void prehandle() {
        request.addHeader("Authorization", "test");
        String jwt = "test";
        when(jwtManager.parse(jwt)).thenReturn(any());
        assertTrue(jwtInterceptor.preHandle(request, response, null));
    }

    @Test
    public void prehandle_fail() {
        thrown.expect(JwtAuthorizationException.class);
        thrown.expectMessage(ErrorManager.NOT_EXIST_TOKEN);
        jwtInterceptor.preHandle(request, response, null);
    }

}
