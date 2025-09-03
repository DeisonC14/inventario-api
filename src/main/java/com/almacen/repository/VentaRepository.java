package com.almacen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.almacen.entity.Venta;

public interface VentaRepository extends JpaRepository<Venta, Integer> {

}
