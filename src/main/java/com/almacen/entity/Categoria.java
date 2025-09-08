package com.almacen.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name="categoria")
@Data
public class Categoria {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Integer id;
	
	@NotBlank(message = "La descripcion es obligatoriao")
	@Size(max=50, min= 2, message = "El nombre debe tener entre 2 y 50 caractéres")
	private String descripcion;

}
