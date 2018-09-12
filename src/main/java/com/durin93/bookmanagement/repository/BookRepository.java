package com.durin93.bookmanagement.repository;

import com.durin93.bookmanagement.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
}
