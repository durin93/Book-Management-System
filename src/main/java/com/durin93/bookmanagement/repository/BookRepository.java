package com.durin93.bookmanagement.repository;

import com.durin93.bookmanagement.domain.Book;
import com.durin93.bookmanagement.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIdAndIsDeletedIsFalse(Long id);

    List<Book> findAllByRender(User user);

    List<Book> findAllByTitleLike(String title);

    List<Book> findAllByAuthorLike(String author);

    @Query("SELECT b FROM Book b where b.title like %:value% or b.author like %:value%")
    List<Book> findAllBooks(@Param("value") String value);

}
