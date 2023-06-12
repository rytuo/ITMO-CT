package com.sd3.client.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@IdClass(UserStock.UserStockId.class)
public class UserStock {

    public static class UserStockId implements Serializable {
        public long userId;
        public long stockId;
    }

    @Id
    public long userId;

    @Id
    public long stockId;

    public long amount;
}
