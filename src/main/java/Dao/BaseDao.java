package Dao;

import Service.PublicWifiInfoService;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public abstract class BaseDao {
    private final String dbFile;

    protected BaseDao() throws ClassNotFoundException, IOException {
        Class.forName("org.sqlite.JDBC");

        Properties prop = new Properties();
        try (InputStream stream = PublicWifiInfoService.class.getClassLoader().getResourceAsStream("application.properties")) {
            prop.load(stream);
            dbFile = prop.getProperty("dbFile");
        } catch (Exception e) {
            throw e;
        }
    }

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + dbFile);
    }
}
