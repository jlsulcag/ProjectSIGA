package org.siga.be;
// Generated 07/08/2017 05:08:45 PM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Component;

/**
 * Producto generated by hbm2java
 */
@Component
public class Producto  implements java.io.Serializable {

     private long idproducto;
     private Clase clase;
     private UnidadMedida unidadMedida;
     private String descripcion;
     private String codigo;
     private int stockMinimo;
     private int stockMaximo;
     private String estado;
     private Date fechaReg;
     private int fraccion;
     private Familia familia;
     private Set<OrdenCompraDetalle> ordenCompraDetalles = new HashSet<OrdenCompraDetalle>(0);
     private Set<NotaEntradaDetalle> notaEntradaDetalles = new HashSet<NotaEntradaDetalle>(0);
     private Set<AlmacenProducto> almacenProductos = new HashSet<AlmacenProducto>(0);

    public Producto() {
        this.idproducto = 0;
        this.clase = new Clase();
        this.unidadMedida = new UnidadMedida();
        this.familia = new Familia();
    }

	
    public Producto(long idproducto, UnidadMedida unidadMedida, String descripcion, String codigo, int stockMinimo, int stockMaximo, String estado, Date fechaReg) {
        this.idproducto = idproducto;
        this.unidadMedida = unidadMedida;
        this.descripcion = descripcion;
        this.codigo = codigo;
        this.stockMinimo = stockMinimo;
        this.stockMaximo = stockMaximo;
        this.estado = estado;
        this.fechaReg = fechaReg;
    }
    public Producto(long idproducto, Clase clase, UnidadMedida unidadMedida, String descripcion, String codigo, int stockMinimo, int stockMaximo, String estado, Date fechaReg, Set<OrdenCompraDetalle> ordenCompraDetalles, Set<NotaEntradaDetalle> notaEntradaDetalles) {
       this.idproducto = idproducto;
       this.clase = clase;
       this.unidadMedida = unidadMedida;
       this.descripcion = descripcion;
       this.codigo = codigo;
       this.stockMinimo = stockMinimo;
       this.stockMaximo = stockMaximo;
       this.estado = estado;
       this.fechaReg = fechaReg;
       this.ordenCompraDetalles = ordenCompraDetalles;
       this.notaEntradaDetalles = notaEntradaDetalles;
    }
   
    public long getIdproducto() {
        return this.idproducto;
    }
    
    public void setIdproducto(long idproducto) {
        this.idproducto = idproducto;
    }
    public Clase getClase() {
        return this.clase;
    }
    
    public void setClase(Clase clase) {
        this.clase = clase;
    }
    public UnidadMedida getUnidadMedida() {
        return this.unidadMedida;
    }
    
    public void setUnidadMedida(UnidadMedida unidadMedida) {
        this.unidadMedida = unidadMedida;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getCodigo() {
        return this.codigo;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public int getStockMinimo() {
        return this.stockMinimo;
    }
    
    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }
    public int getStockMaximo() {
        return this.stockMaximo;
    }
    
    public void setStockMaximo(int stockMaximo) {
        this.stockMaximo = stockMaximo;
    }
    public String getEstado() {
        return this.estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public Date getFechaReg() {
        return this.fechaReg;
    }
    
    public void setFechaReg(Date fechaReg) {
        this.fechaReg = fechaReg;
    }
    public Set<OrdenCompraDetalle> getOrdenCompraDetalles() {
        return this.ordenCompraDetalles;
    }
    
    public void setOrdenCompraDetalles(Set<OrdenCompraDetalle> ordenCompraDetalles) {
        this.ordenCompraDetalles = ordenCompraDetalles;
    }
    public Set<NotaEntradaDetalle> getNotaEntradaDetalles() {
        return this.notaEntradaDetalles;
    }
    
    public void setNotaEntradaDetalles(Set<NotaEntradaDetalle> notaEntradaDetalles) {
        this.notaEntradaDetalles = notaEntradaDetalles;
    }

    public int getFraccion() {
        return fraccion;
    }

    public void setFraccion(int fraccion) {
        this.fraccion = fraccion;
    }

    public Familia getFamilia() {
        return familia;
    }

    public void setFamilia(Familia familia) {
        this.familia = familia;
    }

    public Set<AlmacenProducto> getAlmacenProductos() {
        return almacenProductos;
    }

    public void setAlmacenProductos(Set<AlmacenProducto> almacenProductos) {
        this.almacenProductos = almacenProductos;
    }




}


