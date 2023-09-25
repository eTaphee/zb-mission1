package Util;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class ResultSetUtil {
    public static boolean exists(ResultSet resultSet, String columnLabel) {
        if (resultSet == null) {
            return false;
        }

        try {
            resultSet.findColumn(columnLabel);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static Integer getInteger(ResultSet resultSet, String columnLabel) throws SQLException {
        if (exists(resultSet, columnLabel)) {
            return resultSet.getInt(columnLabel);
        }
        return null;
    }

    public static String getString(ResultSet resultSet, String columnLabel) throws SQLException {
        if (exists(resultSet, columnLabel)) {
            return resultSet.getString(columnLabel);
        }
        return null;
    }
}
