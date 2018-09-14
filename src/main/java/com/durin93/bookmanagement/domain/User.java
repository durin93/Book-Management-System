package com.durin93.bookmanagement.domain;

import com.durin93.bookmanagement.dto.UserDto;
import com.durin93.bookmanagement.exception.UnAuthenticationException;
import com.durin93.bookmanagement.exception.UnAuthorizationException;
import com.durin93.bookmanagement.support.domain.AbstractEntity;
import com.durin93.bookmanagement.support.domain.Level;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "render")
    @JsonIgnore
    @Where(clause = "is_deleted = false")
    private List<Book> rentBooks = new ArrayList<>();

    public User() {

    }

    public User(String userId, String password, String name, Level level) {
        this(0L, userId, password, name);
        this.level = level;
    }

    public User(Long id, String userId, String password, String name) {
        super(id);
        this.userId = userId;
        this.password = password;
        this.name = name;
    }

    public List<Book> getRentBooks() {
        return rentBooks;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public UserDto toUserDto() {
        return new UserDto(getId(), userId, password, name, level);
    }

    public void matchPassword(String password) throws UnAuthenticationException {
        if (!this.password.equals(password)) {
            throw new UnAuthenticationException("비밀번호가 틀렸습니다.");
        }
    }

    public void checkManager() {
        if (!level.isManager()) {
            throw new UnAuthorizationException("관리자만 접근 가능합니다.");
        }
    }


    public List<Book> rentBook(Book book) {
        rentBooks.add(book);
        return rentBooks;
    }

    public List<Book> giveBackBook(Book book) {
        rentBooks.remove(book);
        return rentBooks;
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
