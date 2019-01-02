package io.github.TheCahyag.SpotifyUtil.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * File: SQLManager.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class SQLManager {

    private static Connection source = null;

    public static Connection getConnection() throws SQLException {
        if (source == null){
            source = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/spotify","root","");
        }
        return source;
    }
}
