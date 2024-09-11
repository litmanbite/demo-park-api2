package com.mballem.demo_park_api.DBExcep;

public class EntityNotFoundExcep extends RuntimeException{

    public EntityNotFoundExcep(String msg) {
        super(msg);}
}
