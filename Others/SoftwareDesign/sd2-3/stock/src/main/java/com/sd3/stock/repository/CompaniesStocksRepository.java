package com.sd3.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sd3.stock.model.CompaniyStock;

@Repository
public interface CompaniesStocksRepository extends JpaRepository<CompaniyStock, CompaniyStock.CompaniesStocksId> {
}
