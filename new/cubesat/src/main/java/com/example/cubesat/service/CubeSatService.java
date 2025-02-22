package com.example.cubesat.service;

import com.example.cubesat.model.CubeSatRecord;
import com.example.cubesat.repository.CubeSatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CubeSatService {
    private final CubeSatRepository repository;

    public void saveRecord(CubeSatRecord record) {
        repository.save(record);
    }

    public CubeSatRecord getLatestRecord() {
        return repository.findTopByOrderByReceivedAtDesc();
    }

    public List<CubeSatRecord> getRecordsBetweenDates(LocalDateTime start, LocalDateTime end) {
        return repository.findByReceivedAtBetween(start, end);
    }
}
