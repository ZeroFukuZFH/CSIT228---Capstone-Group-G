package com.example.csit228capstone.services;

import com.example.csit228capstone.Database.Database;
import com.example.csit228capstone.session.Session;

public class BaseService extends Database {
    public BaseService(){
        if(Session.getInstance().getAttribute("currency") == null){
            Session.getInstance().setAttribute("currency","PHP");
        }
    }
    public String getCurrency(){
        return Session.getInstance().getAttribute("currency");
    }

    public void setCurrency(String currency){
        Session.getInstance().setAttribute("currency",currency);
    }
    public int getCurrentUserId() {
        String id = Session.getInstance().getAttribute("id");
        if (id == null) {
            throw new IllegalStateException("No logged-in user found in session.");
        }
        return Integer.parseInt(id);
    }
    public int getDefaultAccountId() {
        String id = Session.getInstance().getAttribute("default_account_id");
        if (id == null) {
            throw new IllegalStateException("No logged-in user found in session.");
        }
        return Integer.parseInt(id);
    }
    public int getDefaultCategoryId() {
        String id = Session.getInstance().getAttribute("default_category_id");
        if (id == null) {
            throw new IllegalStateException("No logged-in user found in session.");
        }
        return Integer.parseInt(id);
    }


}
