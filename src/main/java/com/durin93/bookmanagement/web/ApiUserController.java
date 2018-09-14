package com.durin93.bookmanagement.web;

import com.durin93.bookmanagement.domain.User;
import com.durin93.bookmanagement.dto.UserDto;
import com.durin93.bookmanagement.service.JwtService;
import com.durin93.bookmanagement.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {

    private static final Logger log = LoggerFactory.getLogger(ApiUserController.class);

    private JwtService jwtService;

    private UserService userService;

    @Autowired
    public ApiUserController(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<UserDto> regist(@RequestBody UserDto userDto) {
        UserDto registerUser = userService.regist(userDto);
        addSelfDescription(registerUser);
        return new ResponseEntity<>(registerUser, HttpStatus.CREATED);
    }


    @PostMapping("login")
    public ResponseEntity<UserDto> login(@RequestBody UserDto userDto, HttpServletResponse response) throws UnsupportedEncodingException {
        User loginUser = userService.login(userDto);
        String token = jwtService.create("userId", loginUser.getUserId());
        response.setHeader("Authorization", token);
        return new ResponseEntity<>(loginUser.toUserDto(), HttpStatus.OK);
    }


    @GetMapping("{id}")
    public ResponseEntity<UserDto> show(@PathVariable Long id) {
        UserDto userDto = userService.findById(id);
        addSelfDescription(userDto);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    private void addSelfDescription(UserDto userDto) {
        userDto.add(linkTo(ApiUserController.class).slash(userDto.getId()).withSelfRel());
    }

}
