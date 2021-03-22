/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tareas.colas;

import javax.ejb.Singleton;
import javax.jms.JMSConnectionFactoryDefinition;
import javax.jms.JMSDestinationDefinition;
/**
 *
 * @author user
 */

@JMSDestinationDefinition (
    name = "java:app/jms/colaNuevasTareas",
    interfaceName = "javax.jms.Queue",
    destinationName = "colaNuevasTareas"
)

@JMSConnectionFactoryDefinition (
    name="java:app/jms/TareasConnectionFactory"
)

@Singleton
public class InicializarColaSingletonSessionBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
