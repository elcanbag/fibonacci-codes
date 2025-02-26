#include <Arduino.h>
#include <HardwareSerial.h>
#include <TinyGPSPlus.h>

#define RXD2 16
#define TXD2 17
#define GPS_BAUD 9600

TinyGPSPlus gps;
HardwareSerial gpsSerial(2);

void setup() {
    Serial.begin(115200);
    gpsSerial.begin(GPS_BAUD, SERIAL_8N1, RXD2, TXD2);
}

void loop() {
    while (gpsSerial.available()) {
        gps.encode(gpsSerial.read());  // Parse incoming GPS data
    }

    if (gps.location.isValid()) {
        Serial.print("Latitude: ");
        Serial.print(gps.location.lat(), 6);
        Serial.print(" | Longitude: ");
        Serial.println(gps.location.lng(), 6);
    } else {
        Serial.println("Waiting for GPS signal...");
    }

    if (gps.date.isValid() && gps.time.isValid()) {
        Serial.print("Date: ");
        Serial.print(gps.date.day());
        Serial.print("/");
        Serial.print(gps.date.month());
        Serial.print("/");
        Serial.print(gps.date.year());
        Serial.print(" | Time: ");
        Serial.print(gps.time.hour());
        Serial.print(":");
        Serial.print(gps.time.minute());
        Serial.print(":");
        Serial.println(gps.time.second());
    }

    if (gps.speed.isValid()) {
        Serial.print("Speed: ");
        Serial.print(gps.speed.kmph());
        Serial.println(" km/h");
    }

    if (gps.satellites.isValid()) {
        Serial.print("Satellites: ");
        Serial.println(gps.satellites.value());
    }

    delay(1000);
}
