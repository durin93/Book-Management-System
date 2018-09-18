package com.durin93.bookmanagement.support.test;

import com.durin93.bookmanagement.domain.User;
import com.durin93.bookmanagement.dto.BookDto;
import com.durin93.bookmanagement.repository.UserRepository;
import com.durin93.bookmanagement.security.JwtManager;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest {

    private static final String MANAGER_USER = "durin93";
    private static final String NORMAL_USER = "lsc109";

    @Autowired
    protected TestRestTemplate testRestTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtManager jwtManager;


    public TestRestTemplate template() {
        return testRestTemplate;
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

    protected <T> ResponseEntity<T> requestGET(String resourceUrl, HttpEntity requestEntity, Class<T> responseType){
        return template().exchange(resourceUrl,HttpMethod.GET,requestEntity, responseType);
    }

    protected <T> ResponseEntity<T> requestPUT(String resourceUrl, HttpEntity requestEntity, Class<T> responseType){
        return template().exchange(resourceUrl,HttpMethod.PUT,requestEntity, responseType);
    }

    protected <T> ResponseEntity<T> requestDELETE(String resourceUrl, HttpEntity requestEntity, Class<T> responseType){
        return template().exchange(resourceUrl,HttpMethod.DELETE, requestEntity, responseType);
    }

    protected String createJwt(User user) {
        return jwtManager.create(user);
    }

    protected HttpHeaders jwtHeaders(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Authorization", createJwt(user));
        return headers;
    }

    protected HttpHeaders jwtHeadersFormType(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", createJwt(user));
        return headers;
    }

    protected <T> HttpEntity<T> jwtEntity(User user, Object object) {
        return new HttpEntity<>((T) object, jwtHeaders(user));
    }

    protected HttpEntity jwtEntity(User user) {
        return new HttpEntity(jwtHeaders(user));
    }

    protected  HttpEntity jwtEntityForm(User user) {
        return new HttpEntity(jwtHeadersFormType(user));
    }


    ///Book
    protected String getResourceUrl(BookDto bookDto, String rel) {
        return bookDto.getLink(rel).getHref();
    }

    protected BookDto createBookDefault() {
        return new BookDto("스페인 너는 자유다", "손미나");
    }

    protected BookDto createBookDefault2() {
        return new BookDto("여행의 기술", "알랭 드 보통");
    }

    protected BookDto createBook(BookDto bookDto) {
        return template().
                postForEntity("/api/books", jwtEntity(findManagerUser(), bookDto), BookDto.class)
                .getBody();
    }

}
