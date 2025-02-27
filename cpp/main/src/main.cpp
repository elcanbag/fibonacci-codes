#include <Arduino.h>
#include <Wire.h>
#include <Adafruit_BMP085.h>
#include <DHT.h>
#include <TinyGPSPlus.h>
#include <HardwareSerial.h>
#include <Adafruit_MPU6050.h>
#include <Adafruit_Sensor.h>

// BMP180 Sensor
Adafruit_BMP085 bmp;

// DHT11 Sensor
#define DHTPIN 23
#define DHTTYPE DHT11
DHT dht(DHTPIN, DHTTYPE);

// GPS Sensor
#define RXD2 16
#define TXD2 17
#define GPS_BAUD 9600
TinyGPSPlus gps;
HardwareSerial gpsSerial(2);

// MPU6050 Sensor
Adafruit_MPU6050 mpu;

void setup() {
  Serial.begin(115200);
  Wire.begin();

  // Initialize BMP180
  if (!bmp.begin()) {
    Serial.println("BMP180 not found!");
    while (1);
  }
  Serial.println("BMP180 successfully connected");

  // Initialize DHT11
  dht.begin();

  // Initialize GPS
  gpsSerial.begin(GPS_BAUD, SERIAL_8N1, RXD2, TXD2);

  // Initialize MPU6050
  if (!mpu.begin()) {
    Serial.println("MPU6050 not found!");
    while (1);
  }
  Serial.println("MPU6050 successfully connected");
}

void loop() {
  // BMP180 sensor readings
  Serial.print("BMP180 Temp: ");
  Serial.print(bmp.readTemperature());
  Serial.println(" °C");
  
  Serial.print("Pressure: ");
  Serial.print(bmp.readPressure());
  Serial.println(" Pa");
  
  Serial.print("Altitude: ");
  Serial.print(bmp.readAltitude());
  Serial.println(" m");
  
  Serial.println("-------------------------");
  
  // DHT11 sensor readings
  float dhtTemp = dht.readTemperature();
  float humidity = dht.readHumidity();
  
  if (isnan(dhtTemp) || isnan(humidity)) {
    Serial.println("DHT11 reading error!");
  } else {
    Serial.print("DHT11 Temp: ");
    Serial.print(dhtTemp);
    Serial.print(" °C | Humidity: ");
    Serial.print(humidity);
    Serial.println(" %");
  }
  
  // GPS sensor readings
  while (gpsSerial.available()) {
    gps.encode(gpsSerial.read());
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
  
  // MPU6050 sensor readings
  sensors_event_t accel, gyro, mpuTemp;
  mpu.getEvent(&accel, &gyro, &mpuTemp);
  
  Serial.print("Accel X: ");
  Serial.print(accel.acceleration.x);
  Serial.print(" | Y: ");
  Serial.print(accel.acceleration.y);
  Serial.print(" | Z: ");
  Serial.println(accel.acceleration.z);
  
  Serial.print("Gyro X: ");
  Serial.print(gyro.gyro.x);
  Serial.print(" | Y: ");
  Serial.print(gyro.gyro.y);
  Serial.print(" | Z: ");
  Serial.println(gyro.gyro.z);
  
  Serial.print("MPU Temp: ");
  Serial.print(mpuTemp.temperature);
  Serial.println(" °C");
  
  Serial.println("====================================");
  
  delay(2000);
}
