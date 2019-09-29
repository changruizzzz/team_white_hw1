package com.white.stratego.stratego.game.repository;

import com.white.stratego.stratego.game.Model.Statistics;
import com.white.stratego.stratego.market.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
    public Statistics findByUser(User user);
}
