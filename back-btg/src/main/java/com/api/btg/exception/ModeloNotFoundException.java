package com.api.btg.exception;

public class ModeloNotFoundException extends RuntimeException {
    public ModeloNotFoundException(String mensaje){
        super(mensaje);
    }
}