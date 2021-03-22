
package com.tareas.servicio;

import com.tareas.entidades.Tarea;
import com.tareas.entidades.Usuario;
import com.tareas.excepciones.TareaNotFoundException;
import com.tareas.excepciones.UsuarioNotFoundException;
import java.util.Collection;
import javax.ejb.Local;

@Local
public interface TareasServiceLocal {
    
    public void altaTarea(Tarea tareaNuevo) throws TareaNotFoundException;
    public Collection<Tarea> getTareasToDo();
    public Collection<Tarea> getTareas(Integer idUsuario, String estado);
    public Collection<Tarea> getTareasProgress();
    public Collection<Tarea> getTareasDone();
    public Tarea getTareaId(Integer idTarea) throws TareaNotFoundException;
    public void modificar(Tarea t) throws TareaNotFoundException;
    public void modificarEstado(Tarea t, String estado) throws TareaNotFoundException;
    public Tarea getTareaNombreAndDescripcion(String descripcion, String nombre) throws TareaNotFoundException;
    public void borrar(int idTarea) throws TareaNotFoundException;
}
