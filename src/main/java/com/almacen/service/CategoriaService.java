package com.almacen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.almacen.entity.Categoria;
import com.almacen.repository.CategoriaRepository;


@Service
public class CategoriaService {
	
	 @Autowired
	 private CategoriaRepository categoriaRepository;
	
	 public Categoria guardar(Categoria request) {			
			return categoriaRepository.save(request);		
	}
	    
    public List<Categoria> listar(){
		return categoriaRepository.findAll();
	}
	
	public Categoria get(Integer id) {
		return categoriaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Categoria no encontrada con ID: " + id));
	}
	
	public Categoria actualizar(Integer id,Categoria request) {
		Categoria actual = categoriaRepository.findById(id)
			   .orElseThrow(() -> new RuntimeException("Categoria no encontrada con ID: " + id));    
      
		actual.setDescripcion(request.getDescripcion());
	   return categoriaRepository.save(actual);
	}
	
	public boolean eliminar(Integer id) {
        if (categoriaRepository.existsById(id)) {
        	categoriaRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
