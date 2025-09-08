package com.almacen.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.almacen.entity.Articulo;
import com.almacen.entity.Compra;
import com.almacen.entity.CompraArticulo;
import com.almacen.entity.Proveedor;
import com.almacen.repository.ArticuloRepository;
import com.almacen.repository.CompraArticuloRepository;
import com.almacen.repository.CompraRepository;
import com.almacen.repository.ProveedorRepository;



@Service
public class CompraService {
	

	@Autowired
	private CompraRepository compraRepository;

	@Autowired
	private CompraArticuloRepository compraArtRepository;
	
	@Autowired
	private ArticuloRepository artRepository;
	
	@Autowired
	private ProveedorRepository proveedorRepository;

	public Compra guardar(Compra request) {
		// Validar el cliente
		Proveedor proveedor = proveedorRepository.findById(request.getProveedor()
				.getId()).orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + request.getProveedor().getId()));

		request.setProveedor(proveedor);
		request.setFecha(LocalDateTime.now());

		for (CompraArticulo detalle : request.getDetalles()) {

			Articulo articulo = artRepository.findById(detalle.getArticulo().getId()).orElseThrow(
					() -> new RuntimeException("Artículo no encontrado con ID: " + detalle.getArticulo().getId()));

			detalle.setArticulo(articulo);
			detalle.setCompra(request);
			
			// sumamos la cantidad de articulos en stock
		    articulo.setCantidad(articulo.getCantidad() + detalle.getCantidad());
		    artRepository.save(articulo);
		}

		return compraRepository.save(request);
	}

	public List<Compra> listar() {
		return compraRepository.findAll();
	}

	public Compra get(Integer id) {
		return compraRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Compra no encontrada con ID: " + id));
	}

	public Compra actualizar(Integer id, Compra request) {
		Compra comprtaExiste = compraRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Compra no encontrada con ID: " + id));

		Proveedor proveedor = proveedorRepository.findById(request.getProveedor().getId()).orElseThrow(
				() -> new RuntimeException("Cliente no encontrado con ID: " + request.getProveedor().getId()));

		comprtaExiste.setProveedor(proveedor);
		comprtaExiste.setFecha(LocalDateTime.now());
		comprtaExiste.setValor(request.getValor());

		// Primero limpiar los detalles antiguos para evitar inconsistencias
		if (comprtaExiste.getDetalles() != null) {
			comprtaExiste.getDetalles().clear();
		} else {
			comprtaExiste.setDetalles(new ArrayList<>());
		}

		for (CompraArticulo detalle : request.getDetalles()) {
			Articulo articulo = artRepository.findById(detalle.getArticulo().getId()).orElseThrow(
					() -> new RuntimeException("Artículo no encontrado con ID: " + detalle.getArticulo().getId()));

			detalle.setArticulo(articulo);
			detalle.setCompra(comprtaExiste);

			comprtaExiste.getDetalles().add(detalle);
			
		    articulo.setCantidad(articulo.getCantidad() + detalle.getCantidad());
		    artRepository.save(articulo);
		}

		return compraRepository.save(comprtaExiste);
	}

	public boolean eliminar(Integer id) {
		if (compraRepository.existsById(id)) {
			compraRepository.deleteById(id);
			return true;
		}
		return false;
	}


}
