package com.durin93.bookmanagement.dto;

import com.durin93.bookmanagement.domain.User;
import com.durin93.bookmanagement.support.domain.Level;
import com.durin93.bookmanagement.support.domain.SelfDescription;
import com.durin93.bookmanagement.web.ApiUserController;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.Link;

import javax.validation.constraints.Size;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


public class UserDto {

    @JsonIgnore
    private Long id;

    @Size(min = 3, max = 20)
    private String userId;

    @Size(min = 3, max = 20)
    private String password;

    @Size(min = 3, max = 20)
    private String name;

    private Level level;

    @JsonUnwrapped
    private SelfDescription selfDescription = new SelfDescription();

    public UserDto() {
    }

    public UserDto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public UserDto(String userId, String password, String name) {
        this(userId, password);
        this.name = name;
    }

    public UserDto(Long id, String userId, String password, String name) {
        this(userId, password, name);
        this.id = id;
    }

    public UserDto(Long id, String userId, String password, String name, Level level) {
        this(id, userId, password, name);
        this.level = level;
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SelfDescription getSelfDescription() {
        return selfDescription;
    }

    public void setSelfDescription(SelfDescription selfDescription) {
        this.selfDescription = selfDescription;
    }

    public Link getLink(String rel) {
        return selfDescription.getLink(rel);
    }

    public User toUser() {
        return new User(this.userId, this.password, this.name);
    }

    public UserDto addSelfDescription() {
        selfDescription.add(linkTo(ApiUserController.class).slash(id).withSelfRel());
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDto userDto = (UserDto) o;

        if (userId != null ? !userId.equals(userDto.userId) : userDto.userId != null) return false;
        if (password != null ? !password.equals(userDto.password) : userDto.password != null) return false;
        return name != null ? name.equals(userDto.name) : userDto.name == null;
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
        return "UserDto{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", level=" + level +
                ", links=" + selfDescription +
                '}';
    }
}
