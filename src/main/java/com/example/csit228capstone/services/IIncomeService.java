package com.example.csit228capstone.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public interface IIncomeService {
    default Map<Date, Double> getIncome(){
        Map<Date, Double> income = new HashMap<>();
        return income;
    }
}
