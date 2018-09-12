package com.durin93.bookmanagement.web;

import com.durin93.bookmanagement.dto.UserDto;
import com.durin93.bookmanagement.support.test.AcceptanceTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class ApiBookAcceptanceTest extends AcceptanceTest {

    private static final Logger logger = LoggerFactory.getLogger(ApiBookAcceptanceTest.class);


}
