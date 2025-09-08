package com.almacen.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.almacen.entity.Cliente;
import com.almacen.respose.ApiResponseClass;
import com.almacen.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/cliente")
@SecurityRequirement(name = "bearerAuth")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@Operation(summary = "Guardar un cliente", description = "Guarda los archivos de un cliente.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Cliente guardado correctamente"),
			@ApiResponse(responseCode = "400", description = "Error de validación en los datos"),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor") 
	})
	@PostMapping
	public ResponseEntity<ApiResponseClass<?>> guardar(@Valid @RequestBody Cliente request,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String errorMsg = bindingResult.getFieldError().getDefaultMessage();
			return ResponseEntity.badRequest()
					.body(new ApiResponseClass<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
		}

		try {
			Cliente creado = clienteService.guardar(request);
			return ResponseEntity
					.ok(new ApiResponseClass<>("Cliente creado correctamente.", HttpStatus.OK.value(), creado));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponseClass<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

	// Listar clientes
	@Operation(summary = "Listar clientes", description = "Obtiene una lista de todos los clientes")
	@ApiResponses(value = {
	@ApiResponse(responseCode = "200", description = "Clientes obtenidos correctamente"),
	@ApiResponse(responseCode = "500", description = "Error interno del servidor")
	})
	@GetMapping
	public ResponseEntity<ApiResponseClass<?>> listar() {
		List<Cliente> cliente = clienteService.listar();
		return ResponseEntity
				.ok(new ApiResponseClass<>("Listado obtenido correctamente.", HttpStatus.OK.value(), cliente));
	}

	// Obtener cliente por ID
	@Operation(summary="Obtener cliente por ID",description= "Obtiene los datos de un cliente con su ID")
	@ApiResponses(value = {
	@ApiResponse(responseCode = "200", description = "Cliente encontrado"),
	@ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
	@ApiResponse(responseCode = "500", description = "Error interno del servidor")
	})
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponseClass<?>> get(@PathVariable Integer id) {
		Cliente cliente = clienteService.get(id);
		if (cliente != null) {
			return ResponseEntity.ok(new ApiResponseClass<>("Cliente encontrado.", HttpStatus.OK.value(), cliente));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponseClass<>("Cliente no encontrado.", HttpStatus.NOT_FOUND.value(), null));
		}
	}

	// Actualizar cliente
	@Operation(summary = "Actualizar cliente", description = "Actualiza los datos de un cliente")
	@ApiResponses(value = {
	@ApiResponse(responseCode = "200", description = "Cliente actualizado correctamente"),
	@ApiResponse(responseCode = "400", description = "Error de validación en los datos"),
	@ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
	@ApiResponse(responseCode = "500", description = "Error interno del servidor")
	})
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponseClass<?>> actualizar(@PathVariable Integer id, @Valid @RequestBody Cliente request,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String errorMsg = bindingResult.getFieldError().getDefaultMessage();
			return ResponseEntity.badRequest()
					.body(new ApiResponseClass<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
		}

		Cliente actualizado = clienteService.actualizar(id, request);
		if (actualizado != null) {
			return ResponseEntity
					
					
					
					
					
					
					
					
					
					
					
					
					
					.ok(new ApiResponseClass<>("Cliente actualizado.", HttpStatus.OK.value(), actualizado));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseClass<>(
					"Cliente no encontrado para actualizar.", HttpStatus.NOT_FOUND.value(), null));
		}
	}

	@Operation(summary = "Eliminar cliente", description = "Elimina un cliente según su ID")
	@ApiResponses(value = {
	@ApiResponse(responseCode = "200", description = "Cliente eliminado correctamente"),
	@ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
	@ApiResponse(responseCode = "500", description = "Error interno del servidor")
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponseClass<?>> eliminar(@PathVariable Integer id) {

		boolean eliminado = clienteService.eliminar(id);
		if (eliminado) {
			return ResponseEntity.ok(new ApiResponseClass<>("Cliente eliminado.", HttpStatus.OK.value(), null));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new ApiResponseClass<>("Cliente no encontrado para eliminar.", HttpStatus.NOT_FOUND.value(), null));
		}

	}

}
