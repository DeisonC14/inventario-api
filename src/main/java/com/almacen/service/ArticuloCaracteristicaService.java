package com.almacen.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.almacen.entity.Articulo;
import com.almacen.entity.ArticuloCaracteristica;
import com.almacen.entity.Caracteristica;
import com.almacen.repository.ArticuloCaracteristicaRepository;
import com.almacen.repository.ArticuloRepository;
import com.almacen.repository.CaracteristicaRepository;

@Service
public class ArticuloCaracteristicaService {

	@Autowired
	private ArticuloRepository articuloRepository;

	@Autowired
	private CaracteristicaRepository caracteristicaRepository;

	@Autowired
	private ArticuloCaracteristicaRepository artCarRepository;

	public ArticuloCaracteristica guardar(ArticuloCaracteristica request) {

		Articulo articulo = articuloRepository.findById(request.getArticulo().getId())
				.orElseThrow(() -> new RuntimeException("Articulo no encontrado"));

		Caracteristica caracteristica = caracteristicaRepository.findById(request.getCaracteristica().getId())
				.orElseThrow(() -> new RuntimeException("Caracteristica no encontrado"));

		request.setArticulo(articulo);
		request.setCaracteristica(caracteristica);

		return artCarRepository.save(request);
	}

	// Actualizar
	public ArticuloCaracteristica actualizar(Integer id, ArticuloCaracteristica request) {
		ArticuloCaracteristica artCar = artCarRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("ArticuloCaracteristica no encontrada con ID: " + id));

		Articulo articulo = articuloRepository.findById(request.getArticulo().getId())
				.orElseThrow(() -> new RuntimeException("Articulo no encontrado"));

		Caracteristica caracteristica = caracteristicaRepository.findById(request.getCaracteristica().getId())
				.orElseThrow(() -> new RuntimeException("Caracteristica no encontrado"));

		  artCar.setArticulo(articulo);
		  artCar.setCaracteristica(caracteristica);
		  artCar.setValor(request.getValor());

		return artCarRepository.save(artCar);
	}

	
	// Eliminar
	public boolean eliminar(Integer id) {
		if (artCarRepository.existsById(id)) {
			artCarRepository.deleteById(id);
			return true;
		}
		return false;
	}

}
