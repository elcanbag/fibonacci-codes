#ifndef GPS_H
#define GPS_H

#include <TinyGPSPlus.h>
#include <HardwareSerial.h>

#define RXD2 16
#define TXD2 17
#define GPS_BAUD 9600

TinyGPSPlus gps;
HardwareSerial gpsSerial(2);

inline void gps_init() {
  gpsSerial.begin(GPS_BAUD, SERIAL_8N1, RXD2, TXD2);
}

inline void gps_update() {
  while (gpsSerial.available() > 0) {
    gps.encode(gpsSerial.read());
  }
}

inline String gps_getDate() {
  String dateStr;
  if (gps.date.isValid() && gps.time.isValid()) {
    char dateBuffer[11];
    sprintf(dateBuffer, "%04d-%02d-%02d", gps.date.year(), gps.date.month(), gps.date.day());
    dateStr = String(dateBuffer);
  } else {
    dateStr = "2025-03-01";
  }
  return dateStr;
}

inline String gps_getLat() {
  unsigned long startTime = millis();
  while (!gps.location.isValid() && millis() - startTime < 2000) {
    gps_update();
  }
  return gps.location.isValid() ? String(gps.location.lat(), 6) : "0.000000";
}

inline String gps_getLong() {
  unsigned long startTime = millis();
  while (!gps.location.isValid() && millis() - startTime < 2000) {
    gps_update();
  }
  return gps.location.isValid() ? String(gps.location.lng(), 6) : "0.000000";
}

inline String gps_getSatellites() {
  gps_update();
  return gps.satellites.isValid() ? String(gps.satellites.value()) : "0";
}

#endif
