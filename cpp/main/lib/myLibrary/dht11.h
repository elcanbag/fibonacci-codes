#ifndef DHT11_H
#define DHT11_H

#include <DHT.h>

#define DHTPIN 23
#define DHTTYPE DHT11

DHT dht(DHTPIN, DHTTYPE);

inline void dht11_init() {
  dht.begin();
}

inline float dht11_readTemperature() {
  return dht.readTemperature();
}

inline float dht11_readHumidity() {
  return dht.readHumidity();
}

#endif
