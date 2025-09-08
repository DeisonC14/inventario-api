package com.almacen.respose;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponseClass<T> {
    private String mensaje;
    private int status;
    private T data;
}