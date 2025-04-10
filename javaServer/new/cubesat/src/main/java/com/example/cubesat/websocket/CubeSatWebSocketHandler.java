package com.example.cubesat.websocket;

import com.example.cubesat.model.CubeSatRecord;
import com.example.cubesat.service.CubeSatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class CubeSatWebSocketHandler extends TextWebSocketHandler {
    private final CubeSatService cubeSatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String payload = message.getPayload();
        CubeSatRecord record = parseCubeSatData(payload);
        if (record != null) {
            cubeSatService.saveRecord(record);
            session.sendMessage(new TextMessage("Data received and saved."));
        } else {
            session.sendMessage(new TextMessage("Invalid data format."));
        }
    }

    private CubeSatRecord parseCubeSatData(String data) {
        try {
            Map<String, String> values = new HashMap<>();
            String[] pairs = data.split(";");

            for (String pair : pairs) {
                String[] keyValue = pair.split(":");
                if (keyValue.length == 2) {
                    values.put(keyValue[0], keyValue[1]);
                }
            }

            CubeSatRecord record = new CubeSatRecord();
            record.setRecordDate(values.get("date"));
            record.setTemperature(values.get("temp"));
            record.setHumidity(values.get("hum"));
            record.setLat(values.get("lat"));
            record.setLongg(values.get("longg"));
            record.setX(values.get("x"));
            record.setY(values.get("y"));
            record.setZ(values.get("z"));
            record.setInternalTemp(values.get("internalTemp"));
            record.setPressure(values.get("pressure"));
            record.setReceivedAt(LocalDateTime.now());

            return record;
        } catch (Exception e) {
            return null;
        }
    }
}
