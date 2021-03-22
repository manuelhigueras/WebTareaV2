
package com.tareas.web;

import com.tareas.entidades.Tarea;
import com.tareas.entidades.Usuario;
import com.tareas.excepciones.LoginException;
import com.tareas.excepciones.UsuarioNotFoundException;
import com.tareas.excepciones.UsuarioUpdateException;
import com.tareas.servicio.UsuarioServiceLocal;
import java.io.Serializable;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;


import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;

@Named(value = "gestionUsuariosMB")
@ViewScoped
public class GestionUsuariosManagedBean implements Serializable {

    private Collection<Usuario> coleccionUsuarios;
    private Usuario usuarioEncontrado;
    
    private String emailABuscar; 
    private String password;
    
    @EJB 
    private UsuarioServiceLocal usuarioService;
    
    public GestionUsuariosManagedBean() {
        System.out.println(".... instanciando GsetionUsuarioNB");
    }
    
    @PostConstruct
    public void inicializar(){
        this.coleccionUsuarios = usuarioService.getAllUsuarios();
    }

    public Collection<Usuario> getColeccionUsuarios() {
        return coleccionUsuarios;
    }

    public Usuario getUsuarioEncontrado() {
        return usuarioEncontrado;
    }

    public String getEmailABuscar() {
        return emailABuscar;
    }

    public void setEmailABuscar(String emailABuscar) {
        this.emailABuscar = emailABuscar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    //acciones
    public String buscarUsuario(String email){
        System.out.println("... buscar " + email);
       return buscarPorMail(email);
    }
    
    public String buscarUsuarioPorEmailEntrada(){
        return buscarPorMail(this.emailABuscar);
    }

    private String buscarPorMail(String email){
        try {
            this.usuarioEncontrado = usuarioService.getUsuarioByEmail(email);
            return null;
        } catch (UsuarioNotFoundException ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(ex.getMessage()));
            return null; // me quedo en la misma página 
        }
    }
    
    public String borrar(int idUsuarioABorrar){        
        try {
            this.usuarioService.borrar(idUsuarioABorrar);
            this.inicializar();
            return null;
        } catch (UsuarioNotFoundException ex) {            
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("No se pudo borrar. " + ex.getMessage())); 
        }
        return null;
    }
    
    public String modificar(){
        System.out.println(" ha modificar " + this.usuarioEncontrado.getNombre());
        FacesContext fc = FacesContext.getCurrentInstance();
        try {           
            this.usuarioService.modificar(usuarioEncontrado);
            this.inicializar();
            return null;
        } catch (UsuarioNotFoundException | UsuarioUpdateException ex) {
             fc.addMessage(null, new FacesMessage("No se pudo modificar. " + ex.getMessage()));        
        }catch(Exception e){
              fc.addMessage(null, new FacesMessage("Error no controlado. " + e.getMessage())); 
              e.printStackTrace();
        }
        return null;
    }
    
    public void Datos(){
        System.out.println("Tarea:"+this.emailABuscar + "/" + this.password);
    }
    
    public String login() {
        // BD - ir a buscar usuario y claeveç
        //System.out.printf("login %s   y clave %s", email, clave);

        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpSession sesion = (HttpSession) ctx.getExternalContext().getSession(true);

        try {
            this.usuarioService.login(this.emailABuscar, this.password, sesion);
            ctx.addMessage(null,new FacesMessage("Welcome " + this.emailABuscar));            
            return "tarea?faces-redirect=true";
        } catch (LoginException ex) {
            Logger.getLogger(GestionUsuariosManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            this.emailABuscar = "";
            this.password = "";

            //mensaje de error
            FacesMessage msg = new FacesMessage(ex.getMessage());
            //ctx.addMessage("formLogin:pwd", msg);
            ctx.addMessage(null, msg);
            return "login?faces-redirect=true";
        }

    }    
    
    public String logout() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpSession sesion = (HttpSession) ctx.getExternalContext().getSession(true);
        this.usuarioService.logout(sesion);
        return "login?faces-redirect=true";
    }
    
}
