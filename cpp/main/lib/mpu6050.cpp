#include <Wire.h>
#include <Adafruit_MPU6050.h>
#include <Adafruit_Sensor.h>

Adafruit_MPU6050 mpu;

void setup() {
    Serial.begin(115200);
    Wire.begin();

    if (!mpu.begin()) {
        Serial.println("MPU6050 not found!");
        while (1);
    }
    Serial.println("MPU6050 successfully connected");
}

void loop() {
    sensors_event_t accel, gyro, temp;
    mpu.getEvent(&accel, &gyro, &temp);

    Serial.print("Accel X: "); Serial.print(accel.acceleration.x);
    Serial.print(" | Y: "); Serial.print(accel.acceleration.y);
    Serial.print(" | Z: "); Serial.println(accel.acceleration.z);
  
    Serial.print("Gyro X: "); Serial.print(gyro.gyro.x);
    Serial.print(" | Y: "); Serial.print(gyro.gyro.y);
    Serial.print(" | Z: "); Serial.println(gyro.gyro.z);
    
    Serial.print("MPU Temp: "); Serial.print(temp.temperature);
    Serial.println(" °C");

    Serial.println("-------------------------");

    delay(500);
}
