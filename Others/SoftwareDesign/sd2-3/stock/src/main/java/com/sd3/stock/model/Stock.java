package com.sd3.stock.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Stock {

    @Id
    @GeneratedValue
    public long id;

    public long amount = 0;

    public long price = 0;
}
