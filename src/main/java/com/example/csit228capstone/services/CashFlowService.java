package com.example.csit228capstone.services;

import com.example.csit228capstone.Database.Database;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CashFlowService extends Database implements IIncomeService,IExpenseService {
    public CashFlowService(){
        super();
    }
}
