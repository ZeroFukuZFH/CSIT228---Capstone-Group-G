package com.example.csit228capstone.session;

import java.util.HashMap;
import java.util.Map;

public class Session {
    private static Session instance;
    private Map<String, String> attributes = new HashMap<>();

    private Session() {}

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public void setAttribute(String key, String value) {
        attributes.put(key, value);
    }

    public String getAttribute(String key) {
        return attributes.get(key);
    }

    public void invalidate() {
        attributes.clear();
    }
}
