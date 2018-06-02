package com.imooc.jdbc.dao;

import com.imooc.jdbc.bean.Message;
import com.imooc.jdbc.common.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 消息DAO
 *
 * @version 1.0
 */
public class MessageDAO {

    /**
     * 保存留言信息
     * @param message
     * @return
     */
    public boolean save(Message message) {
        Connection conn = ConnectionUtil.getConnection();
        String sql = "insert into message(user_id, username, title, content, create_time) values (?, ?, ?, ?, ?)";
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, message.getUserId());
            stmt.setString(2, message.getUsername());
            stmt.setString(3, message.getTitle());
            stmt.setString(4, message.getContent());
            stmt.setTimestamp(5, new Timestamp(message.getCreateTime().getTime()));
            stmt.execute();
        } catch (Exception e) {
            System.out.println("保存留言信息失败。");
            e.printStackTrace();
            return false;
        } finally {
            ConnectionUtil.release(null, stmt, conn);
        }
        return true;
    }

    /**
     * 分页查询全部留言
     * @param page 当前页码
     * @param pageSize 每页记录数
     * @return
     */
    public List<Message> getMessages(int page, int pageSize) {
        Connection conn = ConnectionUtil.getConnection();
        String sql = "select * from message order by create_time desc limit ?, ?";//limit m, n：从第m条开始，取出总共n条记录
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Message> messages = new ArrayList<>();
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, (page - 1) * pageSize);
            stmt.setInt(2, pageSize);
            rs = stmt.executeQuery();
            while (rs.next()) {
                messages.add(new Message(rs.getLong("id"),
                        rs.getLong("user_id"),
                        rs.getString("username"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getTimestamp("create_time")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.release(rs, stmt, conn);
        }
        return messages;
    }
    public List<Message> getMyMessages(int page, int pageSize, String username) {
        Connection conn = ConnectionUtil.getConnection();
        String sql = "select * from message where username=? order by create_time desc limit ?, ?";//limit m, n：从第m条开始，取出总共n条记录
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Message> messages = new ArrayList<>();
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1,username);
            stmt.setInt(2, (page - 1) * pageSize);
            stmt.setInt(3, pageSize);
            rs = stmt.executeQuery();
            while (rs.next()) {
                messages.add(new Message(rs.getLong("id"),
                        rs.getLong("user_id"),
                        rs.getString("username"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getTimestamp("create_time")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.release(rs, stmt, conn);
        }
        return messages;
    }

    /**
     * 计算所有留言数量
     * @return
     */
   public int countMessages() {
       Connection conn = ConnectionUtil.getConnection();
       String sql = "select count(*) total from message";
       PreparedStatement stmt = null;
       ResultSet rs = null;
       try {
           stmt = conn.prepareStatement(sql);
           rs = stmt.executeQuery();
           while (rs.next()) {
               return rs.getInt("total");
           }
       } catch (SQLException e) {
           e.printStackTrace();
       } finally {
           ConnectionUtil.release(rs, stmt, conn);
       }
       return 0;
   }


    public void updateMsg(long id, long userid,String title,String content) {
        Connection conn = ConnectionUtil.getConnection();
        String sql = "update message set title = ?,content=? where id=? and user_id=?";
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1,title);
            stmt.setString(2,content);
            stmt.setLong(3,id);
            stmt.setLong(4,userid);
            stmt.execute();
        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.release(null, stmt, conn);
        }

    }

    public void deleteMsg(long id, long userid, String username) {
        Connection conn = ConnectionUtil.getConnection();
        String sql = "delete from message  where id=? and user_id=? and username =?";
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1,id);
            stmt.setLong(2,userid);
            stmt.setString(3,username);
            stmt.execute();
        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.release(null, stmt, conn);
        }
    }
}
