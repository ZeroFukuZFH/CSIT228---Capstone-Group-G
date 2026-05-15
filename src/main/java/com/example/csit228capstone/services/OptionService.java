package com.example.csit228capstone.services;

import com.example.csit228capstone.Database.Database;
import com.example.csit228capstone.data.Transaction;
import com.example.csit228capstone.session.Session;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OptionService extends Database {
    public OptionService(){
        super();
    }

    public void importCsv(List<Transaction> transactions) {

    }
}
