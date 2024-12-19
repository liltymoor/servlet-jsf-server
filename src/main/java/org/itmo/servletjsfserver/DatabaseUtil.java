package org.itmo.servletjsfserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final String URL = "jdbc:postgresql://pg:5432/studs";
    private static final String USER = "s413105";
    private static final String PASSWORD = "r5BklO7TucYga3Oe";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
