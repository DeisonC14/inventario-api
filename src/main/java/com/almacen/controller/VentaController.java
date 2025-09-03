package com.almacen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.almacen.entity.Venta;
import com.almacen.respose.ApiResponse;

import com.almacen.service.VentaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/venta")
public class VentaController {

	@Autowired
	private VentaService ventaService;

	@PostMapping
	public ResponseEntity<ApiResponse<?>> guardar(@Valid @RequestBody Venta request, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String errorMsg = bindingResult.getFieldError().getDefaultMessage();
			return ResponseEntity.badRequest().body(new ApiResponse<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
		}

		try {
			Venta creado = ventaService.guardar(request);
			return ResponseEntity.ok(new ApiResponse<>("Venta creada correctamente.", HttpStatus.OK.value(), creado));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				    .body(new ApiResponse<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

	// Listar ventas
	@GetMapping
	public ResponseEntity<ApiResponse<?>> listar() {
		List<Venta> venta = ventaService.listar();
		return ResponseEntity.ok(new ApiResponse<>("Listado obtenido correctamente.", HttpStatus.OK.value(), venta));
	}

	// Obtener cliente por ID
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<?>> get(@PathVariable Integer id) {
		Venta venta = ventaService.get(id);
		if (venta != null) {
			return ResponseEntity.ok(new ApiResponse<>("venta encontrado.", HttpStatus.OK.value(), venta));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse<>("venta no encontrado.", HttpStatus.NOT_FOUND.value(), null));
		}
	}

	// Actualizar cliente
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<?>> actualizar(@PathVariable Integer id, @Valid @RequestBody Venta request,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String errorMsg = bindingResult.getFieldError().getDefaultMessage();
			return ResponseEntity.badRequest().body(new ApiResponse<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
		}
		
		
		try {
			
			Venta actualizado = ventaService.actualizar(id, request);
			if (actualizado != null) {
				return ResponseEntity.ok(new ApiResponse<>("venta actualizado.", HttpStatus.OK.value(), actualizado));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
						new ApiResponse<>("venta no encontrado para actualizar.", HttpStatus.NOT_FOUND.value(), null));
			}
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				    .body(new ApiResponse<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}

		
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<?>> eliminar(@PathVariable Integer id) {

		boolean eliminado = ventaService.eliminar(id);
		if (eliminado) {
			return ResponseEntity.ok(new ApiResponse<>("venta eliminado.", HttpStatus.OK.value(), null));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new ApiResponse<>("venta no encontrado para eliminar.", HttpStatus.NOT_FOUND.value(), null));
		}

	}

}
