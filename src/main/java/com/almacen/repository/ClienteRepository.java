package com.almacen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.almacen.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
