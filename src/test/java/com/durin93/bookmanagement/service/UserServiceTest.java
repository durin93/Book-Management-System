package com.durin93.bookmanagement.service;

import com.durin93.bookmanagement.domain.User;
import com.durin93.bookmanagement.domain.UserTest;
import com.durin93.bookmanagement.dto.UserDto;
import com.durin93.bookmanagement.exception.UnAuthenticationException;
import com.durin93.bookmanagement.repository.UserRepository;
import com.durin93.bookmanagement.support.exception.ErrorManager;
import com.durin93.bookmanagement.support.domain.Level;
import com.durin93.bookmanagement.support.test.MockitoTest;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserServiceTest extends MockitoTest {


    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;


    private User user;

    @Before
    public void setUp() {
        user = new User("user", UserTest.PASSWORD1234, "사용자테스터", Level.USER);
    }

    @Test
    public void regist() {
        when(userRepository.save(any())).thenReturn(user);
        UserDto userDto = userService.regist(createUser());
        assertThat(userDto, is(user.toUserDto()));
        verify(userRepository, times((1))).save(any());
    }

    @Test
    public void login() {
        when(userRepository.findByUserId(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(),anyString())).thenReturn(true);
        assertThat(user, is(userService.login(createUser().getUserId(), createUser().getPassword())));
        verify(userRepository, times((1))).findByUserId(any());
    }

    @Test
    public void isExist() {
        when(userRepository.findByUserId(anyString())).thenReturn(Optional.of(user));

        thrown.expect(UnAuthenticationException.class);
        thrown.expectMessage(ErrorManager.EXIST_ID);

        userService.isExist(createUser());
        verify(userRepository, times((1))).findByUserId(any());
    }

    public UserDto createUser() {
        return new UserDto("user", "1234", "사용자테스터");
    }


}
