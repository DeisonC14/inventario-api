package com.almacen.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name="compra")
@Data
public class Compra {
	

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Integer id;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime fecha;
	
	@NotNull(message = "El valor es obligatorio")
	private Integer valor;
	
	@NotNull(message = "El cliente es obligatoria")
    @ManyToOne
    @JoinColumn(name = "proveedor", referencedColumnName = "id")
    private Proveedor proveedor;
    
	@OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<CompraArticulo> detalles = new ArrayList<>();
	

	
	
}
