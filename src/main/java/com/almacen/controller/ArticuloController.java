package com.almacen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.almacen.entity.Articulo;
import com.almacen.entity.ArticuloCaracteristica;
import com.almacen.entity.Caracteristica;
import com.almacen.respose.ApiResponseClass;
import com.almacen.service.ArticuloCaracteristicaService;
import com.almacen.service.ArticuloService;

import jakarta.validation.Valid;

@CrossOrigin(origins =  "http://localhost:3000")
@RestController
@RequestMapping("api/articulo")
public class ArticuloController {
	
	@Autowired
	private ArticuloService articuloService;
	
	@Autowired
	private ArticuloCaracteristicaService  artCarService;

	@PostMapping
	public ResponseEntity<ApiResponseClass<?>> guardar(@Valid @RequestBody Articulo request, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String errorMsg = bindingResult.getFieldError().getDefaultMessage();
			return ResponseEntity.badRequest().body(new ApiResponseClass<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
		}

		try {
			Articulo creado = articuloService.guardar(request);
			return ResponseEntity.ok(new ApiResponseClass<>("Articulo creado correctamente.", HttpStatus.OK.value(), creado));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				    .body(new ApiResponseClass<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

	// Listar médicos
	@GetMapping
	public ResponseEntity<ApiResponseClass<?>> listar() {
		List<Articulo> articulos = articuloService.listar();
		return ResponseEntity.ok(new ApiResponseClass<>("Listado obtenido correctamente.", HttpStatus.OK.value(), articulos));
	}

	// Obtener médico por ID
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponseClass<?>> get(@PathVariable Integer id) {
		Articulo articulo = articuloService.get(id);
		if (articulo != null) {
			return ResponseEntity.ok(new ApiResponseClass<>("Articulo encontrado.", HttpStatus.OK.value(), articulo));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponseClass<>("Articulo no encontrado.", HttpStatus.NOT_FOUND.value(), null));
		}
	}

	// Actualizar médico
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponseClass<?>> actualizar(@PathVariable Integer id, @Valid @RequestBody Articulo request,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String errorMsg = bindingResult.getFieldError().getDefaultMessage();
			return ResponseEntity.badRequest().body(new ApiResponseClass<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
		}
		
		Articulo actualizado = articuloService.actualizar(id,request);
		if (actualizado != null) {
			return ResponseEntity.ok(new ApiResponseClass<>("Articulo actualizado.", HttpStatus.OK.value(), actualizado));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new ApiResponseClass<>("Articulo no encontrado para actualizar.", HttpStatus.NOT_FOUND.value(), null));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponseClass<?>> eliminar(@PathVariable Integer id) {

		boolean eliminado = articuloService.eliminar(id);
		if (eliminado) {
			return ResponseEntity.ok(new ApiResponseClass<>("Articulo eliminado.", HttpStatus.OK.value(), null));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new ApiResponseClass<>("Articulo no encontrado para eliminar.", HttpStatus.NOT_FOUND.value(), null));
		}

	}
	
	
	@GetMapping("/{id}/caracteristicas")
    public ResponseEntity<List<ArticuloCaracteristica>> getCaracteristicas(@PathVariable Integer id) {
        List<ArticuloCaracteristica> caracteristicas = articuloService.obtenerCaracteristicas(id);
        return ResponseEntity.ok(caracteristicas);
    }

}
