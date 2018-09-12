package com.durin93.bookmanagement.service;

import com.durin93.bookmanagement.domain.User;
import com.durin93.bookmanagement.dto.UserDto;
import com.durin93.bookmanagement.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import javax.naming.CannotProceedException;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto regist(UserDto userDto) throws CannotProceedException {
        logger.debug(userDto.toString());
        isExist(userDto);
        return userRepository.save(userDto.toUser()).toUserDto();
    }

    public UserDto login(String userId, String password) throws AuthenticationException {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new NullPointerException("존재하지 않는 아이디입니다."));
        user.matchPassword(password);
        return user.toUserDto();
    }

    public void isExist(UserDto userDto) throws CannotProceedException {
        if(userRepository.findByUserId(userDto.getUserId()).isPresent()){
            throw new CannotProceedException("이미 존재하는 아이디 입니다.");
        }
    }


}
