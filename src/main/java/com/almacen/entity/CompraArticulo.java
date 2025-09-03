package com.almacen.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name="compraarticulo")
@Data
public class CompraArticulo {
	

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Integer id;

	@NotNull(message = "La compra es obligatoria")
	@ManyToOne
	@JoinColumn(name = "compra", referencedColumnName = "id")
	@JsonBackReference
	private Compra compra;
	
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
