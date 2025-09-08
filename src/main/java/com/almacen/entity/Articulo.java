package com.almacen.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Table(name="articulo")
@Data
public class Articulo {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Integer id;
		
	@NotBlank(message = "El nombre es obligatorio")
	@Size(max=50, min= 2, message = "La descripción debe tener entre 2 y 25 caractéres")
	private String nombre;
	
	@NotBlank(message = "La descripción es obligatoria")
	@Size(max=25, min= 2, message = "El nombre debe tener entre 2 y 25 caractéres")
	private String descripcion;
	
	@Min(1)
	private Integer cantidad;
	
	@Min(1)
	private Integer estado;
	
	@NotNull(message = "La categoria es obligatoria")
    @ManyToOne
    @JoinColumn(name = "categoria", referencedColumnName = "id")
    private Categoria categoria;
	
	@OneToMany(mappedBy = "articulo", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<ArticuloCaracteristica> caracteristicas = new ArrayList<>();


}
