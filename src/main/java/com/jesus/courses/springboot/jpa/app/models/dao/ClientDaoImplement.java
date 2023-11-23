package com.jesus.courses.springboot.jpa.app.models.dao;

import com.jesus.courses.springboot.jpa.app.models.entity.Client;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
    Creamos un DAO, que es un: Data Access Object es una clase
    que implementa y provee una interfaz común para acceder y
    trabajar con los datos, independiente de las tecnologías a
    utilizar JDBC, JPA, Hibernate, TopLink, OpenJpa, EclipseLink,
    iBATIS o JDO
 */

@Repository("clientDaoImplement")
public class ClientDaoImplement implements IClientDao {

    /*
        Con @PersistenceContext inserta el motor de base de datos que estemos
        usando configurado en properties, pero si no configuramos nada utilizará por
        defecto H2, la dependencia tiene que estar instalada.

        Como estamos usando H2 necesitamos crear un archivo import.sql con los datos
        a insertar al levantar la app spring boot automáticamente leerá el archivo
        e insertará los datos

        En properties tenemos que agregar el spring.h2.console.enabled=true
        si queremos ver la interfaz. Revisar la consola que dirá la URL de conexión
     */
    @PersistenceContext
    private EntityManager em;

    /*
        El @SuppressWarnings es para quitar el warning que proporciona el IDE,
        pero no es ningún error

        El @Transactional es para decirle que solo sea de lectura.
     */
    @Override
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return em.createQuery("from Client ").getResultList();
    }

    @Override
    @Transactional
    public void save(Client client) {
        if (client.getId() != null && client.getId() > 0) {
            em.merge(client); // Actualiza los datos del cliente
        } else {
            em.persist(client); // Crea uno nuevo
        }
    }

    @Override
    public Client findOne(Long id) {
        return em.find(Client.class, id);
    }
}
