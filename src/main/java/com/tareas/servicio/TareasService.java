package com.tareas.servicio;

import com.tareas.entidades.Tarea;
import com.tareas.excepciones.TareaNotFoundException;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class TareasService implements TareasServiceLocal {

    @PersistenceContext(unitName = "TareasPU")
    private EntityManager em;

    @Override
    public Collection<Tarea> getTareas(Integer idUsuario, String estado) {
        Query query = em.createNamedQuery("Tarea.findByEstadoAndUserId");
        query.setParameter("estado", estado);
        query.setParameter("idUsuario", idUsuario);
        return query.getResultList();
    }

    @Override
    public Collection<Tarea> getTareasToDo(){
        Query query = em.createNamedQuery("Tarea.findByEstadoToDo");
        return query.getResultList();
    }
    
    @Override
    public Collection<Tarea> getTareasDone(){
        Query query = em.createNamedQuery("Tarea.findByEstadoDone");
        return query.getResultList();
    }
    
    @Override
    public Collection<Tarea> getTareasProgress() {
        Query query = em.createNamedQuery("Tarea.findByEstadoProgress");
        return query.getResultList();
    }
    
    @Override
    public Tarea getTareaId(Integer idTarea) throws TareaNotFoundException{
        Tarea t = em.find(Tarea.class, idTarea);
        if(t == null){
            throw new TareaNotFoundException("No se ha encontrado la tarea");
        }
        return t;
    }
    
    @Override
    public Tarea getTareaNombreAndDescripcion(String descripcion, String nombre) throws TareaNotFoundException{
        Query query = em.createNamedQuery("Tarea.findByDescripcionAndNombre");
        query.setParameter("descripcion", descripcion);
        query.setParameter("nombre", nombre);
        try{
            Tarea t = (Tarea) query.getSingleResult();
            return t;
        }
        catch(javax.persistence.NoResultException ex) {
            throw new TareaNotFoundException("No se ha encontrado la tarea " + descripcion + " y " + nombre);
        }
    }
     
    @Override
    public void modificar(Tarea t) throws TareaNotFoundException{
        try {
            Tarea tareaBD = this.getTareaId(t.getIdTarea());  // em.find
            em.merge(t);
        } catch (TareaNotFoundException ex) {
            throw new TareaNotFoundException("No se encontro la tarea");
        }  
    }
    
    @Override
    public void modificarEstado(Tarea t, String estado) throws TareaNotFoundException{
        try{
            t.setEstado(estado);
            Tarea tareaBD = this.getTareaId(t.getIdTarea());  // em.find
            em.merge(t);
        } catch (TareaNotFoundException ex) {
            throw new TareaNotFoundException("No se encontro la tarea");
        }  
    }

    @Override
    public void borrar(int idTarea) throws TareaNotFoundException {
        Tarea t = this.getTareaId(idTarea);
        em.remove(t);
    }

    @Override
    public void altaTarea(Tarea tarea) throws TareaNotFoundException {
        try{
            em.persist(tarea);
        }
        catch(javax.persistence.NoResultException ex){
            throw new TareaNotFoundException("No se pudo crear la tarea");
        }
    }    

}
