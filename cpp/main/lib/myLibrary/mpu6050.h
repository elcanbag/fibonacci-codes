#ifndef MPU6050_H
#define MPU6050_H

#include <Adafruit_MPU6050.h>
#include <Adafruit_Sensor.h>

Adafruit_MPU6050 mpu;

inline void mpu6050_init() {
  if (!mpu.begin()) {
    Serial.println("MPU6050 not found!");
    while (1);
  }
  Serial.println("MPU6050 successfully connected");
}

inline void mpu6050_read(sensors_event_t* accel, sensors_event_t* gyro, sensors_event_t* tempEvent) {
  mpu.getEvent(accel, gyro, tempEvent);
}

#endif
