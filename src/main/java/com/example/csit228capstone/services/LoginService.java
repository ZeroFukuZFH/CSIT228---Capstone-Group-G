package com.example.csit228capstone.services;

import com.example.csit228capstone.Database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.RowId;
import java.sql.SQLException;
import com.example.csit228capstone.session.Session;
public class LoginService extends Database {
    public LoginService(){
        super();
    }
    public boolean login(String firstname ,String lastname, String password){

        String sql = "SELECT * FROM user WHERE first_name=? AND last_name =? AND password=?";
        try(PreparedStatement pstmt = super.connection.prepareStatement(sql)) {
            pstmt.setString(1,firstname);
            pstmt.setString(2,lastname);
            pstmt.setString(3,password);
            ResultSet resultSet = pstmt.executeQuery();
            boolean status = resultSet.next();
            if (status){
                int id = resultSet.getInt("user_id");
                Session.getInstance().setAttribute("id",String.valueOf(id));
            }
            return status;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


}
