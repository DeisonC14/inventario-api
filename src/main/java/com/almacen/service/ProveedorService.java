package com.almacen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.almacen.entity.Proveedor;
import com.almacen.repository.ProveedorRepository;

@Service
public class ProveedorService {

	@Autowired
	private ProveedorRepository proveedorRepository;

	public Proveedor guardar(Proveedor request) {
		return proveedorRepository.save(request);
	}

	public List<Proveedor> listar() {
		return proveedorRepository.findAll();
	}

	public Proveedor get(Integer id) {
		return proveedorRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + id));
	}

	public Proveedor actualizar(Integer id, Proveedor request) {
		Proveedor cliente = proveedorRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + id));

		cliente.setNombre(request.getNombre());
		cliente.setDireccion(request.getDireccion());
		cliente.setTelefono(request.getTelefono());
		cliente.setCorreo(request.getCorreo());
		return proveedorRepository.save(cliente);
	}

	public boolean eliminar(Integer id) {
		if (proveedorRepository.existsById(id)) {
			proveedorRepository.deleteById(id);
			return true;
		}
		return false;
	}

}
