package com.durin93.bookmanagement.dto;

import com.durin93.bookmanagement.domain.User;
import com.durin93.bookmanagement.support.domain.Level;
import com.durin93.bookmanagement.support.domain.Links;
import com.durin93.bookmanagement.support.dto.SelfDescription;
import com.durin93.bookmanagement.web.ApiUserController;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.Link;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Size;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class UserDto {

    @JsonIgnore
    private Long id;

    @Size(min = 3, max = 20)
    private String userId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Size(min = 3, max = 20)
    private String password;

    @Size(min = 3, max = 20)
    private String name;

    private Level level;

    @JsonUnwrapped
    private Links links = new Links();

    private String selfDescription = SelfDescription.USERS.getDocs();

    public UserDto() {
    }

    public UserDto(String userId, String password, String name) {
        this.userId = userId;
        this.password = password;
        this.name = name;
    }

    public UserDto(Long id, String userId,  String name, Level level) {
        this.userId = userId;
        this.name = name;
        this.id = id;
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

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public Link getLink(String rel) {
        return links.getLink(rel);
    }

    public String getSelfDescription() {
        return selfDescription;
    }

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(this.userId, passwordEncoder.encode(this.password), this.name);
    }

    public UserDto addLink() {
        links.add(linkTo(ApiUserController.class).slash(id).withSelfRel());
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDto userDto = (UserDto) o;

        if (userId != null ? !userId.equals(userDto.userId) : userDto.userId != null) return false;
        return name != null ? name.equals(userDto.name) : userDto.name == null;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", level=" + level +
                ", links=" + links +
                '}';
    }
}
