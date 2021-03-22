
package com.tareas.web;

import com.tareas.entidades.Usuario;
import com.tareas.excepciones.UsuarioNotFoundException;
import com.tareas.excepciones.UsuarioUpdateException;
import com.tareas.servicio.UsuarioServiceLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

@Named(value = "pruebasMB")
@RequestScoped
public class PruebasManagedBean {

    @EJB
    private UsuarioServiceLocal usuarioService;
    private Usuario usuarioEncontrado;
            
    public PruebasManagedBean() {
    }

    public Usuario getUsuarioEncontrado() {
        return usuarioEncontrado;
    }

    public void setUsuarioEncontrado(Usuario usuarioEncontrado) {
        this.usuarioEncontrado = usuarioEncontrado;
    }
    
    //action 
    public void mostarUsuario(int i){
        try {
            this.usuarioEncontrado = usuarioService.getUsuario(i);
            
            this.usuarioEncontrado.setNombre("Begoña lo cambio");
            this.usuarioService.modificar(usuarioEncontrado);
        } catch (UsuarioNotFoundException ex) {
            Logger.getLogger(PruebasManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UsuarioUpdateException ex) {
            Logger.getLogger(PruebasManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
}
