/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tareas.web;

import com.tareas.entidades.Tarea;
import com.tareas.entidades.Usuario;
import com.tareas.excepciones.TareaNotFoundException;
import com.tareas.servicio.TareasService;
import com.tareas.servicio.TareasServiceLocal;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author user
 */
@Named(value = "altaTareaMB")
@ViewScoped
public class AltaTareaManagedBean implements Serializable {

    private Tarea tareaNueva;
    private Usuario usuario;
    private TareasService service = new TareasService();
    
    @EJB
    private TareasServiceLocal tareasService;
    
    public AltaTareaManagedBean() {
        this.tareaNueva = new Tarea();
        this.tareaNueva.setUsuario(new Usuario());
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public Tarea getTareaNueva() {
        return tareaNueva;
    }

    public void setTareaNueva(Tarea tareaNueva) {
        this.tareaNueva = tareaNueva;
    }

    public TareasService getService() {
        return service;
    }

    public void setService(TareasService service) {
        this.service = service;
    }
    
    public void crearTarea2(){
        System.out.println("tarea:" + this.usuario);
        
    }
   
    public String CrearTarea(){
        FacesContext fc = FacesContext.getCurrentInstance();
        try {
            this.tareasService.altaTarea(this.tareaNueva);
        } catch (TareaNotFoundException ex) {
            fc.addMessage(null, new FacesMessage("No se ha creado" + ex.getMessage()));
        }
        return "tarea?faces-redirect=true";
    }
        
}
