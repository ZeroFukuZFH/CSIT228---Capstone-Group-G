package com.example.csit228capstone.services;

import com.example.csit228capstone.database.dbsingleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class category_service {

    public void loadCategories() {

        try {

            Connection conn =
                    dbsingleton
                            .getInstance()
                            .getConnection();

            String sql =
                    "SELECT category_id, category_name " +
                            "FROM categories";

            PreparedStatement pst =
                    conn.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("category_id");

                String name =
                        rs.getString("category_name");

                System.out.println(
                        id + " - " + name
                );

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}