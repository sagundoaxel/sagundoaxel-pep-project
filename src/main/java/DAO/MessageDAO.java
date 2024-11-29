package DAO;

import Util.ConnectionUtil;
import Model.Message;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import java.sql.SQLException;

public class MessageDAO {

    //CREATE
    //Service should check that the msg < 255
    public Message insertMessage(Message msg){ 
        Connection conn = ConnectionUtil.getConnection();
        try {
            //                                      posted_by, message_text, time_posted_epoch
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, msg.getPosted_by());
            ps.setString(2, msg.getMessage_text());
            ps.setLong(3, msg.getTime_posted_epoch());

            ps.executeUpdate();

            ResultSet pkeyResultSet = ps.getGeneratedKeys();

            if (pkeyResultSet.next()) {
                int generated_msg_id = (int) pkeyResultSet.getLong(1);
                // System.out.println("HELLOOO" + generated_msg_id);
                return new Message(generated_msg_id, msg.getPosted_by(), msg.getMessage_text(), msg.getTime_posted_epoch());
            }            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    //GET ALL MESSAGES
    public List<Message> getAllMessages(){

        List<Message> allmsgs = new ArrayList<>();
        Connection conn = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM message;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Message newMsg = new Message(rs.getInt("message_id"), 
                rs.getInt("posted_by"), 
                rs.getString("message_text"), 
                rs.getLong("time_posted_epoch"));

                allmsgs.add(newMsg);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return allmsgs;
    }

    //GET MESSAGE BY ID
    public Message getMessageById(int id){
        Connection conn = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM message WHERE message_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Message newMsg = new Message(rs.getInt("message_id"), 
                rs.getInt("posted_by"), 
                rs.getString("message_text"), 
                rs.getLong("time_posted_epoch"));

                return newMsg;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    //DELETE MESSAGE BY ID
    // return null if no message found. or return message if it was found. 
    public Message deleteMessageById(int id){
        Connection conn = ConnectionUtil.getConnection();

        Message msgToDelete = getMessageById(id);
        // this means that the message doesn't exist
        if (msgToDelete == null) { return null; }

        try {
            String sqlDelete = "DELETE FROM message WHERE message_id = ?;";

            PreparedStatement psDelete = conn.prepareStatement(sqlDelete);
            psDelete.setInt(1, id);

            int rowsAffected = psDelete.executeUpdate();

            if (rowsAffected == 1){
                return msgToDelete;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // public List<Message> getMessagesFromUser(int id){

    // }



    
    
}
