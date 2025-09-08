package com.almacen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.almacen.entity.ArticuloCaracteristica;
import com.almacen.respose.ApiResponseClass;
import com.almacen.service.ArticuloCaracteristicaService;

import jakarta.validation.Valid;

@CrossOrigin(origins =  "http://localhost:3000")
@RestController
@RequestMapping("api/articuloCaracteristica")
public class ArticuloCaracteristicaController {

	@Autowired
	private ArticuloCaracteristicaService artCarService;
	
	@PostMapping
	public ResponseEntity<ApiResponseClass<?>> guardar(@Valid @RequestBody ArticuloCaracteristica request, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String errorMsg = bindingResult.getFieldError().getDefaultMessage();
			return ResponseEntity.badRequest().body(new ApiResponseClass<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
		}

		try {
			ArticuloCaracteristica creado = artCarService.guardar(request);
			return ResponseEntity.ok(new ApiResponseClass<>("Registro creado correctamente.", HttpStatus.OK.value(), creado));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				    .body(new ApiResponseClass<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponseClass<?>> actualizar(@PathVariable Integer id, @Valid @RequestBody ArticuloCaracteristica request,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String errorMsg = bindingResult.getFieldError().getDefaultMessage();
			return ResponseEntity.badRequest().body(new ApiResponseClass<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
		}
		
		ArticuloCaracteristica actualizado = artCarService.actualizar(id,request);
		if (actualizado != null) {
			return ResponseEntity.ok(new ApiResponseClass<>("Registro actualizado.", HttpStatus.OK.value(), actualizado));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new ApiResponseClass<>("Registro no encontrado para actualizar.", HttpStatus.NOT_FOUND.value(), null));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponseClass<?>> eliminar(@PathVariable Integer id) {

		boolean eliminado = artCarService.eliminar(id);
		if (eliminado) {
			return ResponseEntity.ok(new ApiResponseClass<>("Registro eliminado.", HttpStatus.OK.value(), null));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new ApiResponseClass<>("Registro no encontrado para eliminar.", HttpStatus.NOT_FOUND.value(), null));
		}

	}
	
	 
}

