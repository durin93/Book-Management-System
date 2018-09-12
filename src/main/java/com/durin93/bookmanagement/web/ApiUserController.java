package com.durin93.bookmanagement.web;

import com.durin93.bookmanagement.domain.User;
import com.durin93.bookmanagement.dto.UserDto;
import com.durin93.bookmanagement.service.UserService;
import com.durin93.bookmanagement.support.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import javax.naming.CannotProceedException;
import javax.servlet.http.HttpSession;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {

    private static final Logger logger = LoggerFactory.getLogger(ApiUserController.class);

    private UserService userService;

    public ApiUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<UserDto> regist(@RequestBody UserDto userDto) throws CannotProceedException {
        UserDto registerUser = userService.regist(userDto);
        registerUser.add(linkTo(ApiUserController.class).slash(registerUser.getId()).withSelfRel());
        return new ResponseEntity<>(registerUser, HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<UserDto> login(@RequestBody UserDto userDto, HttpSession session) throws AuthenticationException {
        UserDto loginUser = userService.login(userDto.getUserId(), userDto.getPassword());
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, loginUser);
        loginUser.add(linkTo(ApiUserController.class).slash(loginUser.getId()).withSelfRel());
        return new ResponseEntity<>(loginUser, HttpStatus.OK);
    }

}
