package com.example.edtech_team_new;

import com.example.edtech_team_new.dao.DatesDAO;
import com.example.edtech_team_new.models.Dates;
import com.example.edtech_team_new.webSocket.SimpleWebSocketServer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import java.net.InetSocketAddress;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {

    @FXML private Label date;
    @FXML private Label distance;
    @FXML private Label hum;
    @FXML private Label internalTemp;
    @FXML private Label lat;
    @FXML private Label longg;
    @FXML private ImageView photo;
    @FXML private Label pressure;
    @FXML private Button refresh_btn;
    @FXML private Label temp;
    @FXML private Label time;
    @FXML private Label x;
    @FXML private Label y;
    @FXML private Label z;

    private SimpleWebSocketServer server;


    private boolean dateReceived = false;
    private boolean humReceived = false;
    private boolean latReceived = false;
    private boolean longgReceived = false;
    private boolean tempReceived = false;
    private boolean xReceived = false;
    private boolean yReceived = false;
    private boolean zReceived = false;
    private boolean internalTempReceived = false;
    private boolean pressureReceived = false;
    private boolean distanceReceived = false;

    DatesDAO datesDAO = new DatesDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startServer();

        refresh_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stop();
                try {
                    Thread.sleep(1000);
                    startServer();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void checkIfAllDataReceived() {
        if (dateReceived && humReceived && latReceived && longgReceived && tempReceived &&
                xReceived && yReceived && zReceived && internalTempReceived && pressureReceived &&
                distanceReceived) {
            sqlDates();
        }
    }

    public void sqlDates() {
        try {
            Dates datesNew = new Dates(
                    temp.getText(),
                    hum.getText(),
                    x.getText(),
                    y.getText(),
                    z.getText(),
                    lat.getText(),
                    longg.getText(),
                    internalTemp.getText(),
                    pressure.getText(),
                    distance.getText()
            );
            datesDAO.update(datesNew);
            System.out.println("Database updated with new data.");


            dateReceived = false;
            humReceived = false;
            latReceived = false;
            longgReceived = false;
            tempReceived = false;
            xReceived = false;
            yReceived = false;
            zReceived = false;
            internalTempReceived = false;
            pressureReceived = false;
            distanceReceived = false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void startServer() {
        String host = "0.0.0.0";
        int port = 8094;

        server = new SimpleWebSocketServer(new InetSocketAddress(host, port)) {
            @Override
            public void onMessage(WebSocket conn, String message) {
                System.out.println("Received message: " + message + " from " + conn.getRemoteSocketAddress());

                // example: "date:2025-02-20;hum:45;lat:41.0082;longg:28.9784;temp:26.5;x:1.23;y:4.56;z:7.89;internalTemp:24.0;pressure:1012;distance:15"
                String[] pairs = message.split(";");


                final String[] newDate = new String[1];
                final String[] newHum = new String[1];
                final String[] newLat = new String[1];
                final String[] newLongg = new String[1];
                final String[] newTemp = new String[1];
                final String[] newX = new String[1];
                final String[] newY = new String[1];
                final String[] newZ = new String[1];
                final String[] newInternalTemp = new String[1];
                final String[] newPressure = new String[1];
                final String[] newDistance = new String[1];

                for (String pair : pairs) {
                    String[] msg = pair.split(":", 2);
                    if (msg.length < 2) continue;
                    String key = msg[0].trim();
                    String value = msg[1].trim();
                    switch (key) {
                        case "date":
                            newDate[0] = value;
                            dateReceived = true;
                            break;
                        case "hum":
                            newHum[0] = value;
                            humReceived = true;
                            break;
                        case "lat":
                            newLat[0] = value;
                            latReceived = true;
                            break;
                        case "longg":
                            newLongg[0] = value;
                            longgReceived = true;
                            break;
                        case "temp":
                            newTemp[0] = value;
                            tempReceived = true;
                            break;
                        case "x":
                            newX[0] = value;
                            xReceived = true;
                            break;
                        case "y":
                            newY[0] = value;
                            yReceived = true;
                            break;
                        case "z":
                            newZ[0] = value;
                            zReceived = true;
                            break;
                        case "internalTemp":
                            newInternalTemp[0] = value;
                            internalTempReceived = true;
                            break;
                        case "pressure":
                            newPressure[0] = value;
                            pressureReceived = true;
                            break;
                        case "distance":
                            newDistance[0] = value;
                            distanceReceived = true;
                            break;
                        default:
                            System.out.println("Unknown key: " + key);
                            break;
                    }
                }
                // Tüm verileri tek seferde UI'ya aktaralım
                Platform.runLater(() -> {
                    if (newDate[0] != null) date.setText(newDate[0]);
                    if (newHum[0] != null) hum.setText(newHum[0]);
                    if (newLat[0] != null) lat.setText(newLat[0]);
                    if (newLongg[0] != null) longg.setText(newLongg[0]);
                    if (newTemp[0] != null) temp.setText(newTemp[0]);
                    if (newX[0] != null) x.setText(newX[0]);
                    if (newY[0] != null) y.setText(newY[0]);
                    if (newZ[0] != null) z.setText(newZ[0]);
                    if (newInternalTemp[0] != null) internalTemp.setText(newInternalTemp[0]);
                    if (newPressure[0] != null) pressure.setText(newPressure[0]);
                    if (newDistance[0] != null) distance.setText(newDistance[0]);
                });
                checkIfAllDataReceived();
            }

            @Override
            public void onOpen(WebSocket conn, ClientHandshake handshake) {
                System.out.println("New connection: " + conn.getRemoteSocketAddress());
                conn.send("Welcome to the server!");
            }

            @Override
            public void onClose(WebSocket conn, int code, String reason, boolean remote) {
                System.out.println("Closed connection: " + conn.getRemoteSocketAddress() +
                        " with exit code " + code + " additional info: " + reason);
            }
        };
        server.start();
    }

    public void stop() {
        if (server != null) {
            try {
                server.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
