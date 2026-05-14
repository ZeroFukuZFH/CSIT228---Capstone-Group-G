package com.example.csit228capstone.services;

import com.example.csit228capstone.Database.Database;
import com.example.csit228capstone.session.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterService extends Database {
    public RegisterService(){
        super();
    }
    public void register(String firstname,String lastname,String password){
        String sql = "INSERT INTO user (first_name,last_name,password) VALUES (?,?,?)";
        try(PreparedStatement pstmt = super.connection.prepareStatement(sql)) {
            pstmt.setString(1,firstname);
            pstmt.setString(2,lastname);
            pstmt.setString(3,password);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt("user_id");
                    Session.getInstance().setAttribute("id",String.valueOf(id));
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
