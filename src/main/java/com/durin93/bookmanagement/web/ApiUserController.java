package com.durin93.bookmanagement.web;

import com.durin93.bookmanagement.domain.User;
import com.durin93.bookmanagement.dto.UserDto;
import com.durin93.bookmanagement.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {

    private static final Logger log = LoggerFactory.getLogger(ApiUserController.class);

    private UserService userService;

    public ApiUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<UserDto> regist(@RequestBody UserDto userDto) {
        UserDto registerUser = userService.regist(userDto);
        return new ResponseEntity<>(registerUser, HttpStatus.CREATED);
    }


    @PostMapping("authentication")
    public ResponseEntity<UserDto> login(@RequestBody UserDto userDto, HttpServletResponse response) {
        User loginUser = userService.login(userDto);
        String token = userService.createToken(loginUser);
        response.setHeader("Authorization", token);
        return new ResponseEntity<>(loginUser.toUserDto(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> show(@PathVariable Long id) {
        User user = userService.findById(id);
        return new ResponseEntity<>(user.toUserDto(), HttpStatus.OK);
    }


}
