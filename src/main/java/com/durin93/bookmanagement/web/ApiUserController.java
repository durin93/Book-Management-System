package com.durin93.bookmanagement.web;

import com.durin93.bookmanagement.domain.User;
import com.durin93.bookmanagement.dto.BookDtos;
import com.durin93.bookmanagement.dto.UserDto;
import com.durin93.bookmanagement.security.JwtManager;
import com.durin93.bookmanagement.service.BookService;
import com.durin93.bookmanagement.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {

    private static final Logger log = LoggerFactory.getLogger(ApiUserController.class);

    private JwtManager jwtManager;

    private UserService userService;

    private BookService bookService;

    @Autowired
    public ApiUserController(JwtManager jwtManager, UserService userService, BookService bookService) {
        this.jwtManager = jwtManager;
        this.userService = userService;
        this.bookService = bookService;
    }

    @PostMapping("")
    public ResponseEntity<UserDto> regist(@RequestBody UserDto userDto) {
        UserDto registerUser = userService.regist(userDto);
        return new ResponseEntity<>(registerUser, HttpStatus.CREATED);
    }


    @PostMapping("authentication")
    public ResponseEntity<UserDto> login(@RequestBody UserDto userDto, HttpServletResponse response){
        User loginUser = userService.login(userDto);
        String token = jwtManager.create(loginUser);
        response.setHeader("Authorization", token);
        UserDto loginedUser = loginUser.toUserDto();
        return new ResponseEntity<>(loginedUser, HttpStatus.OK);
    }


    @GetMapping("{id}")
    public ResponseEntity<UserDto> show(@PathVariable Long id) {
        UserDto userDto = userService.findById(id);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("{id}/books")
    public ResponseEntity<BookDtos> showRentBooks(@PathVariable Long id) {
        return new ResponseEntity<>(bookService.findRentBooks(id), HttpStatus.OK);
    }

}
