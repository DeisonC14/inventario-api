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
import com.almacen.respose.ApiResponse;
import com.almacen.service.ClienteService;


import jakarta.validation.Valid;

@CrossOrigin(origins =  "http://localhost:3000")
@RestController
@RequestMapping("api/cliente")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@PostMapping
	public ResponseEntity<ApiResponse<?>> guardar(@Valid @RequestBody Cliente request, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String errorMsg = bindingResult.getFieldError().getDefaultMessage();
			return ResponseEntity.badRequest().body(new ApiResponse<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
		}

		try {
			Cliente creado = clienteService.guardar(request);
			return ResponseEntity.ok(new ApiResponse<>("Cliente creado correctamente.", HttpStatus.OK.value(), creado));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				    .body(new ApiResponse<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

	// Listar clientes
	@GetMapping
	public ResponseEntity<ApiResponse<?>> listar() {
		List<Cliente> cliente = clienteService.listar();
		return ResponseEntity.ok(new ApiResponse<>("Listado obtenido correctamente.", HttpStatus.OK.value(), cliente));
	}

	// Obtener cliente por ID
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<?>> get(@PathVariable Integer id) {
		Cliente cliente = clienteService.get(id);
		if (cliente != null) {
			return ResponseEntity.ok(new ApiResponse<>("Medico encontrado.", HttpStatus.OK.value(), cliente));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse<>("Cliente no encontrado.", HttpStatus.NOT_FOUND.value(), null));
		}
	}

	// Actualizar cliente
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<?>> actualizar(@PathVariable Integer id, @Valid @RequestBody Cliente request,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String errorMsg = bindingResult.getFieldError().getDefaultMessage();
			return ResponseEntity.badRequest().body(new ApiResponse<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
		}
		
		Cliente actualizado = clienteService.actualizar(id,request);
		if (actualizado != null) {
			return ResponseEntity.ok(new ApiResponse<>("Cliente actualizado.", HttpStatus.OK.value(), actualizado));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new ApiResponse<>("Cliente no encontrado para actualizar.", HttpStatus.NOT_FOUND.value(), null));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<?>> eliminar(@PathVariable Integer id) {

		boolean eliminado = clienteService.eliminar(id);
		if (eliminado) {
			return ResponseEntity.ok(new ApiResponse<>("Cliente eliminado.", HttpStatus.OK.value(), null));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new ApiResponse<>("Cliente no encontrado para eliminar.", HttpStatus.NOT_FOUND.value(), null));
		}

	}

}
