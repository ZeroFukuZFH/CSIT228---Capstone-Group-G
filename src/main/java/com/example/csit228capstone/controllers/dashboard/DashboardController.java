package com.example.csit228capstone.controllers.dashboard;

import com.example.csit228capstone.services.AccountService;
import com.example.csit228capstone.services.OptionService;
import javafx.fxml.FXML;

public class DashboardController {
    @FXML
    public void initialize(){
        AccountService accountService = new AccountService();
    }
}