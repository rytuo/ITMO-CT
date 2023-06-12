package com.sd3.stock.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Company {

    @Id
    @GeneratedValue
    public long id;
}
