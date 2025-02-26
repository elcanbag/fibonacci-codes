package com.example.cubesat.controller;

import com.example.cubesat.model.CubeSatRecord;
import com.example.cubesat.service.CubeSatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/cubesat")
@RequiredArgsConstructor
@Tag(name = "CubeSat API", description = "APIs for retrieving CubeSat data")
public class CubeSatController {
    private final CubeSatService cubeSatService;

    @Operation(summary = "Retrieve the latest CubeSat record")
    @GetMapping("/latest")
    public CubeSatRecord getLatestRecord() {
        return cubeSatService.getLatestRecord();
    }

    @Operation(summary = "Retrieve CubeSat records within a specified time range")
    @GetMapping("/records")
    public List<CubeSatRecord> getRecordsBetweenDates(
            @RequestParam("start") String startDate,
            @RequestParam("end") String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        return cubeSatService.getRecordsBetweenDates(start, end);
    }
}
