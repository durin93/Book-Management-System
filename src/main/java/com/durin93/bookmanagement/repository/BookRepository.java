package com.durin93.bookmanagement.repository;

import com.durin93.bookmanagement.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {


    Optional<Book> findFirstByAndIdAndIsDeletedIsFalse(Long id);
}
