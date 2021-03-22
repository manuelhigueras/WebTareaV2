
package com.tareas.servicio;

import com.tareas.entidades.Usuario;
import com.tareas.excepciones.LoginException;
import com.tareas.excepciones.UsuarioNotFoundException;
import com.tareas.excepciones.UsuarioUpdateException;
import java.util.Collection;
import javax.ejb.Local;
import javax.servlet.http.HttpSession;

@Local
public interface UsuarioServiceLocal {
    
    public  Usuario getUsuario(int id)  throws UsuarioNotFoundException;
    public void alta(Usuario usrNuevo) throws UsuarioNotFoundException;
    public Collection<Usuario> getAllUsuarios();
    public Usuario getUsuarioByEmail(String email) throws UsuarioNotFoundException;
    public void borrar(int id) throws UsuarioNotFoundException;
    public void modificar(Usuario usr) throws UsuarioNotFoundException, UsuarioUpdateException;
    public void login(String email, String clave, HttpSession sesion) throws LoginException;
    public void logout(HttpSession sesion);      
}
