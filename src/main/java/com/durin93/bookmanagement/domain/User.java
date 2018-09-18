package com.durin93.bookmanagement.domain;

import com.durin93.bookmanagement.dto.UserDto;
import com.durin93.bookmanagement.exception.UnAuthenticationException;
import com.durin93.bookmanagement.exception.UnAuthorizationException;
import com.durin93.bookmanagement.support.domain.AbstractEntity;
import com.durin93.bookmanagement.support.domain.ErrorManager;
import com.durin93.bookmanagement.support.domain.Level;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class User extends AbstractEntity {

    @Size(min = 3, max = 20)
    @Column(nullable = false, length = 20, unique = true)
    private String userId;

    @Size(min = 3, max = 20)
    @Column(nullable = false, length = 20)
    private String password;

    @Size(min = 3, max = 20)
    @Column(nullable = false, length = 20)
    private String name;

    @Enumerated(EnumType.STRING)
    private Level level = Level.USER;

    public User() {

    }

    public User(String userId, String password, String name, Level level) {
        this(userId, password, name);
        this.level = level;
    }

    public User(String userId, String password, String name) {
        this.userId = userId;
        this.password = password;
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public UserDto toUserDto() {
        UserDto userDto = new UserDto(getId(), userId, password, name, level);
        return userDto.addSelfDescription();
    }

    public Boolean matchPassword(String password) throws UnAuthenticationException {
        if (!this.password.equals(password)) {
            throw new UnAuthenticationException(ErrorManager.WRONG_PASSWORD);
        }
        return true;
    }

    public Boolean checkManager() {
        if (!level.isManager()) {
            throw new UnAuthorizationException(ErrorManager.NO_MANAGER);
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != null ? !userId.equals(user.userId) : user.userId != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        return name != null ? name.equals(user.name) : user.name == null;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", level=" + level +
                '}';
    }

}
