
package com.tareas.web;

import com.tareas.entidades.Tarea;
import com.tareas.entidades.Usuario;
import com.tareas.excepciones.TareaNotFoundException;
import com.tareas.servicio.TareasServiceLocal;
import java.io.Serializable;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

@Named(value = "gestionTareasMB")
@RequestScoped
//@ViewScoped
public class GestionTareasManagedBean implements Serializable{
    
    private Collection<Tarea> coleccionTareasTODO;
    private Collection<Tarea> coleccionTareaProgress;
    private Collection<Tarea> coleccionTareaDone;
    private boolean visible = false;
    private int idTarea;
    private Tarea tarea;
    private Tarea tareaAux;
    private Usuario user;
    
    @EJB
    private TareasServiceLocal tareasService;

    public GestionTareasManagedBean() {
        System.out.println(".... constuctor GestionTareasManagedBean");
        //this.idTarea = 0;
        this.tarea = new Tarea();
        this.user = new Usuario();
        this.tarea.setUsuario(this.user);
    }
    
    @PostConstruct
    public void iniciar(){
        System.out.println(".... inicar tablas GestionTareasManagedBean");
        this.coleccionTareasTODO = this.tareasService.getTareasToDo();
        this.coleccionTareaProgress = this.tareasService.getTareasProgress();
        this.coleccionTareaDone = this.tareasService.getTareasDone();
    }

    public Collection<Tarea> getColeccionTareaDone() {
        return coleccionTareaDone;
    }

    public void setColeccionTareaDone(Collection<Tarea> coleccionTareaDone) {
        this.coleccionTareaDone = coleccionTareaDone;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
    
    public int getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(int idTarea) {
        this.idTarea = idTarea;
    }

    public Tarea getTarea() {
        return tarea;
    }

    public Tarea getTareaAux() {
        return tareaAux;
    }

    public void setTareaAux(Tarea tareaAux) {
        this.tareaAux = tareaAux;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }

    public Collection<Tarea> getColeccionTareaProgress() {
        return coleccionTareaProgress;
    }

    public Collection<Tarea> getColeccionTareasTODO() {
        return coleccionTareasTODO;
    }
  
    public String buscarTarea(String descripcion, String nombre){
        try{
            this.tarea= tareasService.getTareaNombreAndDescripcion(descripcion, nombre);
            return null;
        }
        catch(TareaNotFoundException ex){
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(ex.getMessage()));
            return null; // me quedo en la misma página             
        }
    }
        
//    public void comprueba(){
//        System.out.println("TAMANO DE LISTA:"+listaId.size());
//        System.out.println("valor tamano:"+listaId.isEmpty());
//    }
    
    public String modificarTareaEstado(String estado){
        System.out.println(" ha modificar " + this.tarea);
        FacesContext fc = FacesContext.getCurrentInstance();
        try {
            this.tareasService.modificarEstado(tarea, estado);
            return "tareas?faces-redirect=true";
        } catch (TareaNotFoundException ex) {
            fc.addMessage(null, new FacesMessage("No se pudo modificar. " + ex.getMessage()));  
            return null;
        } catch(Exception e){
              fc.addMessage(null, new FacesMessage("Error no controlado. " + e.getMessage())); 
              e.printStackTrace();
        }
        return null;        
    }
    
//    public void Prueba(int id){
//        System.out.println("Tareas " + this.tarea.getIdTarea() + "/id " + id);
//    }
    
    public String cambiarPagina(Tarea t){
        asignaTarea(t);
        return "modificarTarea?faces-redirect=true";
    }
    
    public void asignaTarea(Tarea t){
        System.out.println("Aqui estoy asignando la tarea:"+t);
        this.tarea = t;
    }
    
    public Tarea recuperaTarea(){
        System.out.println("recuperando la tarea:"+this.tarea);
        return this.tarea;
    }
    
    public String borrar(){        
        try {
            System.out.println("id:"+this.tarea.getIdTarea());
            this.tareasService.borrar(this.tarea.getIdTarea());
            this.iniciar();
            return "tareas?faces-redirect=true";
        } catch (TareaNotFoundException ex) {            
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("No se pudo borrar. " + ex.getMessage())); 
        }
        return null;
    }
   
}