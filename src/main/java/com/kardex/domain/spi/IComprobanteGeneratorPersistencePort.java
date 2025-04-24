package com.kardex.domain.spi;

public interface IComprobanteGeneratorPersistencePort {

    void generarComprobante(String nombre, String monto, String idTransaccion, String fecha, String archivoDestino);
}
