/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.controlador.web;




import com.ipn.mx.entidades.Categoria;
import com.ipn.mx.entidades.Usuario;
import com.ipn.mx.entidades.dao.CategoriaDAO;
import com.ipn.mx.entidades.dao.UsuarioDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@ManagedBean(name = "usuarioMB")
@SessionScoped
public class UsuarioMB extends BaseBean implements Serializable {
    private final UsuarioDAO dao = new UsuarioDAO();
    private final CategoriaDAO catDAO = new CategoriaDAO();
    
    private Usuario dto;
    private List<Usuario> listaUsuarios;
    private List<Categoria> listaCategorias;
    
    /**
     * Creates a new instance of UsuarioMB
     */
    public UsuarioMB() {
    }

    public Usuario getDto() {
        return dto;
    }

    public void setDto(Usuario dto) {
        this.dto = dto;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public List<Categoria> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(List<Categoria> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }
    
    @PostConstruct
    public void init(){
        listaUsuarios = new ArrayList<>();
        listaUsuarios = dao.readAll();
        
        listaCategorias = new ArrayList<>();
        listaCategorias = catDAO.read_all();
    }
    
    public String prepareAdd(){
        dto = new Usuario();
        setAction(ACT_CREATE);
        return "/producto/usuarioForm?faces-redirect=true";
    }
    public String prepareUpdate(){
        setAction(ACT_UPDATE);
        return "/producto/usuarioForm?faces-redirect=true";
    }
    public String prepareIndex(){
        init();
        return "/producto/listadoUsuarios?faces-redirect=true";
    }
    public boolean validate(){
        boolean valido = true;
        //Aqui les toca a ustedes poner todas las reglas de validacion 
       
        return valido;
    }
    
    public String add(){
        boolean valido = validate();
        if(valido){
          dao.create(dto);
            if(valido){
                return prepareIndex();
            }else{
                return prepareAdd();
            }
        }
        return prepareAdd();
    }
    public String update(){
        boolean valido = validate();
        if(valido){
            dao.update(dto);
            if(valido){
                return prepareIndex();
            }else{
                return prepareUpdate();
            }
        }
        return prepareUpdate();
    }
    public String delete(){
        dao.delete(dto);
        return prepareIndex();
    }
    
    public void seleccionarUsuario(ActionEvent event){
        String claveSeleccionada = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap().get("claveSel");
        dto = new Usuario();
        dto.setUser_id(Integer.parseInt(claveSeleccionada));
        try{
            dto = dao.read(dto);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
