package com.durin93.bookmanagement.web;

import com.durin93.bookmanagement.domain.History;
import com.durin93.bookmanagement.dto.HistoryDto;
import com.durin93.bookmanagement.dto.HistoryDtos;
import com.durin93.bookmanagement.service.HistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/histories")
public class ApiHistoryController {

    private static final Logger log = LoggerFactory.getLogger(ApiHistoryController.class);

    private HistoryService historyService;

    public ApiHistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }


    @GetMapping("{id}")
    public ResponseEntity<HistoryDto> show(@PathVariable Long id) {
        History history = historyService.show(id);
        return new ResponseEntity<>(history.toHistoryDto(), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<HistoryDtos> search(String searchType, Long id) {
        HistoryDtos histories = historyService.search(searchType, id);
        return new ResponseEntity<>(histories, HttpStatus.OK);
    }


}
