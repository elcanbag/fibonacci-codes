DROP TABLE IF EXISTS dates;
CREATE TABLE dates (
  id SERIAL PRIMARY KEY,
  record_date VARCHAR(50),
  temperature VARCHAR(50),
  humidity VARCHAR(50),
  x VARCHAR(50),
  y VARCHAR(50),
  z VARCHAR(50),
  lat VARCHAR(50),
  longg VARCHAR(50),
  internal_temp VARCHAR(50),
  pressure VARCHAR(50),
  received_at TIMESTAMP
);
