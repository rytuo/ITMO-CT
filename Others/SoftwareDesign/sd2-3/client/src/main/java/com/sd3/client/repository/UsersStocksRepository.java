package com.sd3.client.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sd3.client.model.UserStock;

@Repository
public interface UsersStocksRepository extends JpaRepository<UserStock, UserStock.UserStockId> {

    List<UserStock> getUserStocksByUserId(long userId);

    Optional<UserStock> getUserStocksByUserIdAndStockId(long userId, long stockId);
}
