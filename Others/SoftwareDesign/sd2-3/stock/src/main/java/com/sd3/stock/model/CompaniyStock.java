package com.sd3.stock.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@IdClass(CompaniyStock.CompaniesStocksId.class)
public class CompaniyStock {

    public static class CompaniesStocksId implements Serializable {
        public long companyId;
        public long stockId;
    }

    @Id
    public long companyId;

    @Id
    public long stockId;
}
