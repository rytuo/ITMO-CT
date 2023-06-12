package com.sd3.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sd3.client.model.UserEntity;

@Repository
public interface UsersRepository extends JpaRepository<UserEntity, Long> {
}
