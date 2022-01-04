/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.controlador.web;

import com.ipn.mx.entidades.Categoria;
import com.ipn.mx.entidades.Gerente;
import com.ipn.mx.entidades.dao.CategoriaDAO;
import com.ipn.mx.entidades.dao.GerenteDAO;
import com.ipn.mx.utillerias.EnviarMail;
import com.ipn.mx.utillerias.SessionUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

/**
 *
 * @author darkdestiny
 */
@ManagedBean(name = "gerenteMB")
@SessionScoped
public class GerenteMB extends BaseBean implements Serializable {

    private final GerenteDAO dao = new GerenteDAO();
    private final CategoriaDAO catDAO = new CategoriaDAO();

    private Gerente dto;
    private List<Gerente> listaGerentes;
    private List<Categoria> listaCategorias;
    private String pwd;

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

	private String user;
    /**
     * Creates a new instance of GerenteMB
     */
    public GerenteMB() {
    }

    public Gerente getDto() {
        return dto;
    }

    public void setDto(Gerente dto) {
        this.dto = dto;
    }

    public List<Gerente> getListaGerentes() {
        listaGerentes.remove((Gerente)SessionUtils.getUserName());
        return listaGerentes;
    }

    public void setListaGerentes(List<Gerente> listaGerentes) {
        this.listaGerentes = listaGerentes;
    }

    public List<Categoria> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(List<Categoria> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }

    @PostConstruct
    public void init() {
        listaGerentes = new ArrayList<>();
        listaGerentes = dao.readAll();        

        listaCategorias = new ArrayList<>();
        listaCategorias = catDAO.read_all();
    }

    public String prepareAdd() {
        dto = new Gerente();
        setAction(ACT_CREATE);
        return "/producto/gerenteForm?faces-redirect=true";
    }
    
    public String prepareRegistro() {
        dto = new Gerente();        
        return "/gerenteRegistro?faces-redirect=true";
    }


    public String prepareUpdate() {
        setAction(ACT_UPDATE);
        return "/producto/gerenteForm?faces-redirect=true";
    }
    
    public String prepareMyUpdate(){
        setAction(ACT_UPDATE);
        dto = (Gerente)SessionUtils.getUserName();
        return "/producto/gerenteForm?faces-redirect=true";
    }

    public String prepareIndex() {
        init();
        return "/producto/listadoGerentes?faces-redirect=true";
    }

    public boolean validate() {
        boolean valido = true;
        //Aqui les toca a ustedes poner todas las reglas de validacion 

        return valido;
    }

    public String add() {
        boolean valido = validate();
        if (valido) {
            dao.create(dto);
            if (valido) {
                return prepareIndex();
            } else {
                return prepareAdd();
            }
        }
        return prepareAdd();
    }
    
    public String addRegistro(){        
        boolean valido = validate();
        if (valido) {
            dao.create(dto);
            if (valido) {
                EnviarMail m = new EnviarMail();
                m.send_mail(dto.getEmail(),"Creación de cuenta", "Hola " + dto.getNombreGerente()+ " tu cuenta ha sido generada con éxito y ya puedes hacer uso total de nuestra aplicación. Gracias.");
                return "/index.xhtml?faces-redirect=true";
            } else {
                return prepareRegistro();
            }
        }
        return prepareAdd();
    }

    public String update() {
        boolean valido = validate();
        if (valido) {
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
    
    public String delete_my_account(){
        dto = (Gerente)SessionUtils.getUserName();
        dao.delete(dto);
        logout();
        return "/index?faces-redirect=true";
    }

    public void seleccionarGerente(ActionEvent event) {
        String claveSeleccionada = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap().get("claveSel");
        dto = new Gerente();
        dto.setIdGerente(Long.parseLong(claveSeleccionada));
        try {
            dto = dao.read(dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String validateUsernamePassword() {
        dto = new Gerente();
        dto.setEmail(user);
        dto.setPassword(pwd)
                ;
        Gerente valido = dao.read_login(dto);
        if (valido != null) {
            HttpSession session = SessionUtils.getSession();
            session.setAttribute("username", valido);
            return "gerente";
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Incorrect Username and Passowrd",
                            "Please enter correct username and Password"));
            return "login";
        }
    }

    //logout event, invalidate session
    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return "login";
    }
}
