package org.siga.util;

public abstract class Tarea implements Accion{
    private final String accion;
    private final long respuesta;
    
    public Tarea(String accion,long rsp) {
        this.accion = accion;
        this.respuesta = rsp;
    }
    @Override
    public String getAccion() {
        return accion;
    }    
    @Override
    public long getRepuesta() {
        return respuesta;
    }
}
