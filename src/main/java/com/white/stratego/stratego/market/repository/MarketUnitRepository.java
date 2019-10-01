package com.white.stratego.stratego.market.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.white.stratego.stratego.market.model.MarketUnit;
import com.white.stratego.stratego.market.model.User;

import java.util.List;
import java.util.Set;

public interface MarketUnitRepository<T extends MarketUnit> extends JpaRepository<MarketUnit, Long>{

    List<T> findBySavedBy(User user);

    List<T> findByCreatedBy(User user);
    void deleteMarketUnitsById(long id);
    T findById(long id);
}
