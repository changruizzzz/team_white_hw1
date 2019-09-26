package com.white.stratego.stratego.market.repository;

import com.white.stratego.stratego.market.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository<T extends User> extends JpaRepository<User, Long>{
    T findByUsername(String username);
    T findByEmail(String email);
}
