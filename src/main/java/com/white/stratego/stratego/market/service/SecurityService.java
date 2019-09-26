package com.white.stratego.stratego.market.service;

public interface SecurityService {

    String findLoggedInUsername();

    void autoLogin(String email, String password);
}
