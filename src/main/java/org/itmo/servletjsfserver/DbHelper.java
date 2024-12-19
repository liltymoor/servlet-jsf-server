package org.itmo.servletjsfserver;

import jakarta.servlet.http.HttpServletResponse;
import org.itmo.servletjsfserver.model.Attempt;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbHelper {
    static Connection conn;

    static private Connection getConn() {
        if (conn == null) {
            try {
                conn = DatabaseUtil.getConnection();
            } catch (SQLException e) {
                System.out.println("Failed to open connection to db");
            }
        }
        return conn;
    }

    static public boolean addAttempt(Attempt attempt) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "INSERT INTO attempts (x, y, r, result, created_at, execution_time) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setDouble(1, attempt.getX());
                statement.setDouble(2, attempt.getY());
                statement.setDouble(3, attempt.getR());
                statement.setBoolean(4, attempt.isResult());
                statement.setTimestamp(5, new java.sql.Timestamp(attempt.getCreatedAt().getTime()));
                statement.setLong(6, attempt.getExecutionTime());
                statement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    static public List<Attempt> getAllAttempts() {
        List<Attempt> attempts = new ArrayList<>();
        String sql = "SELECT * FROM attempts";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Attempt attempt = new Attempt();
                attempt.setId(resultSet.getInt("id"));
                attempt.setX(resultSet.getDouble("x"));
                attempt.setY(resultSet.getDouble("y"));
                attempt.setR(resultSet.getDouble("r"));
                attempt.setResult(resultSet.getBoolean("result"));
                attempt.setCreatedAt(new java.util.Date(resultSet.getTimestamp("created_at").getTime()));
                attempt.setExecutionTime(resultSet.getLong("execution_time"));
                attempts.add(attempt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attempts;
    }

}
