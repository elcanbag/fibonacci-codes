package com.example.edtech_team_new.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dates {
    private int id;
    private String recordDate;
    private String temperature;
    private String humidity;
    private String x;
    private String y;
    private String z;
    private String lat;
    private String longg;
    private String internalTemp;
    private String pressure;
    private LocalDateTime receivedAt;

    public Dates(String recordDate, String temperature, String humidity, String x, String y, String z, String lat, String longg, String internalTemp, String pressure) {
        this.recordDate = recordDate;
        this.temperature = temperature;
        this.humidity = humidity;
        this.x = x;
        this.y = y;
        this.z = z;
        this.lat = lat;
        this.longg = longg;
        this.internalTemp = internalTemp;
        this.pressure = pressure;
    }
}
