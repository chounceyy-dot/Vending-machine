package controller;

// For SQL database connection
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Admin;
import utils.DBConnection;

public class AdminController {
    public static Admin login(String username, String password) 
    {
        if (password.isBlank() || username.isBlank()) 
        {
            System.out.println("\nYou left a blank space.");
            return null;
        }
        
        String sql = """
                     SELECT * FROM admins WHERE username = ? AND password = ?
                     """;
        
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) 
        {
            ps.setString(1, username);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) 
            {
                System.out.println("\nLogged in as " + rs.getString("username"));
                return new Admin(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
            }
            
            System.out.println("\nIncorrect username or password.");
            return null;
        } 
        
        catch (SQLException error) 
        {
            error.printStackTrace();
            return null;
        }
        
    }

    public static Admin register(String username, String password, String confirmPassword) 
    {
        if (username.isBlank()) 
        {
            System.out.println("\nUsername is empty.");
            return null;
        }
        
        if (password.isBlank() || !password.equals(confirmPassword)) 
        {
            System.out.println("\nPassword and password confirmation does not match.");
            return null;
        }

        String sql = """
                    INSERT INTO admins(username, password)
                    VALUES(?, ?)
                    """;

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) 
        {
            ps.setString(1, username);
            ps.setString(2, password);

            int rows = ps.executeUpdate();

            if (rows > 0) 
            {

                ResultSet keys = ps.getGeneratedKeys();

                if (keys.next()) 
                {
                    int id = keys.getInt(1);

                    Admin admin = new Admin(id, username, password);

                    System.out.println("\n" + username + " is created successfully.");

                    return admin;
                }
            }

        } 
        
        catch (SQLException error) 
        {
            error.printStackTrace();
        }

        return null;
    }
    
    public static void changePassword(Admin admin, String oldPassword, String newPassword, String confirmPassword) 
    {
        if (!admin.verifyPassword(oldPassword)) 
        {
            System.out.println("Old password does no match");
            return;
        }
        
        if (newPassword.isBlank() || !newPassword.equals(confirmPassword)) 
        {
            System.out.println("Password and confirm password does not match.");
            return;
        }
        
        String sql = """
                     UPDATE admins
                     SET password = ?
                     WHERE id = ?
                     """;
        
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) 
        {    
            ps.setString(1, newPassword);
            ps.setInt(2, admin.getId());
            
            int rows = ps.executeUpdate();
            
            if (rows > 0) 
            {
                System.out.println("Password successfully changed.");
                admin.setPassword(newPassword);
            } 
            
            else 
            {
                System.out.println("Something went wrong.");
            }
        } 
        
        catch (SQLException error) 
        {
            error.printStackTrace();
        }
    }
}
