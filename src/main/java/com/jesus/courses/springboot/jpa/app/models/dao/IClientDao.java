package com.jesus.courses.springboot.jpa.app.models.dao;

import com.jesus.courses.springboot.jpa.app.models.entity.Client;

import java.util.List;

/*
    Esta interfaz tiene que tener los métodos necesarios para
    recuperar y almacenar los datos (contrato de implementación) con
    las operaciones básicas: listar, obtener por id, guardar, eliminar, etc.
 */

public interface IClientDao {

    public List<Client> findAll();

    public void save(Client client);

    public Client findOne(Long id);

}
