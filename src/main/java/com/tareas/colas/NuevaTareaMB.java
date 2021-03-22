/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tareas.colas;

import com.tareas.entidades.Tarea;
import com.tareas.entidades.Usuario;
import com.tareas.excepciones.TareaNotFoundException;
import com.tareas.servicio.TareasService;
import com.tareas.servicio.TareasServiceLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *
 * @author user
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:app/jms/colaNuevasTareas"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class NuevaTareaMB implements MessageListener {
    
    @EJB
    private TareasServiceLocal tareasService;
    
    public NuevaTareaMB() {
    }
    
    @Override
    public void onMessage(Message message) {
        try {
            TextMessage texto = (TextMessage) message;
            System.out.println("... recibo el mensaje " + texto.getText());
            Tarea nueva = new Tarea();
            nueva.setDescripcion(texto.getText());
            nueva.setEstado("DONE");
            Usuario u = new Usuario();
            u.setIdUsuario(1);
            nueva.setUsuario(u);
            
            try {
                tareasService.altaTarea(nueva);
            } catch (TareaNotFoundException ex) {
                Logger.getLogger(NuevaTareaMB.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (JMSException ex) {
            Logger.getLogger(NuevaTareaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
}
