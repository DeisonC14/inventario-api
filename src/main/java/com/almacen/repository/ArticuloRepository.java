package com.almacen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.almacen.entity.Articulo;

public interface ArticuloRepository extends JpaRepository<Articulo, Integer> {

}
