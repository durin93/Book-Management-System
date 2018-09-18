package com.durin93.bookmanagement.security;

import com.durin93.bookmanagement.exception.JwtAuthorizationException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class JwtInterceptorTest {

    private MockHttpServletRequest request = new MockHttpServletRequest();

    private MockHttpServletResponse response = new MockHttpServletResponse();

    @Mock
    private JwtManager jwtManager;

    @InjectMocks
    private JwtInterceptor jwtInterceptor;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp(){
        request.setRequestURI("/test");
        request.setMethod("GET");
        request.addHeader("Authorization", "test");
    }

    @Test
    public void prehandle(){
        String jwt = "test";
        when(jwtManager.isUsable(jwt)).thenReturn(true);
        assertTrue(jwtInterceptor.preHandle(request,response,null));
    }

    @Test
    public void prehandle_fail(){
        thrown.expect(JwtAuthorizationException.class);
        jwtInterceptor.preHandle(request,response,null);
    }

}
