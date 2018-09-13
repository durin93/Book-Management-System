package com.durin93.bookmanagement.support.test;

import com.durin93.bookmanagement.domain.User;
import com.durin93.bookmanagement.repository.UserRepository;
import com.durin93.bookmanagement.service.JwtService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.UnsupportedEncodingException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public abstract class AcceptanceTest {

    private static final String MANAGER_USER = "durin93";
    private static final String NORMAL_USER = "lsc109";

    @Autowired
    protected  TestRestTemplate testRestTemplate;

    @Autowired
    protected WebTestClient webTestClient;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;


    public TestRestTemplate template() {
        return testRestTemplate;
    }

    public TestRestTemplate basicAuthTemplate() {
        return basicAuthTemplate(findManagerUser());
    }

    public TestRestTemplate basicAuthTemplate(User loginUser){
        return testRestTemplate.withBasicAuth(loginUser.getUserId(), loginUser.getPassword());
    }

    protected User findManagerUser() {
        return findByUserId(MANAGER_USER);
    }

    protected User findNormalUser() {
        return findByUserId(NORMAL_USER);
    }

    protected User findByUserId(String userId) {
        return userRepository.findByUserId(userId).get();
    }


    protected <T> ResponseEntity<T> getResource(String location, Class<T> responseType, User loginUser) {
        return basicAuthTemplate(loginUser).getForEntity(location, responseType);
    }

    protected <T> ResponseEntity<T> getResource(String location, Class<T> responseType) {
        return basicAuthTemplate(findManagerUser()).getForEntity(location, responseType);
    }

    protected String createJwt(User user){
        return jwtService.create("userInfo",user);
    }

    protected HttpHeaders jwtHeaders(User user){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", createJwt(user));
        return headers;
    }

    public  <T> HttpEntity<T> httpEntity(HttpHeaders headers, Object object) {
        return new HttpEntity<>((T)object,headers);
    }

    public  HttpEntity httpEntity(HttpHeaders headers) {
        return new HttpEntity(headers);
    }

}
