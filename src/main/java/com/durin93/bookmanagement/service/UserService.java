package com.durin93.bookmanagement.service;

import com.durin93.bookmanagement.domain.User;
import com.durin93.bookmanagement.dto.UserDto;
import com.durin93.bookmanagement.exception.NotFoundException;
import com.durin93.bookmanagement.exception.UnAuthenticationException;
import com.durin93.bookmanagement.repository.UserRepository;
import com.durin93.bookmanagement.support.domain.ErrorManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

    public void isExist(UserDto userDto) throws UnAuthenticationException {
        if (userRepository.findByUserId(userDto.getUserId()).isPresent()) {
            throw new UnAuthenticationException(ErrorManager.EXIST_ID);
        }
    }


    public UserDto findById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorManager.NOT_EXIST_ID)).toUserDto();
    }

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException(ErrorManager.NOT_EXIST_ID));
    }

}
