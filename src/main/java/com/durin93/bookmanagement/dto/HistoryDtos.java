package com.durin93.bookmanagement.dto;

import com.durin93.bookmanagement.domain.History;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HistoryDtos {

    private List<HistoryDto> histories;

    public HistoryDtos() {

    }

    public HistoryDtos(List<HistoryDto> histories) {
        this.histories = histories;
    }


    public static HistoryDtos of(List<History> histories) {
        List<HistoryDto> historyDtos = histories.stream().map(b -> b.toHistoryDto()).collect(Collectors.toList());
        return new HistoryDtos(historyDtos);
    }

    @JsonIgnore
    public int getSize() {
        return histories.size();
    }

    public List<HistoryDto> getHistories() {
        return histories;
    }

    public void setHistories(List<HistoryDto> histories) {
        this.histories = histories;
    }

}
