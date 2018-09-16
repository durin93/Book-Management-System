package com.durin93.bookmanagement.service;

import com.durin93.bookmanagement.domain.User;
import com.durin93.bookmanagement.dto.UserDto;
import com.durin93.bookmanagement.exception.UnAuthenticationException;
import com.durin93.bookmanagement.repository.UserRepository;
import com.durin93.bookmanagement.support.domain.Level;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    UserRepository userRepository;

    @InjectMocks
    private UserService userService;


    private User user;

    @Before
    public void setUp() {
        user = new User("user", "password", "사용자테스터", Level.USER);
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
        assertThat(user, is(userService.login(createUser())));
        verify(userRepository, times((1))).findByUserId(any());
    }

    @Test
    public void isExist() {
        when(userRepository.findByUserId(anyString())).thenReturn(Optional.of(user));

        thrown.expect(UnAuthenticationException.class);
        thrown.expectMessage("이미 존재하는 아이디 입니다.");

        userService.isExist(createUser());
        verify(userRepository, times((1))).findByUserId(any());
    }

    public UserDto createUser() {
        return new UserDto("user", "password", "사용자테스터");
    }


}
