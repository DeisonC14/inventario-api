package com.almacen.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Entity
@Data
public class Proveedor {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Integer id;
	
	@NotBlank(message = "El nombre es obligatorio")
	@Size(max=50, min= 2, message = "El nombre debe tener entre 2 y 10 caractéres")
	private String nombre;
	

	@NotBlank(message = "La direccion es obligatoria")
	@Size(max=25, min= 2, message = "El nombre debe tener entre 2 y 10 caractéres")
	private String direccion;
	
	@NotBlank(message = "El teléfono es obligatorio.")
	@Pattern(regexp = "\\d+", message = "El teléfono debe ser numérico.")
	@Column(length = 20)
	private String telefono;
	
	@Email(message = "El formato de email no es correcto")
	@NotBlank(message = "El email es obligatorio")
	private String correo;
	

}
