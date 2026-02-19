package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.DBConnection;

import model.Admin;

public class ProductController {
    public static int vendMachMoney = 0;
    
    public static void addProduct(String product_name, int quantity, int price_in_pesos) 
    {
        if (product_name == null)
        {
            System.out.println("No name labelled.");
            return;
        }
        
        String sql = """
                     INSERT INTO products(product_name, quantity, price_in_pesos)
                     VALUES (?, ?, ?)
                     """;
        
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setString(1, product_name);
            ps.setInt(2, quantity);
            ps.setFloat(3, price_in_pesos);
           
            ps.execute();
        }
        
        catch (SQLException error)
        {
            error.printStackTrace();
        }
    }

    public static void editProduct(String initial_product_name, String new_product_name, int new_quantity, int new_price_in_pesos)
    {
        if (initial_product_name == null || new_product_name == null)
        {
            System.out.println("No product name mentioned.");
            return;
        }
        
        String sql = """
                     UPDATE products
                     SET products.product_name = ?, products.quantity = ?, products.price_in_pesos = ?
                     WHERE products.product_name = ?
                     """;
        
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setString(1, new_product_name);
            ps.setInt(2, new_quantity);
            ps.setFloat(3, new_price_in_pesos);
            ps.setString(4, initial_product_name);
            
            int rows = ps.executeUpdate();
            
            if (rows > 0)
            {
                System.out.println("Product updated successfully.");
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

    public static void deleteProduct(String product_name)
    {
        String sql = """
                     DELETE FROM products WHERE products.product_name = ?
                     """;
        
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setString(1, product_name);
            
            ps.executeQuery();
        }
        
        catch (SQLException error)
        {
            error.printStackTrace();
        }
    }
    
    public static void viewProducts()
    {
        String sql = """
                     SELECT * FROM products
                     """;
                
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql))
        {
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) 
            {
                // Retrieve data by column name or index
                int id = rs.getInt("id");
                String product_name = rs.getString("product_name");
                int quantity = rs.getInt("quantity");
                float price = rs.getFloat("price_in_pesos");

                System.out.println("ID: " + id + ", Name: " + product_name + ", Quantity: " + quantity + ", Price: " + price);
            }
        }
        
        catch (SQLException error)
        {
            error.printStackTrace();
        }
    }
    
    public static void viewOneProduct(String product_name)
    {
        String sql = """
                     SELECT * FROM products
                     WHERE product_name = ?
                     """;
                
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setString(1, product_name);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) 
            {
                // Retrieve data by column name or index
                int id = rs.getInt("id");
                String productName = rs.getString("product_name");
                int quantity = rs.getInt("quantity");
                float price = rs.getFloat("price_in_pesos");

                System.out.println("ID: " + id + ", Name: " + productName + ", Quantity: " + quantity + ", Price: " + price);
            }
            
            else
            {
                System.out.println("Inexistent product.");
            }
        }
        
        catch (SQLException error)
        {
            error.printStackTrace();
        }
    }
    
    public static int getPrice(int id)
    {
        String sql = """
                     SELECT price_in_pesos FROM products
                     WHERE id = ?
                     """;
        
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next())
            {
                return rs.getInt("price_in_pesos");
            }
            
            else
            {
                System.out.println("Something went wrong.");
                return 0;
            }
            
        }
        
        catch (SQLException error)
        {
            error.printStackTrace();
            return 0;
        }
    }
    
    public static String getProductName(int id)
    {
        String sql = """
                     SELECT product_name FROM products
                     WHERE id = ?
                     """;
        
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next())
            {
                return rs.getString("product_name");
            }
            
            else
            {
                return "Something went wrong.";
            }
            
        }
        
        catch (SQLException error)
        {
            error.printStackTrace();
            return "Something went wrong.";
        }
    }
    
    public static int getQuantity(int id)
    {
        String sql = """
                     SELECT quantity FROM products
                     WHERE id = ?
                     """;
        
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next())
            {
                return rs.getInt("quantity");
            }
            
            else
            {
                System.out.println("Something went wrong.");
                return 0;
            }
            
        }
        
        catch (SQLException error)
        {
            error.printStackTrace();
            return 0;
        }
    }
    
    public static void setQuantity(int id, int quantity){
        String sql = """
                     UPDATE products
                     SET quantity = ?
                     WHERE id = ?
                     """;
        
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setInt(1, quantity);
            ps.setInt(2, id);
           
            int rows = ps.executeUpdate();
            
            if (rows > 0)
            {
                System.out.println("Stock has been updated successfully.");
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
    
    public static int getMoney()
    {
        return vendMachMoney;
    }
}
