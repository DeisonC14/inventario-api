package com.almacen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.almacen.entity.Caracteristica;
import com.almacen.respose.ApiResponseClass;
import com.almacen.service.CaracteristicaService;

import jakarta.validation.Valid;

@CrossOrigin(origins =  "http://localhost:3000")
@RestController
@RequestMapping("api/caracteristica")
public class CaracteristicaController {
	
	@Autowired
	private CaracteristicaService caracteristicaService;

	@PostMapping
	public ResponseEntity<ApiResponseClass<?>> guardar(@Valid @RequestBody Caracteristica request, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String errorMsg = bindingResult.getFieldError().getDefaultMessage();
			return ResponseEntity.badRequest().body(new ApiResponseClass<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
		}

		try {
			Caracteristica creado = caracteristicaService.guardar(request);
			return ResponseEntity.ok(new ApiResponseClass<>("Caracteristica creado correctamente.", HttpStatus.OK.value(), creado));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				    .body(new ApiResponseClass<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

	// Listar médicos
	@GetMapping
	public ResponseEntity<ApiResponseClass<?>> listar() {
		List<Caracteristica> caracteristica = caracteristicaService.listar();
		return ResponseEntity.ok(new ApiResponseClass<>("Listado obtenido correctamente.", HttpStatus.OK.value(), caracteristica));
	}

	// Obtener Caracteristica por ID
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponseClass<?>> get(@PathVariable Integer id) {
		Caracteristica caracteristica = caracteristicaService.get(id);
		if (caracteristica != null) {
			return ResponseEntity.ok(new ApiResponseClass<>("Categoria encontrado.", HttpStatus.OK.value(), caracteristica));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponseClass<>("Caracteristica no encontrado.", HttpStatus.NOT_FOUND.value(), null));
		}
	}

	// Actualizar caracteristica
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponseClass<?>> actualizar(@PathVariable Integer id, @Valid @RequestBody Caracteristica request,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String errorMsg = bindingResult.getFieldError().getDefaultMessage();
			return ResponseEntity.badRequest().body(new ApiResponseClass<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
		}
		
		Caracteristica actualizado = caracteristicaService.actualizar(id,request);
		if (actualizado != null) {
			return ResponseEntity.ok(new ApiResponseClass<>("Caracteristica actualizado.", HttpStatus.OK.value(), actualizado));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new ApiResponseClass<>("Caracteristica no encontrado para actualizar.", HttpStatus.NOT_FOUND.value(), null));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponseClass<?>> eliminar(@PathVariable Integer id) {

		boolean eliminado = caracteristicaService.eliminar(id);
		if (eliminado) {
			return ResponseEntity.ok(new ApiResponseClass<>("Categoria eliminado.", HttpStatus.OK.value(), null));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new ApiResponseClass<>("Categoria no encontrado para eliminar.", HttpStatus.NOT_FOUND.value(), null));
		}

	}

}