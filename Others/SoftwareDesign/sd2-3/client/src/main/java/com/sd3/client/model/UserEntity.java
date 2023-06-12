package com.sd3.client.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue
    public long id;

    public long funds = 0;
}
