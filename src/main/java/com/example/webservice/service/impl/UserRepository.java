package com.example.webservice.service.impl;


import com.example.webservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select * from user where userName = ?1 ", nativeQuery = true)
    User findByUserName(String username);

    @Query(value = "select * from user where id = ?1", nativeQuery = true)
    Optional<User> findUserById(String id);
}
