package com.almacen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.almacen.entity.Articulo;
import com.almacen.entity.ArticuloCaracteristica;
import com.almacen.entity.Categoria;
import com.almacen.entity.VentaArticulo;
import com.almacen.repository.ArticuloRepository;
import com.almacen.repository.CategoriaRepository;

@Service
public class ArticuloService {

	@Autowired
	private ArticuloRepository articuloRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	public Articulo guardar(Articulo request) {

		Categoria categoria = categoriaRepository.findById(request.getCategoria().getId()).orElseThrow(
				() -> new RuntimeException("Categoria no encontrada con ID: " + request.getCategoria().getId()));

		request.setCategoria(categoria);

		return articuloRepository.save(request);
	}

	public List<Articulo> listar() {
		return articuloRepository.findAll();
	}

	public Articulo get(Integer id) {
		return articuloRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Articulo no encontrado con ID: " + id));
	}

	// Actualizar
	public Articulo actualizar(Integer id, Articulo request) {
		Articulo actual = articuloRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Articulo no encontrado con ID: " + id));

		Categoria categoria = categoriaRepository.findById(request.getCategoria().getId()).orElseThrow(
				() -> new RuntimeException("Categoria no encontrada con ID: " + request.getCategoria().getId()));

		actual.setCategoria(categoria);
		actual.setNombre(request.getNombre());
		actual.setDescripcion(request.getDescripcion());
		actual.setCantidad(request.getCantidad());
		actual.setEstado(request.getEstado());

		return articuloRepository.save(actual);
	}

	// Eliminar
	public boolean eliminar(Integer id) {
		if (articuloRepository.existsById(id)) {
			articuloRepository.deleteById(id);
			return true;
		}
		return false;
	}

	// Devolver las caracteristicas de un articulo
	public List<ArticuloCaracteristica> obtenerCaracteristicas(Integer idArticulo) {
		Articulo articulo = articuloRepository.findById(idArticulo)
				.orElseThrow(() -> new RuntimeException("Articulo  no encontrado"));

		return articulo.getCaracteristicas();
	}

	// Validar stock
	public void validarStockDisponible(List<VentaArticulo> detalles) {
		for (VentaArticulo detalle : detalles) {
			Articulo articulo = articuloRepository.findById(detalle.getArticulo().getId()).orElseThrow(
					() -> new RuntimeException("Artículo no encontrado con ID: " + detalle.getArticulo().getId()));

			if (articulo.getCantidad() < detalle.getCantidad()) {
				throw new RuntimeException("Stock insuficiente para el artículo con ID: " + articulo.getId());
			}
		}
	}

	public void descontarStock(List<VentaArticulo> detalles) {
		for (VentaArticulo detalle : detalles) {
			Articulo articulo = articuloRepository.findById(detalle.getArticulo().getId()).orElseThrow(
					() -> new RuntimeException("Artículo no encontrado con ID: " + detalle.getArticulo().getId()));

			articulo.setCantidad(articulo.getCantidad() - detalle.getCantidad());
			articuloRepository.save(articulo);
		}
	}

	// Restaurar se utiliza cuando se require actualizar una venta.
	public void restaurarStock(List<VentaArticulo> detalles) {
		for (VentaArticulo detalle : detalles) {
			Articulo articulo = articuloRepository.findById(detalle.getArticulo().getId()).orElseThrow(
					() -> new RuntimeException("Artículo no encontrado con ID: " + detalle.getArticulo().getId()));

			articulo.setCantidad(articulo.getCantidad() + detalle.getCantidad());
			articuloRepository.save(articulo);
		}
	}

}
