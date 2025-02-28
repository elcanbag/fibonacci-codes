package com.example.edtech_team_new.websocket;

import com.example.edtech_team_new.dao.DatesDAO;
import com.example.edtech_team_new.models.Dates;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomWebSocketHandler extends TextWebSocketHandler {

    private final DatesDAO datesDAO;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        String payload = message.getPayload();
        String[] pairs = payload.split(";");
        String recordDate = null, temperature = null, humidity = null, x = null, y = null, z = null, lat = null, longg = null, internalTemp = null, pressure = null;
        for (String pair : pairs) {
            String[] keyValue = pair.split(":", 2);
            if (keyValue.length < 2) continue;
            String key = keyValue[0].trim();
            String value = keyValue[1].trim();
            switch (key) {
                case "date":
                    recordDate = value;
                    break;
                case "temp":
                    temperature = value;
                    break;
                case "hum":
                    humidity = value;
                    break;
                case "x":
                    x = value;
                    break;
                case "y":
                    y = value;
                    break;
                case "z":
                    z = value;
                    break;
                case "lat":
                    lat = value;
                    break;
                case "longg":
                    longg = value;
                    break;
                case "internalTemp":
                    internalTemp = value;
                    break;
                case "pressure":
                    pressure = value;
                    break;
                default:
                    break;
            }
        }
        Dates dates = new Dates(recordDate, temperature, humidity, x, y, z, lat, longg, internalTemp, pressure);
        dates.setReceivedAt(LocalDateTime.now());
        try {
            datesDAO.update(dates);
            session.sendMessage(new TextMessage("Data received and stored successfully"));
        } catch (Exception e) {
            log.error("Error storing data", e);
            try {
                session.sendMessage(new TextMessage("Error storing data: " + e.getMessage()));
            } catch (Exception ex) {
                log.error("Error sending error message", ex);
            }
        }
    }
}
