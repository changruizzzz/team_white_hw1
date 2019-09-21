package com.white.stratego.stratego.market.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.white.stratego.stratego.market.model.MarketUnit;
import com.white.stratego.stratego.market.model.User;
import java.util.Set;

public interface MarketUnitRepository extends JpaRepository<MarketUnit, Long>{

    Set<MarketUnit> findBySavedBy(User user);
    Set<MarketUnit> findByCreatedBy(User user);
    void deleteMarketUnitsById(long id);
    MarketUnit findById(long id);
}
