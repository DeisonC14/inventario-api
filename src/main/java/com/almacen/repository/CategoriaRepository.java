package com.almacen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.almacen.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

}
