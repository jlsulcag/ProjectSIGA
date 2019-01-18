/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siga.ctrl;

import java.util.LinkedList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import org.siga.be.Moneda;
import org.siga.bl.MonedaBl;

/**
 *
 * @author JSULCAG
 */
@ManagedBean
@ViewScoped
public class MonedaBean {

    @ManagedProperty(value = "#{monedaBl}")
    private MonedaBl monedaBl;
    @ManagedProperty(value = "#{moneda}")
    private Moneda moneda;
    
    private List<SelectItem> selectOneItemsMoneda;
    
    public MonedaBean() {
    }

    public MonedaBl getMonedaBl() {
        return monedaBl;
    }

    public void setMonedaBl(MonedaBl monedaBl) {
        this.monedaBl = monedaBl;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    public List<SelectItem> getSelectOneItemsMoneda() {
        this.selectOneItemsMoneda= new LinkedList<SelectItem>();
        for (Moneda obj : listar()) {
            this.setMoneda(obj);
            SelectItem selectItem = new SelectItem(moneda.getIdmoneda(), moneda.getDescripcion());
            this.selectOneItemsMoneda.add(selectItem);
        }
        return selectOneItemsMoneda;
    }

    public void setSelectOneItemsMoneda(List<SelectItem> selectOneItemsMoneda) {
        this.selectOneItemsMoneda = selectOneItemsMoneda;
    }

    private List<Moneda> listar() {
        return monedaBl.listar();
    }
    
}
