#include <Wire.h>
#include <Adafruit_BMP085.h>

Adafruit_BMP085 bmp;

void setup() {
    Serial.begin(115200);
    Wire.begin();

    if (!bmp.begin()) {
        Serial.println("BMP180 not found!");
        while (1);
    }
    Serial.println("BMP180 successfully connected");
}

void loop() {
    Serial.print("BMP180 Temp: "); Serial.print(bmp.readTemperature());
    Serial.println(" °C");

    Serial.print("Pressure: "); Serial.print(bmp.readPressure());
    Serial.println(" Pa");

    Serial.print("Altitude: "); Serial.print(bmp.readAltitude());
    Serial.println(" m");

    Serial.println("-------------------------");

    delay(500);
}
