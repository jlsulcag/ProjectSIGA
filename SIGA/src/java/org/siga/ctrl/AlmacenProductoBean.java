
package org.siga.ctrl;

import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import org.siga.be.Almacen;
import org.siga.be.AlmacenProducto;
import org.siga.be.NotaEntrada;
import org.siga.be.Producto;
import org.siga.bl.AlmacenBl;
import org.siga.bl.AlmacenProductoBl;
import org.siga.bl.NotaIngresoBl;
import org.siga.util.MensajeView;

@ManagedBean
@ViewScoped
public class AlmacenProductoBean {
    @ManagedProperty(value = "#{almacenProducto}")
    private AlmacenProducto almacenProducto;
    @ManagedProperty(value = "#{almacenProductoBl}")
    private AlmacenProductoBl almacenProductoBl;
    @ManagedProperty(value = "#{notaEntrada}")
    private NotaEntrada notaEntrada;
    @ManagedProperty(value = "#{notaIngresoBl}")
    private NotaIngresoBl notaIngresoBl;
    @ManagedProperty(value = "#{producto}")
    private Producto producto;
    @ManagedProperty(value = "#{almacenBl}")
    private AlmacenBl almacenBl;
    
    private List<AlmacenProducto> listInventario;
    private String txtBusqueda;
    private List<SelectItem> selectOneItemsAlmacenProducto;
    private int invocador = -1;
    public static final int NOTA_SALIDA_DISTRIBUCION = 1, STOCK = 2;
    
    
    public AlmacenProductoBean() {
    }
    
    @PostConstruct
    public void inicio(){
        almacenProducto.setAlmacen(defaultAlmacen());
        //Este metodo lista  todos los productos por almacen, valido para el caso de stock
//        if(almacenProducto.getAlmacen().getIdalmacen() != 0){
//            listarProductos(almacenProducto.getAlmacen().getIdalmacen());
//        }        
    }
    
    public void buscarAlmacenProducto(){
        almacenProducto = almacenProductoBl.buscarxId(almacenProducto.getIdalmacenproducto());
    }
    
    public void listarRef(){
        setListInventario(almacenProductoBl.listarRef(txtBusqueda.toUpperCase(), almacenProducto.getAlmacen().getIdalmacen()));
    }

    public AlmacenProducto getAlmacenProducto() {
        return almacenProducto;
    }

    public void setAlmacenProducto(AlmacenProducto almacenProducto) {
        this.almacenProducto = almacenProducto;
    }

    public AlmacenProductoBl getAlmacenProductoBl() {
        return almacenProductoBl;
    }

    public void setAlmacenProductoBl(AlmacenProductoBl almacenProductoBl) {
        this.almacenProductoBl = almacenProductoBl;
    }

    public NotaEntrada getNotaEntrada() {
        return notaEntrada;
    }

    public void setNotaEntrada(NotaEntrada notaEntrada) {
        this.notaEntrada = notaEntrada;
    }

    public NotaIngresoBl getNotaIngresoBl() {
        return notaIngresoBl;
    }

    public void setNotaIngresoBl(NotaIngresoBl notaIngresoBl) {
        this.notaIngresoBl = notaIngresoBl;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    private Almacen defaultAlmacen() {
        return almacenBl.buscarXNombre("SEDE PRINCIPAL");
    }

    public AlmacenBl getAlmacenBl() {
        return almacenBl;
    }

    public void setAlmacenBl(AlmacenBl almacenBl) {
        this.almacenBl = almacenBl;
    }

    public List<AlmacenProducto> getListInventario() {
        return listInventario;
    }

    public void setListInventario(List<AlmacenProducto> listInventario) {
        this.listInventario = listInventario;
    }

    private void listarProductos(long idalmacen) {
        setListInventario(almacenProductoBl.listarXAlmacen(idalmacen));
    }

    public String getTxtBusqueda() {
        return txtBusqueda;
    }

    public void setTxtBusqueda(String txtBusqueda) {
        this.txtBusqueda = txtBusqueda;
    }

    public List<SelectItem> getSelectOneItemsAlmacenProducto() {
        this.selectOneItemsAlmacenProducto= new LinkedList<SelectItem>();
        for (AlmacenProducto obj : listarAlmacenProducto()) {
            this.setAlmacenProducto(obj);
            SelectItem selectItem = new SelectItem(almacenProducto.getIdalmacenproducto(), almacenProducto.getProducto().getDescripcion());
            this.selectOneItemsAlmacenProducto.add(selectItem);
        }
        return selectOneItemsAlmacenProducto;
    }

    public void setSelectOneItemsAlmacenProducto(List<SelectItem> selectOneItemsAlmacenProducto) {
        this.selectOneItemsAlmacenProducto = selectOneItemsAlmacenProducto;
    }

    private List<AlmacenProducto> listarAlmacenProducto() {
        return almacenProductoBl.listarPeps();
    }
    
    private List<AlmacenProducto> listarAlmacenProducto(long idalmacen) {
        return almacenProductoBl.listarPeps();
    }

    public int getInvocador() {
        return invocador;
    }

    public void setInvocador(int invocador) {
        this.invocador = invocador;
    }
    
}
