package com.almacen.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.almacen.entity.User;
public interface UserRepository extends JpaRepository<User, Integer> {Optional<User> findByUsername(String username); }