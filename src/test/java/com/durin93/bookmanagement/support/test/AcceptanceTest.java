package com.durin93.bookmanagement.support.test;

import com.durin93.bookmanagement.domain.User;
import com.durin93.bookmanagement.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AcceptanceTest {

    private static final String MANAGER_USER = "durin93";

    @Autowired
    protected  TestRestTemplate testRestTemplate;

    @Autowired
    private UserRepository userRepository;


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

    protected User findByUserId(String userId) {
        return userRepository.findByUserId(userId).get();
    }


}
