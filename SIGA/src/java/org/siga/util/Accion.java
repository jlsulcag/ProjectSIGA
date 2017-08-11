package org.siga.util;

public interface Accion {
    public static String REGISTRO="REGISTRO",
                         ACTUALIZACION="ACTUALIZACION",
                         ELIMINACION="ELIMINACION",
                         LISTADO="LISTADO";    
    
    public void proceso();
    public String getAccion();    
    public long getRepuesta();   
}
