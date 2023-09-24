package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class BaseDao {
    private final String DB_FILE = "/Users/thlee/DataGripProjects/zerobase/mission1.sqlite";

    protected BaseDao() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
    }

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + DB_FILE);
    }
}
