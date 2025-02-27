#ifndef WEBSOCKET_H
#define WEBSOCKET_H

#include <Arduino.h>
#include <WiFi.h>
#include <ArduinoWebsockets.h>
#include <Wire.h>

// Include sensor libraries
#include "bmp180.h"
#include "dht11.h"
#include "gps.h"
#include "mpu6050.h"

using namespace websockets;

// WiFi & WebSocket settings – adjust these as needed:
const char* ssid = "Qwerty_asggsvsvsgz";
const char* password = "ThisLANisMyLAN";
const char* websocket_url = "ws://192.168.0.136:8080/ws";

WebsocketsClient client;

inline void ws_setup() {
  Serial.begin(115200);
  Wire.begin();

  // Initialize sensors
  bmp180_init();
  dht11_init();
  gps_init();
  mpu6050_init();

  // Connect to WiFi
  Serial.print("Connecting to WiFi");
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(300);
  }
  Serial.println();
  Serial.print("Connected to WiFi. IP Address: ");
  Serial.println(WiFi.localIP());

  // Connect to WebSocket server using the full URL
  Serial.print("Connecting to WebSocket: ");
  Serial.println(websocket_url);
  client.connect(websocket_url);

  // Optional: set a callback for incoming messages
  client.onMessage([](WebsocketsMessage message) {
    Serial.print("Received message: ");
    Serial.println(message.data());
  });
}

inline void ws_loop() {
  // Maintain WebSocket connection
  client.poll();

  // ----- Sensor Readings -----
  float bmpTemp = bmp180_readTemperature();
  float pressure = bmp180_readPressure();
  float altitude = bmp180_readAltitude();

  float dhtTemp = dht11_readTemperature();
  float humidity = dht11_readHumidity();

  gps_update();

  String dateStr = gps_getDate();
  String latStr  = gps_getLat();
  String longStr = gps_getLong();

  sensors_event_t accel, gyro, mpuTempEvent;
  mpu6050_read(&accel, &gyro, &mpuTempEvent);

  // ----- Build Data String -----
  // Format:
  // date:YYYY-MM-DD;hum:XX;lat:...;longg:...;temp:...;x:...;y:...;z:...;
  // internalTemp:...;pressure:...;alt:...;sat:...
  String data = "date:" + dateStr +
                ";hum:" + String(humidity, 0) +
                ";lat:" + latStr +
                ";longg:" + longStr +
                ";temp:" + String(dhtTemp, 1) +
                ";x:" + String(accel.acceleration.x, 2) +
                ";y:" + String(accel.acceleration.y, 2) +
                ";z:" + String(accel.acceleration.z, 2) +
                ";internalTemp:" + String(mpuTempEvent.temperature, 1) +
                ";pressure:" + String(pressure, 0) +
                ";alt:" + String(altitude, 1) +
                ";sat:" + String(gps_getSatellites());

  // ----- Send Data via WebSocket -----
  client.send(data);
  Serial.print("Sent data: ");
  Serial.println(data);

  delay(2000);  // Wait for 2 seconds between loops
}

#endif
