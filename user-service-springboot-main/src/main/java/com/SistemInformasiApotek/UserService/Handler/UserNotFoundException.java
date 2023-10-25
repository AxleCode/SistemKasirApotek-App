package com.SistemInformasiApotek.UserService.Handler;


public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}

