package com.mballem.demo_park_api.DBExcep;

import lombok.Data;

public class DBUsername extends RuntimeException {

    public DBUsername(String m) {
        super(m);
    }
}
