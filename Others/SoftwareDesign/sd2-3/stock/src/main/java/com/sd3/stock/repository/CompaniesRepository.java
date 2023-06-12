package com.sd3.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sd3.stock.model.Company;

@Repository
public interface CompaniesRepository extends JpaRepository<Company, Long> {
}
