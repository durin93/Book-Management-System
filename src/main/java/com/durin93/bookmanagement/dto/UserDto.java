package com.durin93.bookmanagement.dto;

import com.durin93.bookmanagement.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class UserDto {

    private Long id;

    @Size(min = 3, max = 20)
    private String userId;

    @Size(min = 3, max = 20)
    private String password;

    @Size(min = 3, max = 20)
    private String name;

    private List<Link> links = new ArrayList<>();

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


    public Long getId() {
        return id;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
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

    public User toUser() {
        return new User(this.userId, this.password, this.name);
    }


    public void add(Link link) {
        links.add(link);
    }

    public Link getLink(String rel) {
        for (Link link : links) {
            if (link.getRel().equals(rel)) {
                return link;
            }
        }
        return null;
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
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", links=" + links +
                '}';
    }
}
