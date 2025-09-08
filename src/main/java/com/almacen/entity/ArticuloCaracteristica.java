package com.almacen.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name="articulo_caracteristica")
@Data
public class ArticuloCaracteristica {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull(message = "El articulo es obligatorio")
    @ManyToOne
    @JoinColumn(name = "articulo", referencedColumnName = "id")
	@JsonBackReference
    private Articulo articulo;
	
	@NotNull(message = "La caracteristica es obligatoria")
    @ManyToOne
    @JoinColumn(name = "caracteristica", referencedColumnName = "id")
    private Caracteristica caracteristica;
	
	@NotBlank(message = "El valor es obligatorio")
	private String valor;

}

