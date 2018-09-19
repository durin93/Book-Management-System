package com.durin93.bookmanagement.dto;

import com.durin93.bookmanagement.domain.History;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class HistoryDtos {

    private List<HistoryDto> histories;

    public HistoryDtos() {

    }

    public HistoryDtos(List<HistoryDto> histories) {
        this.histories = histories;
    }


    public static HistoryDtos of(List<History> histories) {
        List<HistoryDto> historyDtos = new ArrayList<>();
        for (History history : histories) {
            historyDtos.add(history.toHistoryDto());
        }
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

    public HistoryDto get(int index) {
        return histories.get(index);
    }
}
