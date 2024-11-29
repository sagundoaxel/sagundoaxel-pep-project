package DAO;

import Util.ConnectionUtil;
import Model.Account;

import java.sql.*;
// import java.util.ArrayList;
// import java.util.List;


public class AccountDAO {
    
    public Account insertAccount(Account account){
      
        Connection conn = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?);" ;
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ps.executeUpdate();

            ResultSet pkeyResultSet = ps.getGeneratedKeys();

            if (pkeyResultSet.next()) {
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Account getAccountByUsername(String username){

        Connection conn = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM account WHERE username = ?;" ;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"), 
                rs.getString("username"), 
                rs.getString("password"));

                return account;
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Account getAccountById(int account_id){
        Connection conn = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM account WHERE account_id = ?;" ;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, account_id);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Account dbAccount = new Account(rs.getInt("account_id"), 
                rs.getString("username"), 
                rs.getString("password"));

                return dbAccount;
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        return null;

    }


    

}
