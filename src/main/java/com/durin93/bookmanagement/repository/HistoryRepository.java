package com.durin93.bookmanagement.repository;

import com.durin93.bookmanagement.domain.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {

    List<History> findAllByUserId(Long id);

    List<History> findAllByBookId(Long id);

}
