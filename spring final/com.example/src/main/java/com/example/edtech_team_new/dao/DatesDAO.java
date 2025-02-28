package com.example.edtech_team_new.dao;

import com.example.edtech_team_new.db.DbHandler;
import com.example.edtech_team_new.models.Dates;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DatesDAO {
    private final DbHandler dbHandler;

    public void update(Dates dates) throws SQLException {
        Connection conn = dbHandler.getConnection();
        String sql = "INSERT INTO dates(record_date, temperature, humidity, x, y, z, lat, longg, internal_temp, pressure, received_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dates.getRecordDate());
            ps.setString(2, dates.getTemperature());
            ps.setString(3, dates.getHumidity());
            ps.setString(4, dates.getX());
            ps.setString(5, dates.getY());
            ps.setString(6, dates.getZ());
            ps.setString(7, dates.getLat());
            ps.setString(8, dates.getLongg());
            ps.setString(9, dates.getInternalTemp());
            ps.setString(10, dates.getPressure());
            ps.setTimestamp(11, Timestamp.valueOf(dates.getReceivedAt()));
            int rowsInserted = ps.executeUpdate();
            System.out.println("Rows inserted: " + rowsInserted);
        }
    }

    public Dates getLatest() throws SQLException {
        Connection conn = dbHandler.getConnection();
        String sql = "SELECT * FROM dates ORDER BY id DESC LIMIT 1;";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Dates dates = new Dates();
                dates.setRecordDate(rs.getString("record_date"));
                dates.setTemperature(rs.getString("temperature"));
                dates.setHumidity(rs.getString("humidity"));
                dates.setX(rs.getString("x"));
                dates.setY(rs.getString("y"));
                dates.setZ(rs.getString("z"));
                dates.setLat(rs.getString("lat"));
                dates.setLongg(rs.getString("longg"));
                dates.setInternalTemp(rs.getString("internal_temp"));
                dates.setPressure(rs.getString("pressure"));
                Timestamp ts = rs.getTimestamp("received_at");
                if(ts != null) {
                    dates.setReceivedAt(ts.toLocalDateTime());
                }
                return dates;
            }
        }
        return null;
    }

    public List<Dates> getByInterval(LocalDateTime from, LocalDateTime to) throws SQLException {
        List<Dates> list = new ArrayList<>();
        Connection conn = dbHandler.getConnection();
        String sql = "SELECT * FROM dates WHERE received_at BETWEEN ? AND ? ORDER BY received_at ASC;";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(from));
            ps.setTimestamp(2, Timestamp.valueOf(to));
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Dates dates = new Dates();
                dates.setRecordDate(rs.getString("record_date"));
                dates.setTemperature(rs.getString("temperature"));
                dates.setHumidity(rs.getString("humidity"));
                dates.setX(rs.getString("x"));
                dates.setY(rs.getString("y"));
                dates.setZ(rs.getString("z"));
                dates.setLat(rs.getString("lat"));
                dates.setLongg(rs.getString("longg"));
                dates.setInternalTemp(rs.getString("internal_temp"));
                dates.setPressure(rs.getString("pressure"));
                Timestamp ts = rs.getTimestamp("received_at");
                if(ts != null) {
                    dates.setReceivedAt(ts.toLocalDateTime());
                }
                list.add(dates);
            }
        }
        return list;
    }
}
