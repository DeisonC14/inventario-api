package com.almacen.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.almacen.entity.Cliente;
import com.almacen.repository.ClienteRepository;

@Service
public class ClienteService {
	
	 @Autowired
	 private ClienteRepository clienteRepository;
	
	 public Cliente guardar(Cliente request) {			
			return clienteRepository.save(request);		
	}
	    
    public List<Cliente> listar(){
		return clienteRepository.findAll();
	}
	
	public Cliente get(Integer id) {
		return clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));
	}
	
	public Cliente actualizar(Integer id,Cliente request) {
	   Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));    
      
	   cliente.setNombre(request.getNombre());
	   cliente.setDireccion(request.getDireccion());
	   cliente.setTelefono(request.getTelefono());
	   cliente.setCorreo(request.getCorreo());
	   return clienteRepository.save(cliente);
	}
	
	public boolean eliminar(Integer id) {
        if (clienteRepository.existsById(id)) {
        	clienteRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
