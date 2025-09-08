package com.almacen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.almacen.entity.Venta;
import com.almacen.respose.ApiResponseClass;

import com.almacen.service.VentaService;

import jakarta.validation.Valid;

@CrossOrigin(origins =  "http://localhost:3000")
@RestController
@RequestMapping("api/venta")
public class VentaController {

	@Autowired
	private VentaService ventaService;

	@PostMapping
	public ResponseEntity<ApiResponseClass<?>> guardar(@Valid @RequestBody Venta request, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String errorMsg = bindingResult.getFieldError().getDefaultMessage();
			return ResponseEntity.badRequest().body(new ApiResponseClass<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
		}

		try {
			Venta creado = ventaService.guardar(request);
			return ResponseEntity.ok(new ApiResponseClass<>("Venta creada correctamente.", HttpStatus.OK.value(), creado));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				    .body(new ApiResponseClass<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

	// Listar ventas
	@GetMapping
	public ResponseEntity<ApiResponseClass<?>> listar() {
		List<Venta> venta = ventaService.listar();
		return ResponseEntity.ok(new ApiResponseClass<>("Listado obtenido correctamente.", HttpStatus.OK.value(), venta));
	}

	// Obtener cliente por ID
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponseClass<?>> get(@PathVariable Integer id) {
		Venta venta = ventaService.get(id);
		if (venta != null) {
			return ResponseEntity.ok(new ApiResponseClass<>("venta encontrado.", HttpStatus.OK.value(), venta));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponseClass<>("venta no encontrado.", HttpStatus.NOT_FOUND.value(), null));
		}
	}

	// Actualizar cliente
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponseClass<?>> actualizar(@PathVariable Integer id, @Valid @RequestBody Venta request,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String errorMsg = bindingResult.getFieldError().getDefaultMessage();
			return ResponseEntity.badRequest().body(new ApiResponseClass<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
		}
		
		
		try {
			
			Venta actualizado = ventaService.actualizar(id, request);
			if (actualizado != null) {
				return ResponseEntity.ok(new ApiResponseClass<>("venta actualizado.", HttpStatus.OK.value(), actualizado));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
						new ApiResponseClass<>("venta no encontrado para actualizar.", HttpStatus.NOT_FOUND.value(), null));
			}
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				    .body(new ApiResponseClass<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}

		
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponseClass<?>> eliminar(@PathVariable Integer id) {

		boolean eliminado = ventaService.eliminar(id);
		if (eliminado) {
			return ResponseEntity.ok(new ApiResponseClass<>("venta eliminado.", HttpStatus.OK.value(), null));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new ApiResponseClass<>("venta no encontrado para eliminar.", HttpStatus.NOT_FOUND.value(), null));
		}

	}

}
