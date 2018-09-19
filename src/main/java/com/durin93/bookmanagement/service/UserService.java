package com.durin93.bookmanagement.service;

import com.durin93.bookmanagement.domain.User;
import com.durin93.bookmanagement.dto.UserDto;
import com.durin93.bookmanagement.exception.NotFoundException;
import com.durin93.bookmanagement.exception.UnAuthenticationException;
import com.durin93.bookmanagement.repository.UserRepository;
import com.durin93.bookmanagement.security.JwtManager;
import com.durin93.bookmanagement.support.exception.ErrorManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;

    private JwtManager jwtManager;

    @Autowired
    public UserService(UserRepository userRepository, JwtManager jwtManager) {
        this.userRepository = userRepository;
        this.jwtManager = jwtManager;
    }

    public UserDto regist(UserDto userDto) {
        isExist(userDto);
        return userRepository.save(userDto.toUser()).toUserDto();
    }

    public User login(UserDto userDto) throws UnAuthenticationException {
        User user = findByUserId(userDto.getUserId());
        user.matchPassword(userDto.getPassword());
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


    public User findById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorManager.NOT_EXIST_ID));
    }

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException(ErrorManager.NOT_EXIST_ID));
    }

}
