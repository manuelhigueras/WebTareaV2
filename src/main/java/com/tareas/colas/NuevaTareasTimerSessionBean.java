/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tareas.colas;

import java.util.Date;
import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 *
 * @author user
 */
@Stateless
public class NuevaTareasTimerSessionBean {

    @Resource(mappedName = "java:app/jms/TareasConnectionFactory")
    private ConnectionFactory connectionFactory;
    @Resource(mappedName = "java:app/jms/colaNuevasTareas")
    private Queue cola;
    
    
    
    @Schedule(hour = "*", minute = "*/1", persistent = false)
    public void myTimer() {
        try{
            System.out.println("Timer event: " + new Date());
            String descripcion = "Tarea " + (int) (Math.random() * 100);
            System.out.println("Envio el mensaje " + descripcion);
            Connection con = connectionFactory.createConnection();
            Session sesion = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
            TextMessage msg = sesion.createTextMessage(descripcion);
            MessageProducer productor = sesion.createProducer(cola);
            
            productor.send(msg);
            
            productor.close();
            sesion.close();
            con.close();
            
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
