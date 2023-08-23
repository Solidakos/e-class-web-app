package com.example.eclasswebapp.mysql.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.username = ?1")
    public User findByUsername(@Param("username") String username);

    @Query("SELECT COUNT(*) FROM User WHERE username = ?1")
    public int doesUserExist(@Param("username") String username);

}