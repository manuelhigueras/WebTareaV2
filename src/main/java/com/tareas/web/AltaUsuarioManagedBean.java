/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tareas.web;

import com.tareas.entidades.Usuario;
import com.tareas.excepciones.UsuarioNotFoundException;
import com.tareas.servicio.UsuarioServiceLocal;
import java.io.Serializable;
import java.util.Collection;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Manuel
 */
@Named(value = "usuario")
@ViewScoped
public class AltaUsuarioManagedBean implements Serializable{

    private Collection<Usuario> coleccionUsuarios;
    private Usuario usuarioEncontrado;
    
    @EJB
    private UsuarioServiceLocal usuarioService;
    
    private String emailABuscar;    
    
    public AltaUsuarioManagedBean() {
        System.out.println(".... ALTA USUARIO MANAGER BEAN");
        this.usuarioEncontrado = new Usuario();
    }

    public Collection<Usuario> getColeccionUsuarios() {
        return coleccionUsuarios;
    }

    public void setColeccionUsuarios(Collection<Usuario> coleccionUsuarios) {
        this.coleccionUsuarios = coleccionUsuarios;
    }

    public Usuario getUsuarioEncontrado() {
        return usuarioEncontrado;
    }

    public void setUsuarioEncontrado(Usuario usuarioEncontrado) {
        this.usuarioEncontrado = usuarioEncontrado;
    }

    public String getEmailABuscar() {
        return emailABuscar;
    }

    public void setEmailABuscar(String emailABuscar) {
        this.emailABuscar = emailABuscar;
    }
        
    public String CrearUsuario(){
        System.out.println("creando usuario " + this.usuarioEncontrado);
        this.usuarioEncontrado.setIdUsuario(1);
        FacesContext fc = FacesContext.getCurrentInstance();  
        try {
            this.usuarioService.alta(this.usuarioEncontrado);
        }
        catch(UsuarioNotFoundException ex){
            fc.addMessage(null, new FacesMessage("No se pudo modificar. " + ex.getMessage()));  
        }
        catch(Exception e){
            fc.addMessage(null, new FacesMessage("Error no controlado. " + e.getMessage())); 
            e.printStackTrace();
        }
        return "login?faces-redirect=true";
    }    
   
}
