/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Account;

/**
 *
 * @author DUYTHAI
 */
public class AccountDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<Account> getAllAccounts() {
        List<Account> list = new ArrayList<>();
        String sql = "SELECT * FROM ACCOUNTS";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Account(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("fullname"),
                        rs.getString("avatar_url"),
                        rs.getString("role"),
                        rs.getString("date_created")
                ));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public Account login(String email, String password) {
        String sql = "SELECT id, email, password, fullname, avatar_url, role, date_created FROM ACCOUNTS\n"
                + "WHERE email = '" + email + "' AND password = '" + password + "'";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                return new Account(
                        rs.getInt("id"),
                        rs.getString("email"),
                        "xxxxxxxx",
                        rs.getString("fullname"),
                        rs.getString("avatar_url"),
                        rs.getString("role"),
                        rs.getString("date_created"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }

    public String register(String email, String password, String fullname, String avatar_url) {
        String sql = "INSERT INTO ACCOUNTS(email, password, fullname, avatar_url, role) VALUES\n"
                + "('" + email + "', '" + password + "', N'" + fullname + "', '" + avatar_url + "', 'customer')";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == 2627) {
                return "Account existed";
            } else {
                System.out.println(e);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return "";
    }

    public void updateProfile(String email, String password, String fullname, String avatar_url) {
        String sql = "UPDATE ACCOUNTS SET password = '" + password + "', fullname = N'" + fullname + "', avatar_url = '" + avatar_url + "' WHERE email = '" + email + "';";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Account checkAccountExisted(String email) {
        String sql = "SELECT id, email, password, fullname, avatar_url, role, date_created FROM Accounts WHERE email = '" + email + "'";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                return new Account(
                        rs.getInt("id"),
                        rs.getString("email"),
                        "xxxxxxxx",
                        rs.getString("fullname"),
                        rs.getString("avatar_url"),
                        rs.getString("role"),
                        rs.getString("date_created"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }

    public static void main(String[] args) {
        AccountDAO dao = new AccountDAO();
//        List<Account> list = dao.getAllAccounts();
//        for (Account p : list) {
//            System.out.println(p.toString());
//        }
        System.out.println(dao.checkAccountExisted("admin@admin.com").toString());
    }
}
