package com.almacen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.almacen.entity.ArticuloCaracteristica;
import com.almacen.respose.ApiResponse;
import com.almacen.service.ArticuloCaracteristicaService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("api/articuloCaracteristica")
public class ArticuloCaracteristicaController {

	@Autowired
	private ArticuloCaracteristicaService artCarService;
	
	@PostMapping
	public ResponseEntity<ApiResponse<?>> guardar(@Valid @RequestBody ArticuloCaracteristica request, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String errorMsg = bindingResult.getFieldError().getDefaultMessage();
			return ResponseEntity.badRequest().body(new ApiResponse<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
		}

		try {
			ArticuloCaracteristica creado = artCarService.guardar(request);
			return ResponseEntity.ok(new ApiResponse<>("Registro creado correctamente.", HttpStatus.OK.value(), creado));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				    .body(new ApiResponse<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<?>> actualizar(@PathVariable Integer id, @Valid @RequestBody ArticuloCaracteristica request,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String errorMsg = bindingResult.getFieldError().getDefaultMessage();
			return ResponseEntity.badRequest().body(new ApiResponse<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
		}
		
		ArticuloCaracteristica actualizado = artCarService.actualizar(id,request);
		if (actualizado != null) {
			return ResponseEntity.ok(new ApiResponse<>("Registro actualizado.", HttpStatus.OK.value(), actualizado));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new ApiResponse<>("Registro no encontrado para actualizar.", HttpStatus.NOT_FOUND.value(), null));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<?>> eliminar(@PathVariable Integer id) {

		boolean eliminado = artCarService.eliminar(id);
		if (eliminado) {
			return ResponseEntity.ok(new ApiResponse<>("Registro eliminado.", HttpStatus.OK.value(), null));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new ApiResponse<>("Registro no encontrado para eliminar.", HttpStatus.NOT_FOUND.value(), null));
		}

	}
	
	 
}

