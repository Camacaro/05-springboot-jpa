package com.jesus.courses.springboot.jpa.app.models.dao;

import com.jesus.courses.springboot.jpa.app.models.entity.Client;
import org.springframework.data.repository.CrudRepository;

/*
    Con esta nueva interfaz podemos obviar la interfaz de IClientDao,
    ya que CrudRepository tiene los métodos que creamos en IClientDao
    además de tener otros métodos que nos pueden ayudar.

    Los métodos que creamos en ClientDaoImplement y que implementaIClientDao
    ya se encuentran dentro de la clase CrudRepository

    Simplificando la creación del CRUD, de los métodos que hicimos

    Para utilizar este ejemplo debo ir a ClientServiceImplement
    reemplazar el clientDao que esta y que use esta interfaz
    private IClientDaoCrudRepository clientDao
    luego debo ajustar los métodos, algunos nombres son diferentes
    y funcionara igual, además que ya puedo usar otros métodos que me provee
    CrudRepository sin to tener que crearlos.

    Sí necesito algún Query específico lo creo dentro de esta interfaz, y ya lo puedo
    usar en la clase service

    Para más referencia
    https://docs.spring.io/spring-data/jpa/reference/jpa/getting-started.html
 */

public interface IClientDaoCrudRepository extends CrudRepository<Client, Long> {
}
