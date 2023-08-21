package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {
    
    public Account registerAccount(Account account)
    {
        Connection connection = ConnectionUtil.getConnection();

        try
        {
            String sql = "INSERT INTO Account (username, password) values (?, ?)" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                System.out.println("Account Registered");
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        System.out.println("Registration Failed");
        return null;
    }

    public Account getAccountByUsername(String username){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM Account where username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"),
                rs.getString("username"),
                rs.getString("password"));
                System.out.println(account.getUsername() + " exists");
                return account;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        System.out.println("Account does not exist");
        return null;
    }

    public Account login(String username, String password){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM Account where username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"),
                rs.getString("username"),
                rs.getString("password"));
                System.out.println(account.getUsername() + " " + account.getAccount_id() + " exists");
                return account;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        System.out.println("Account does not exist");
        return null;
    }
}
