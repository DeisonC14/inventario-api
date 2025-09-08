package com.almacen.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.almacen.entity.Caracteristica;
import com.almacen.repository.CaracteristicaRepository;

@Service
public class CaracteristicaService {
	

	 @Autowired
	 private CaracteristicaRepository caracteristicaRepository;
	
	 public Caracteristica guardar(Caracteristica request) {			
			return caracteristicaRepository.save(request);		
	}
	    
   public List<Caracteristica> listar(){
		return caracteristicaRepository.findAll();
	}
	
	public Caracteristica get(Integer id) {
		return caracteristicaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Caracteristica no encontrada con ID: " + id));
	}
	
	public Caracteristica actualizar(Integer id,Caracteristica request) {
		Caracteristica actual = caracteristicaRepository.findById(id)
			   .orElseThrow(() -> new RuntimeException("Caracteristica no encontrada con ID: " + id));    
     
		actual.setDescripcion(request.getDescripcion());
	   return caracteristicaRepository.save(actual);
	}
	
	public boolean eliminar(Integer id) {
       if (caracteristicaRepository.existsById(id)) {
    	   caracteristicaRepository.deleteById(id);
           return true;
       }
       return false;
   }

}
