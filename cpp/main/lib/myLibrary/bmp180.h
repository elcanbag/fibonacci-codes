#ifndef BMP180_H
#define BMP180_H

#include <Adafruit_BMP085.h>

Adafruit_BMP085 bmp;

inline void bmp180_init() {
  if (!bmp.begin()) {
    Serial.println("BMP180 not found!");
    while (1);
  }
  Serial.println("BMP180 successfully connected");
}

inline float bmp180_readTemperature() {
  return bmp.readTemperature();
}

inline float bmp180_readPressure() {
  return bmp.readPressure();
}

inline float bmp180_readAltitude() {
  return bmp.readAltitude();
}

#endif
