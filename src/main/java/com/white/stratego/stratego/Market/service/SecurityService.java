package com.white.stratego.stratego.Market.service;

public interface SecurityService {

    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
