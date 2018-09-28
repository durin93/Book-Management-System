package com.durin93.bookmanagement.support.test;

import com.durin93.bookmanagement.domain.User;
import com.durin93.bookmanagement.dto.BookDto;
import com.durin93.bookmanagement.dto.UserDto;
import com.durin93.bookmanagement.repository.UserRepository;
import com.durin93.bookmanagement.security.JwtManager;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

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

    protected <T> ResponseEntity<T> requestGET(String resourceUrl, HttpEntity requestEntity, Class<T> responseType) {
        return template().exchange(resourceUrl, HttpMethod.GET, requestEntity, responseType);
    }

    protected <T> ResponseEntity<T> requestPUT(String resourceUrl, HttpEntity requestEntity, Class<T> responseType) {
        return template().exchange(resourceUrl, HttpMethod.PUT, requestEntity, responseType);
    }

    protected <T> ResponseEntity<T> requestDELETE(String resourceUrl, HttpEntity requestEntity, Class<T> responseType) {
        return template().exchange(resourceUrl, HttpMethod.DELETE, requestEntity, responseType);
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

    protected HttpEntity jwtEntityForm(User user) {
        return new HttpEntity(jwtHeadersFormType(user));
    }


    ///Book
    protected String getResourceUrl(BookDto bookDto, String rel) {
        return bookDto.getLink(rel).getHref();
    }

    protected BookDto createBookDefault() {
        return new BookDto("스페인 너는 자유다", "손미나", LocalDate.of(2006, 7, 28), 340, 582);
    }

    protected BookDto createBookDefault2() {
        return new BookDto("여행의 기술", "알랭 드 보통", LocalDate.of(2011, 12, 10), 328, 470);
    }

    protected BookDto createBookDefault3() {
        return new BookDto("내가 사랑한 유럽 TOP10", "정여울", LocalDate.of(2014, 1, 10), 380, 575);
    }

    protected BookDto createBook(BookDto bookDto) {
        return template().
                postForEntity("/api/books", jwtEntity(findManagerUser(), bookDto), BookDto.class)
                .getBody();
    }

    //User

    protected UserDto createUserDefault() {
        return new UserDto("test1", "password", "name");
    }

    protected UserDto createUser(UserDto userDto) {
        return template().
                postForEntity("/api/users", jwtEntity(findManagerUser(), userDto), UserDto.class)
                .getBody();
    }

    protected String getResourceUrl(UserDto userDto, String rel) {
        return userDto.getLink(rel).getHref();
    }
    protected Map<String,String> loginUserMap(){
        Map<String, String> loginUser =  new HashMap<>();
        loginUser.put("userId", "durin93");
        loginUser.put("password", "1234");
        return loginUser;
    }


}
