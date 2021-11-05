/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop_management_system;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class DatabaseConnection {
    
    Connection connection;           
    /**
     *
     */
    public static Connection connectDB() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:sqlserver://localhost:1433;databaseName=ShopManagementSystem;selectMethod=cursor", "sa", "123456");

            System.out.println("DATABASE NAME IS:" + connection.getMetaData().getDatabaseProductName());

            /*Statement statement = connection.createStatement();
            ResultSet resultSet = statement
                    .executeQuery("SELECT * FROM Admin");
            
            
            while (resultSet.next()) {
                
                System.out.println("Admin NAME:" + 
                        resultSet.getString("FirstName"));
                
            }*/
            return connection;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    public static void main(String[] args) {
        DatabaseConnection cnObj = new DatabaseConnection();
        cnObj.connectDB();
    }
    
}
