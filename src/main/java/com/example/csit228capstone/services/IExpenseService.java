package com.example.csit228capstone.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public interface IExpenseService {
    default Map<Date, Double> getExpenses(){
        Map<Date, Double> expenses = new HashMap<>();
        return expenses;
    }
}
