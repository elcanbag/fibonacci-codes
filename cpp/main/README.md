# Hardware Wiring

This project uses an **ESP32 DevKit** connected to multiple sensor modules.  
All sensors are standard breakout boards with onboard pull-ups/regulators, so **no external resistors** are required.

## Components
- **ESP32 DevKit** (3.3 V logic)
- **BMP180** Barometric Pressure & Temperature Sensor (I²C)
- **MPU6050** Accelerometer & Gyroscope (I²C)
- **DHT11** Temperature & Humidity Sensor (single-wire)
- **GPS Module** (e.g., NEO-6M, UART)

## Power & Ground Connections
| ESP32 Pin | Connected To | Notes |
|-----------|-------------|-------|
| 3V3       | BMP180 VCC, MPU6050 VCC, DHT11 VCC | GPS VCC to 3V3 if supported, else to 5V (VIN) |
| GND       | All module GND pins | Common ground |

## Signal Connections
### I²C Bus (BMP180 + MPU6050)
| ESP32 Pin | Connected To |
|-----------|--------------|
| GPIO21 (SDA) | BMP180 SDA, MPU6050 SDA |
| GPIO22 (SCL) | BMP180 SCL, MPU6050 SCL |

### DHT11
| ESP32 Pin | Connected To |
|-----------|--------------|
| GPIO23    | DHT11 DATA   |

### GPS (UART2)
| ESP32 Pin  | Connected To |
|------------|--------------|
| GPIO16 (RX2) | GPS TX     |
| GPIO17 (TX2) | GPS RX     |

## Notes
- Use **3V3 logic** for all signal lines.
- If GPS module requires 5 V for power, only connect **VCC** to 5 V; UART lines must remain 3.3 V.
- Keep wiring short to reduce noise.
