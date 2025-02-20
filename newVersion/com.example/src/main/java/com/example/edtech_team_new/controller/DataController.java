package com.example.edtech_team_new.controller;

import com.example.edtech_team_new.dao.DatesDAO;
import com.example.edtech_team_new.models.Dates;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class DataController {
    private final DatesDAO datesDAO;

    @PostMapping("/dates")
    public ResponseEntity<?> postDates(@RequestBody Dates dates) {
        try {
            datesDAO.update(dates);
            return ResponseEntity.ok(Collections.singletonMap("status", "success"));
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @GetMapping("/dates")
    public ResponseEntity<?> getLatestDates() {
        try {
            Dates dates = datesDAO.getLatest();
            if (dates == null) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(dates);
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @GetMapping("/dates/interval")
    public ResponseEntity<?> getDatesByInterval(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        try {
            List<Dates> datesList = datesDAO.getByInterval(from, to);
            if (datesList.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(datesList);
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @GetMapping("/hello")
    public ResponseEntity<?> hello() {
        return ResponseEntity.ok(Collections.singletonMap("message", "Hello from Spring Boot API!"));
    }
}
