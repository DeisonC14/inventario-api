package com.almacen.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name="ventaarticulo")
@Data
public class VentaArticulo {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Integer id;

	@NotNull(message = "La venta es obligatoria")
	@ManyToOne
	@JoinColumn(name = "venta", referencedColumnName = "id")
	@JsonBackReference
	private Venta venta;
	
	@NotNull(message = "El articulo es obligatoria")
    @ManyToOne
    @JoinColumn(name = "articulo", referencedColumnName = "id")
    private Articulo articulo;
	  
	@NotNull(message = "El valor unitario es obligatorio")
	private Integer valor_unitario;
	
	@NotNull(message = "La cantidad es obligatorio")
	private Integer cantidad;
	
	@NotNull(message = "El valor es obligatorio")
	private Integer valor;


}
