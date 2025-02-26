package com.example.cubesat.controller;

import com.example.cubesat.model.CubeSatRecord;
import com.example.cubesat.service.CubeSatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/cubesat")
@RequiredArgsConstructor
public class CubeSatController {
    private final CubeSatService cubeSatService;

    @GetMapping("/latest")
    public CubeSatRecord getLatestRecord() {
        return cubeSatService.getLatestRecord();
    }

    @GetMapping("/records")
    public List<CubeSatRecord> getRecordsBetweenDates(
            @RequestParam("start") String startDate,
            @RequestParam("end") String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        return cubeSatService.getRecordsBetweenDates(start, end);
    }
}
