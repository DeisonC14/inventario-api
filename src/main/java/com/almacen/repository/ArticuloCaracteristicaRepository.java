package com.almacen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.almacen.entity.ArticuloCaracteristica;

public interface ArticuloCaracteristicaRepository extends JpaRepository<ArticuloCaracteristica, Integer> {
	
	
}
