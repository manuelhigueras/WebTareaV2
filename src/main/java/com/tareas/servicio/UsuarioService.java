package com.tareas.servicio;

import com.tareas.entidades.Usuario;
import com.tareas.excepciones.LoginException;
import com.tareas.excepciones.UsuarioNotFoundException;
import com.tareas.excepciones.UsuarioUpdateException;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;

@Stateless
public class UsuarioService implements UsuarioServiceLocal {

    @PersistenceContext(unitName = "TareasPU")
    private EntityManager em;

    @Override
    public Usuario getUsuario(int id) throws UsuarioNotFoundException {
        Usuario u = em.find(Usuario.class, id);
        if(u == null){
            throw new UsuarioNotFoundException("No existe el usuario solicitado");
        }
        return u;
    }

    @Override
    public void alta(Usuario usr) {
        em.persist(usr);
    }

    @Override
    public Collection<Usuario> getAllUsuarios() {
        // consulta SQL   -  SELECT * FROM USUAIROS
        // jpa query -       SELECT  u  FROM Usuario u
        //                   SELECT u.idUsuaio, u.emial FROM Usuario u

        //CREATE NATIVE QUERY   - usa SQL SINTAXIS , TABLAS COLUMNAS 
        //String consulta = "SELECT * FROM USUARIOS";
        //Query query = em.createNativeQuery(consulta);
        //CREATE QUERY
//        String consulta = "SELECT  u  FROM Usuario u";
//        Query query = em.createQuery(consulta);
        //CREATE NAMEDQUERY 
        Query query = em.createNamedQuery("Usuario.findAll");

        return query.getResultList();

    }

    @Override
    public Usuario getUsuarioByEmail(String email) throws UsuarioNotFoundException {

        Query query = em.createNamedQuery("Usuario.findByEmail");
        query.setParameter("email", email);
        try {
            Usuario usr = (Usuario) query.getSingleResult();
            return usr;
        } catch (javax.persistence.NoResultException e) {
                throw new UsuarioNotFoundException("no existe un usuario con el emial " + email);
        }
    }
    
    public Usuario getUsuarioByEmailAndPassword(String email, String password) throws UsuarioNotFoundException {

        Query query = em.createNamedQuery("Usuario.findByEmailAndPassword");
        query.setParameter("email", email);
        query.setParameter("password", password);
        try {
            Usuario usr = (Usuario) query.getSingleResult();
            return usr;
        } catch (javax.persistence.NoResultException e) {
                throw new UsuarioNotFoundException("no existe el usuario con el email " + email + "y clave " + password);
        }
    } 

    @Override
    public void borrar(int id) throws UsuarioNotFoundException {
        Usuario u = this.getUsuario(id);
        em.remove(u);
        //commit -- "DELETE ... 
    }
    
     public void modificar(Usuario usr) throws UsuarioNotFoundException, UsuarioUpdateException{
         
           Usuario usrBD = this.getUsuario(usr.getIdUsuario());  // em.find
//           usrBD.setEmail(usr.getEmail());
//           usrBD.setNombre(usr.getNombre());
//           usrBD.setPassword(usr.getPassword());
           em.merge(usr);

          //los métodos de un EJB HACE COMMIT AL FINA SI NO HAY EXCEPCIONES
         // COMMIT  -> se sincronice los objetos de em con las tablas de la bd
     }

    @Override
    public void login(String email, String clave, HttpSession sesion) throws LoginException {
        //En bd ver si existe y coincide email y clave
        Collection<Usuario> usuarios = this.getAllUsuarios();
        Usuario usrEncontrado = null;
        for(Usuario u: usuarios){
           if(u.getEmail().equals(email)){
               usrEncontrado = u;
               break;
           }
        }
        //si existe añadir a sesion
        //sino lanzo exception
        if(usrEncontrado == null){
            throw new LoginException("El usuario no existe. Debe registrarse.");
        }
        else{
            if(usrEncontrado.getPassword().equals(clave)){
                sesion.setAttribute("usuario", usrEncontrado);
            }
            else{
                throw new LoginException("Clave no valida");
            }
        }  
    }
    
    @Override
    public void logout(HttpSession sesion){
//        Collection<Usuario> lista = this.getAllUsuarios();
//        for(Usuario user: lista){
//            if(user.getActivo())
//                user.setActivo(false);
//        }
        sesion.invalidate();
    }
    
}
