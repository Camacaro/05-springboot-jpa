package com.jesus.courses.springboot.jpa.app.service;

import com.jesus.courses.springboot.jpa.app.models.entity.Client;

import java.util.List;

public interface IClientService {
    public List<Client> findAll();
    public void save(Client client);
    public Client findOne(Long id);
    public void delete(Long id);
}
