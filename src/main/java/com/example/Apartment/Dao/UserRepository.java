package com.example.Apartment.Dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Apartment.Entity.UserLogin;

@Repository
public interface UserRepository extends JpaRepository<UserLogin, Integer> {

	Optional<UserLogin> findByUsername(String userName);
}
