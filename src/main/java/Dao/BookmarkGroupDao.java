package Dao;

import Entity.BookmarkGroup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookmarkGroupDao extends BaseDao {
    public BookmarkGroupDao() throws ClassNotFoundException {
        super();
    }

    public BookmarkGroup findById(int id) {
        try (
                Connection conn = getConnection();
                PreparedStatement statement = conn.prepareStatement("select id, name, [order], reg_dttm, updated_dttm from bookmark_group where id = ?;");
        ) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String updatedDttm = resultSet.getString("updated_dttm");

                    return BookmarkGroup.builder()
                            .id(resultSet.getInt("id"))
                            .name(resultSet.getString("name"))
                            .order(resultSet.getInt("order"))
                            .registerDateTime(LocalDateTime.parse(resultSet.getString("reg_dttm")))
                            .updatedDateTime((updatedDttm != null) ? LocalDateTime.parse(updatedDttm) : null)
                            .build();
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<BookmarkGroup> findAll() {
        List<BookmarkGroup> bookmarkGroupList = new ArrayList<>();

        try (
                Connection conn = getConnection();
                PreparedStatement statement = conn.prepareStatement("select id, name, [order], reg_dttm, updated_dttm from bookmark_group order by [order] desc;");
                ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                String updatedDttm = resultSet.getString("updated_dttm");

                bookmarkGroupList.add(BookmarkGroup
                        .builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .order(resultSet.getInt("order"))
                        .registerDateTime(LocalDateTime.parse(resultSet.getString("reg_dttm")))
                        .updatedDateTime((updatedDttm != null) ? LocalDateTime.parse(updatedDttm) : null)
                        .build());
            }
            return bookmarkGroupList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public BookmarkGroup create(BookmarkGroup bookmarkGroup) {
        try (
                Connection conn = getConnection();
                PreparedStatement statement = conn.prepareStatement("insert into bookmark_group(name, [order], reg_dttm) values(?, ?, ?);");
        ) {
            LocalDateTime now = LocalDateTime.now();
            statement.setString(1, bookmarkGroup.getName());
            statement.setInt(2, bookmarkGroup.getOrder());
            statement.setString(3, now.toString());
            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return bookmarkGroup
                            .toBuilder()
                            .id(resultSet.getInt(1))
                            .registerDateTime(now)
                            .build();
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean update(BookmarkGroup bookmarkGroup) {
        try (
                Connection conn = getConnection();
                PreparedStatement statement = conn.prepareStatement("update bookmark_group set name = ?, [order] = ?, updated_dttm = ? where id = ?");
        ) {
            statement.setString(1, bookmarkGroup.getName());
            statement.setInt(2, bookmarkGroup.getOrder());
            statement.setString(3, LocalDateTime.now().toString());
            statement.setInt(4, bookmarkGroup.getId());
            return (statement.executeUpdate() > 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(BookmarkGroup bookmarkGroup) {
        try (
                Connection conn = getConnection();
                PreparedStatement statement = conn.prepareStatement("delete from bookmark_group where id = ?");
        ) {
            statement.setInt(1, bookmarkGroup.getId());
            return (statement.executeUpdate() > 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
        BookmarkGroupDao dao= new BookmarkGroupDao();
        System.out.println(dao.findById(2));
    }
}
