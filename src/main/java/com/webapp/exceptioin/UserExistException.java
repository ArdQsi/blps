package com.webapp.exceptioin;

public class UserExistException extends RuntimeException {
    public UserExistException(String msg){
        super(msg);
    }
}
