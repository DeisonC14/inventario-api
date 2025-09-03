package com.almacen.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.almacen.entity.Articulo;
import com.almacen.entity.Cliente;
import com.almacen.entity.Venta;
import com.almacen.entity.VentaArticulo;
import com.almacen.repository.ArticuloRepository;
import com.almacen.repository.ClienteRepository;
import com.almacen.repository.VentaArticuloRepository;
import com.almacen.repository.VentaRepository;

import jakarta.transaction.Transactional;

@Service
public class VentaService {

	@Autowired
	private VentaRepository ventaRepository;

	@Autowired
	private VentaArticuloRepository ventaArtRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ArticuloRepository artRepository;
	
	@Autowired
	private ArticuloService articuloService;


	@Transactional
	public Venta guardar(Venta request) {
	    // Validar cliente
	    Cliente cliente = clienteRepository.findById(request.getCliente().getId())
	        .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + request.getCliente().getId()));
	    request.setCliente(cliente);
	    request.setFecha(LocalDateTime.now());

	    // Validar stock
	    articuloService.validarStockDisponible(request.getDetalles());

	    // Guaradar detalle de la venta
	    for (VentaArticulo detalle : request.getDetalles()) {
	        Articulo articulo = articuloService.get(detalle.getArticulo().getId());
	        detalle.setArticulo(articulo);
	        detalle.setVenta(request);
	    }

	    // Guardar venta
	    Venta ventaGuardada = ventaRepository.save(request);

	    // Descontar stock 
	    articuloService.descontarStock(request.getDetalles());

	    return ventaGuardada;
	}

	
	public List<Venta> listar() {
		return ventaRepository.findAll();
	}

	public Venta get(Integer id) {
		return ventaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));
	}

	@Transactional
	public Venta actualizar(Integer id, Venta request) {
	    Venta ventaExistente = ventaRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));

	    Cliente cliente = clienteRepository.findById(request.getCliente().getId())
	            .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + request.getCliente().getId()));
	    
	    ventaExistente.setCliente(cliente);
	    ventaExistente.setFecha(LocalDateTime.now());
	    ventaExistente.setValor(request.getValor());

	    //Restaurar stock de los artículos anteriores
	    if (ventaExistente.getDetalles() != null && !ventaExistente.getDetalles().isEmpty()) {
	        articuloService.restaurarStock(ventaExistente.getDetalles());
	        ventaExistente.getDetalles().clear();
	    } else {
	        ventaExistente.setDetalles(new ArrayList<>());
	    }

	    // Validar stock de los nuevos artículos
	    articuloService.validarStockDisponible(request.getDetalles());

	    // Agregar nuevos detalles a la venta
	    for (VentaArticulo nuevoDetalle : request.getDetalles()) {
	        Articulo articulo = articuloService.get(nuevoDetalle.getArticulo().getId());

	        nuevoDetalle.setArticulo(articulo);
	        nuevoDetalle.setVenta(ventaExistente);

	        ventaExistente.getDetalles().add(nuevoDetalle);
	    }

	    // Guardar la venta actualizada
	    Venta ventaActualizada = ventaRepository.save(ventaExistente);

	    // Descontar el nuevo stock
	    articuloService.descontarStock(request.getDetalles());

	    return ventaActualizada;
	}


	public boolean eliminar(Integer id) {
		if (ventaRepository.existsById(id)) {
			ventaRepository.deleteById(id);
			return true;
		}
		return false;
	}

}
