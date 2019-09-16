package com.white.stratego.stratego.Market.repository;

import com.white.stratego.stratego.Market.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
    User findByUsername(String username);
    User findByEmail(String email);
}
