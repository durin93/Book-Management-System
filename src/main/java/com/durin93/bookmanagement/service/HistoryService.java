package com.durin93.bookmanagement.service;

import com.durin93.bookmanagement.domain.Book;
import com.durin93.bookmanagement.domain.History;
import com.durin93.bookmanagement.domain.User;
import com.durin93.bookmanagement.dto.HistoryDtos;
import com.durin93.bookmanagement.exception.NotFoundException;
import com.durin93.bookmanagement.repository.HistoryRepository;
import com.durin93.bookmanagement.support.domain.HistoryType;
import com.durin93.bookmanagement.support.exception.ErrorManager;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class HistoryService {

    private HistoryRepository historyRepository;

    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public History regist(Book book, User loginUser, HistoryType historyType) {
        return historyRepository.save(new History(book, loginUser, historyType));
    }

    public History show(Long id) {
        return historyRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorManager.NOT_EXIST_HISTORY));
    }

    public HistoryDtos search(String searchType, Long id) {
        if (searchType.equals("user")) {
            return HistoryDtos.of(historyRepository.findAllByUserId(id));
        }
        if (searchType.equals("book")) {
            return HistoryDtos.of(historyRepository.findAllByBookId(id));
        }
        return HistoryDtos.of(historyRepository.findAll());
    }
}
