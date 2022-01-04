/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.controlador.web;

import com.ipn.mx.entidades.Categoria;
import com.ipn.mx.entidades.Gerente;
import com.ipn.mx.entidades.Tienda;
import com.ipn.mx.entidades.dao.CategoriaDAO;
import com.ipn.mx.entidades.dao.GerenteDAO;
import com.ipn.mx.entidades.dao.TiendaDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author darkdestiny
 */
@ManagedBean(name = "tiendaMB")
@SessionScoped
public class TiendaMB extends BaseBean implements Serializable {

    private final TiendaDAO dao = new TiendaDAO();
    private final CategoriaDAO catDAO = new CategoriaDAO();
    private final GerenteDAO ger_dao = new GerenteDAO();

    private Tienda dto;
    private List<Tienda> listaTiendas;
    private List<Gerente> listaGerentes;

    public List<Gerente> getListaGerentes() {
        return listaGerentes;
    }

    public void setListaGerentes(List<Gerente> listaGerentes) {
        this.listaGerentes = listaGerentes;
    }

    /**
     * Creates a new instance of TiendaMB
     */
    public TiendaMB() {
    }

    public Tienda getDto() {
        return dto;
    }

    public void setDto(Tienda dto) {
        this.dto = dto;
    }

    public List<Tienda> getListaTiendas() {
        return listaTiendas;
    }

    public void setListaTiendas(List<Tienda> listaTiendas) {
        this.listaTiendas = listaTiendas;
    }


    @PostConstruct
    public void init() {
        listaTiendas = new ArrayList<>();
        listaTiendas = dao.readAll();
        listaGerentes = new ArrayList<>();
        listaGerentes = ger_dao.readAll();
    }

    public String prepareAdd() {
        dto = new Tienda();
        setAction(ACT_CREATE);
        return "/producto/tiendaForm?faces-redirect=true";
    }

    public String prepareUpdate() {
        setAction(ACT_UPDATE);
        return "/producto/tiendaForm?faces-redirect=true";
    }

    public String prepareProductosUnaTienda() {
        listaTiendas = dao.readAll();


        return "/producto/listadoTiendas?faces-redirect=true";
    }

    public String prepareIndex() {
        init();
        return "/producto/listadoTiendas?faces-redirect=true";
    }

    public boolean validate() {
        boolean valido = true;
        //Aqui les toca a ustedes poner todas las reglas de validacion 

        return valido;
    }

    public String add() {
        boolean valido = validate();
        if (valido) {

            Gerente g = new Gerente();
            g.setIdGerente(dto.getIdGer());
            g = ger_dao.read(g);
            dto.setIdGerente(g);
            dao.create(dto);
            if (valido) {
                return prepareIndex();
            } else {
                return prepareAdd();
            }
        }
        return prepareAdd();
    }

    public String update() {
        boolean valido = validate();
        if (valido) {
            Gerente g = new Gerente();
            g.setIdGerente(dto.getIdGer());
            g = ger_dao.read(g);
            dto.setIdGerente(g);            
            dao.update(dto);
            if (valido) {
                return prepareIndex();
            } else {
                return prepareUpdate();
            }
        }
        return prepareUpdate();
    }

    public String delete() {
        dao.delete(dto);
        return prepareIndex();
    }

    public void seleccionarTienda(ActionEvent event) {
        String claveSeleccionada = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap().get("claveSel");
        dto = new Tienda();
        dto.setIdTienda(Integer.parseInt(claveSeleccionada));
        try {
            dto = dao.read(dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
