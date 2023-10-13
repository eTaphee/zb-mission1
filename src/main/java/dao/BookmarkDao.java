package dao;

import entity.Bookmark;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookmarkDao extends BaseDao {

    public BookmarkDao() throws ClassNotFoundException, IOException {
        super();
    }

    public Bookmark findById(int id) {
        try (
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement("select\n" +
                "    b.*,\n" +
                "    bg.*,\n" +
                "    pwi.*\n" +
                "from\n" +
                "    bookmark as b\n" +
                "inner join\n" +
                "        main.bookmark_group bg on bg.id = b.bookmark_group_id\n" +
                "inner join\n" +
                "        main.public_wifi_info pwi on pwi.mgr_no = b.wifi_mgr_no\n" +
                "where b.id = ?")
        ) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                return (resultSet.next()) ? Bookmark.from(resultSet) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Bookmark findByBookmarkGroupIdAndWifiManageNo(int bookmarkGroupId, String wifiManageNo) {
        if (wifiManageNo == null) {
            return null;
        }

        try (
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement("select\n" +
                "    b.*,\n" +
                "    bg.*,\n" +
                "    pwi.*\n" +
                "from\n" +
                "    bookmark as b\n" +
                "inner join\n" +
                "        main.bookmark_group bg on bg.id = b.bookmark_group_id\n" +
                "inner join\n" +
                "        main.public_wifi_info pwi on pwi.mgr_no = b.wifi_mgr_no\n" +
                "where bg.id = ? and pwi.mgr_no = ?")
        ) {
            statement.setInt(1, bookmarkGroupId);
            statement.setString(2, wifiManageNo);

            try (ResultSet resultSet = statement.executeQuery()) {
                return (resultSet.next()) ? Bookmark.from(resultSet) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Bookmark> findAll() {
        List<Bookmark> bookmarkList = new ArrayList<>();

        try (
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement("select\n" +
                "    b.*,\n" +
                "    bg.*,\n" +
                "    pwi.*\n" +
                "from\n" +
                "    bookmark as b\n" +
                "inner join\n" +
                "        main.bookmark_group bg on bg.id = b.bookmark_group_id\n" +
                "inner join\n" +
                "        main.public_wifi_info pwi on pwi.mgr_no = b.wifi_mgr_no\n" +
                "order by b.id asc;");
            ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                bookmarkList.add(Bookmark.from(resultSet));
            }
            return bookmarkList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Bookmark create(Bookmark bookmark) {
        if (bookmark == null
            || bookmark.getWifi() == null
            || bookmark.getGroup() == null) {
            return null;
        }

        try (
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(
                "insert into bookmark(bookmark_group_id, wifi_mgr_no, reg_dttm) values(?, ?, ?);")
        ) {
            LocalDateTime now = LocalDateTime.now();
            statement.setInt(1, bookmark.getGroup().getId());
            statement.setString(2, bookmark.getWifi().getManageNo());
            statement.setString(3, now.toString());
            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return bookmark
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

    public boolean delete(Bookmark bookmark) {
        if (bookmark == null) {
            return false;
        }

        try (
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(
                "delete from bookmark where id = ?")
        ) {
            statement.setInt(1, bookmark.getId());
            return (statement.executeUpdate() > 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
    }
}
