package com.durin93.bookmanagement.web;

import com.durin93.bookmanagement.domain.User;
import com.durin93.bookmanagement.dto.UserDto;
import com.durin93.bookmanagement.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {

    private UserService userService;

    public ApiUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<UserDto> regist(@Valid @RequestBody UserDto userDto, BindingResult error) {
        if (error.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.regist(userDto));
    }


    @PostMapping("authentication")
    public ResponseEntity<UserDto> login(@RequestBody Map<String,String> data, HttpServletResponse response) {
        User loginUser = userService.login(data.get("userId"),data.get("password"));
        response.setHeader("Authorization", userService.createToken(loginUser));
        return ResponseEntity.ok(loginUser.toUserDto());
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> show(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id).toUserDto());
    }


}
