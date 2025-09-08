package com.almacen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.almacen.entity.Categoria;
import com.almacen.respose.ApiResponseClass;
import com.almacen.service.CategoriaService;

import jakarta.validation.Valid;

@CrossOrigin(origins =  "http://localhost:3000")
@RestController
@RequestMapping("api/categoria")
public class CategoriaController {
	
	@Autowired
	private CategoriaService categoriaService;

	@PostMapping
	public ResponseEntity<ApiResponseClass<?>> guardar(@Valid @RequestBody Categoria request, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String errorMsg = bindingResult.getFieldError().getDefaultMessage();
			return ResponseEntity.badRequest().body(new ApiResponseClass<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
		}

		try {
			Categoria creado = categoriaService.guardar(request);
			return ResponseEntity.ok(new ApiResponseClass<>("Categoria creado correctamente.", HttpStatus.OK.value(), creado));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				    .body(new ApiResponseClass<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

	// Listar médicos
	@GetMapping
	public ResponseEntity<ApiResponseClass<?>> listar() {
		List<Categoria> categorias = categoriaService.listar();
		return ResponseEntity.ok(new ApiResponseClass<>("Listado obtenido correctamente.", HttpStatus.OK.value(), categorias));
	}

	// Obtener médico por ID
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponseClass<?>> get(@PathVariable Integer id) {
		Categoria categoria = categoriaService.get(id);
		if (categoria != null) {
			return ResponseEntity.ok(new ApiResponseClass<>("Categoria encontrado.", HttpStatus.OK.value(), categoria));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponseClass<>("Categoria no encontrado.", HttpStatus.NOT_FOUND.value(), null));
		}
	}

	// Actualizar médico
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponseClass<?>> actualizar(@PathVariable Integer id, @Valid @RequestBody Categoria request,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String errorMsg = bindingResult.getFieldError().getDefaultMessage();
			return ResponseEntity.badRequest().body(new ApiResponseClass<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
		}
		
		Categoria actualizado = categoriaService.actualizar(id,request);
		if (actualizado != null) {
			return ResponseEntity.ok(new ApiResponseClass<>("Categoria actualizado.", HttpStatus.OK.value(), actualizado));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new ApiResponseClass<>("Categoria no encontrado para actualizar.", HttpStatus.NOT_FOUND.value(), null));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponseClass<?>> eliminar(@PathVariable Integer id) {

		boolean eliminado = categoriaService.eliminar(id);
		if (eliminado) {
			return ResponseEntity.ok(new ApiResponseClass<>("Categoria eliminado.", HttpStatus.OK.value(), null));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new ApiResponseClass<>("Categoria no encontrado para eliminar.", HttpStatus.NOT_FOUND.value(), null));
		}

	}

}
