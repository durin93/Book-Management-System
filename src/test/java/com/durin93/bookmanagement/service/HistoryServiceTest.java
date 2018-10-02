package com.durin93.bookmanagement.service;

import com.durin93.bookmanagement.domain.Book;
import com.durin93.bookmanagement.domain.History;
import com.durin93.bookmanagement.domain.User;
import com.durin93.bookmanagement.dto.BookDto;
import com.durin93.bookmanagement.dto.BookDtos;
import com.durin93.bookmanagement.dto.SearchDto;
import com.durin93.bookmanagement.exception.RentalException;
import com.durin93.bookmanagement.exception.UnAuthorizationException;
import com.durin93.bookmanagement.repository.BookRepository;
import com.durin93.bookmanagement.repository.HistoryRepository;
import com.durin93.bookmanagement.support.domain.HistoryType;
import com.durin93.bookmanagement.support.domain.Level;
import com.durin93.bookmanagement.support.exception.ErrorManager;
import com.durin93.bookmanagement.support.test.MockitoTest;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class HistoryServiceTest extends MockitoTest {


    @Mock
    HistoryRepository historyRepository;

    @InjectMocks
    private HistoryService historyService;


    private HistoryType historyType;
    private History history;

    @Before
    public void setUp() {
        historyType = HistoryType.UPDATE;
        history = new History(1L, 1L, historyType);
    }

    @Test
    public void show() {
        when(historyRepository.findById(anyLong())).thenReturn(Optional.of(history));
        historyService.show(1L);
        verify(historyRepository, times((1))).findById(any());
    }

    @Test
    public void save() {
        when(historyRepository.save(any())).thenReturn(history);
        assertThat(historyService.save(1L,1L,historyType),is(history));
        verify(historyRepository, times((1))).save(any());
    }

    @Test
    public void search_book() {
        when(historyRepository.findAllByBookId(anyLong())).thenReturn(anyList());
        historyService.search("book", 1L);
        verify(historyRepository, times((1))).findAllByBookId(anyLong());
    }

    @Test
    public void search_user() {
        when(historyRepository.findAllByUserId(anyLong())).thenReturn(anyList());
        historyService.search("user", 1L);
        verify(historyRepository, times((1))).findAllByUserId(anyLong());
    }


    public BookDto createBook() {
        return new BookDto("스페인 너는 자유다", "손미나", LocalDate.of(2006, 7, 28), 340, 582);
    }


}
