package com.durin93.bookmanagement.service;

import com.durin93.bookmanagement.domain.User;
import com.durin93.bookmanagement.dto.UserDto;
import com.durin93.bookmanagement.exception.NotFoundException;
import com.durin93.bookmanagement.exception.UnAuthenticationException;
import com.durin93.bookmanagement.repository.UserRepository;
import com.durin93.bookmanagement.security.JwtManager;
import com.durin93.bookmanagement.support.exception.ErrorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    private JwtManager jwtManager;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, JwtManager jwtManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtManager = jwtManager;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto regist(UserDto userDto) {
        isExist(userDto);
        return userRepository.save(userDto.toUser(passwordEncoder)).toUserDto();
    }

    public User login(String userId, String password) throws UnAuthenticationException {
        User user = findByUserId(userId);
        user.matchPassword(password, passwordEncoder);
        return user;
    }

    public User loginUser() {
        return userRepository.findByUserId(jwtManager.decode()).orElseThrow(() -> new NotFoundException(ErrorManager.NOT_EXIST_ID));
    }

    public void isExist(UserDto userDto) throws UnAuthenticationException {
        if (userRepository.findByUserId(userDto.getUserId()).isPresent()) {
            throw new UnAuthenticationException(ErrorManager.EXIST_ID);
        }
    }

    public String createToken(User loginUser) {
        return jwtManager.create(loginUser);
    }

    public User findById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorManager.NOT_EXIST_ID));
    }

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException(ErrorManager.NOT_EXIST_ID));
    }


}
