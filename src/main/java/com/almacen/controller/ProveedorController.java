package com.almacen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.almacen.entity.Proveedor;
import com.almacen.respose.ApiResponse;
import com.almacen.service.ProveedorService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("api/proveedor")
public class ProveedorController {
	
	@Autowired
	private ProveedorService proveedorService;

	@PostMapping
	public ResponseEntity<ApiResponse<?>> guardar(@Valid @RequestBody Proveedor request, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String errorMsg = bindingResult.getFieldError().getDefaultMessage();
			return ResponseEntity.badRequest().body(new ApiResponse<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
		}

		try {
			Proveedor creado = proveedorService.guardar(request);
			return ResponseEntity.ok(new ApiResponse<>("Proveedor creado correctamente.", HttpStatus.OK.value(), creado));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				    .body(new ApiResponse<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

	// Listar médicos
	@GetMapping
	public ResponseEntity<ApiResponse<?>> listar() {
		List<Proveedor> proveedor = proveedorService.listar();
		return ResponseEntity.ok(new ApiResponse<>("Listado obtenido correctamente.", HttpStatus.OK.value(), proveedor));
	}

	// Obtener médico por ID
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<?>> get(@PathVariable Integer id) {
		Proveedor proveedor = proveedorService.get(id);
		if (proveedor != null) {
			return ResponseEntity.ok(new ApiResponse<>("Proveedor encontrado.", HttpStatus.OK.value(), proveedor));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse<>("Proveedor no encontrado.", HttpStatus.NOT_FOUND.value(), null));
		}
	}

	// Actualizar médico
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<?>> actualizar(@PathVariable Integer id, @Valid @RequestBody Proveedor request,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String errorMsg = bindingResult.getFieldError().getDefaultMessage();
			return ResponseEntity.badRequest().body(new ApiResponse<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
		}
		
		Proveedor actualizado = proveedorService.actualizar(id,request);
		if (actualizado != null) {
			return ResponseEntity.ok(new ApiResponse<>("Proveedor actualizado.", HttpStatus.OK.value(), actualizado));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new ApiResponse<>("Proveedor no encontrado para actualizar.", HttpStatus.NOT_FOUND.value(), null));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<?>> eliminar(@PathVariable Integer id) {

		boolean eliminado = proveedorService.eliminar(id);
		if (eliminado) {
			return ResponseEntity.ok(new ApiResponse<>("Cliente eliminado.", HttpStatus.OK.value(), null));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new ApiResponse<>("Cliente no encontrado para eliminar.", HttpStatus.NOT_FOUND.value(), null));
		}

	}


}
