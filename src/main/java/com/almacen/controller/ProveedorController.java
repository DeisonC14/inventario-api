package com.almacen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.almacen.entity.Proveedor;
import com.almacen.respose.ApiResponseClass;
import com.almacen.service.ProveedorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@CrossOrigin(origins =  "http://localhost:3000")
@RestController
@RequestMapping("api/proveedor")
@SecurityRequirement(name = "bearerAuth")
public class ProveedorController {
	
	@Autowired
	private ProveedorService proveedorService;

	@Operation(summary = "Guardar un proveedor", description = "Guarda los archivos de un proveedor.")
	@ApiResponses(value = {
	@ApiResponse(responseCode = "200", description = "Proveedor guardado correctamente"),
	@ApiResponse(responseCode = "400", description = "Error de validación en los datos"),
	@ApiResponse(responseCode = "500", description = "Error interno del servidor")
	})
	@PostMapping
	public ResponseEntity<ApiResponseClass<?>> guardar(@Valid @RequestBody Proveedor request, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String errorMsg = bindingResult.getFieldError().getDefaultMessage();
			return ResponseEntity.badRequest().body(new ApiResponseClass<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
		}

		try {
			Proveedor creado = proveedorService.guardar(request);
			return ResponseEntity.ok(new ApiResponseClass<>("Proveedor creado correctamente.", HttpStatus.OK.value(), creado));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				    .body(new ApiResponseClass<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

	// Listar proveedor
	@Operation(summary = "Listar proveedores", description = "Obtiene una lista de todos los proveedores")
	@ApiResponses(value = {
	@ApiResponse(responseCode = "200", description = "Proveedores obtenidos correctamente"),
	@ApiResponse(responseCode = "500", description = "Error interno del servidor")
	})
	@GetMapping
	public ResponseEntity<ApiResponseClass<?>> listar() {
		List<Proveedor> proveedor = proveedorService.listar();
		return ResponseEntity.ok(new ApiResponseClass<>("Listado obtenido correctamente.", HttpStatus.OK.value(), proveedor));
	}

	// Obtener proveedor por ID
	@Operation(summary="Obtener proveedor por ID",description= "Obtiene los datos de un proveedor con su ID")
	@ApiResponses(value = {
	@ApiResponse(responseCode = "200", description = "Proveedor encontrado"),
	@ApiResponse(responseCode = "404", description = "Proveedor no encontrado"),
	@ApiResponse(responseCode = "500", description = "Error interno del servidor")
	})
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponseClass<?>> get(@PathVariable Integer id) {
		Proveedor proveedor = proveedorService.get(id);
		if (proveedor != null) {
			return ResponseEntity.ok(new ApiResponseClass<>("Proveedor encontrado.", HttpStatus.OK.value(), proveedor));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponseClass<>("Proveedor no encontrado.", HttpStatus.NOT_FOUND.value(), null));
		}
	}

	// Actualizar proveedor
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponseClass<?>> actualizar(@PathVariable Integer id, @Valid @RequestBody Proveedor request,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String errorMsg = bindingResult.getFieldError().getDefaultMessage();
			return ResponseEntity.badRequest().body(new ApiResponseClass<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
		}
		
		Proveedor actualizado = proveedorService.actualizar(id,request);
		if (actualizado != null) {
			return ResponseEntity.ok(new ApiResponseClass<>("Proveedor actualizado.", HttpStatus.OK.value(), actualizado));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new ApiResponseClass<>("Proveedor no encontrado para actualizar.", HttpStatus.NOT_FOUND.value(), null));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponseClass<?>> eliminar(@PathVariable Integer id) {

		boolean eliminado = proveedorService.eliminar(id);
		if (eliminado) {
			return ResponseEntity.ok(new ApiResponseClass<>("Cliente eliminado.", HttpStatus.OK.value(), null));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new ApiResponseClass<>("Cliente no encontrado para eliminar.", HttpStatus.NOT_FOUND.value(), null));
		}

	}


}
