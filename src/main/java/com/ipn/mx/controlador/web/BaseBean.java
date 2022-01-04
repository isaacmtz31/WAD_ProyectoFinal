package com.ipn.mx.controlador.web;

import java.io.Serializable;

/**
 *
 * @author Isaac
 */
public class BaseBean implements Serializable{
    
    protected static final String ACT_CREATE = "CREAR";
    protected static final String ACT_UPDATE = "ACTUALIZAR";
    protected static final String ACT_DELETE = "ELIMINAR";    

    protected String action;
    
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
    
    public boolean is_create_mode(){
        if (action != null) {
            return action.equals(ACT_CREATE);                   
        }
        return false;
    }
    
    public boolean is_update_mode(){
        if (action != null) {
            return action.equals(ACT_UPDATE);                   
        }
        return false;
    }
    
}
