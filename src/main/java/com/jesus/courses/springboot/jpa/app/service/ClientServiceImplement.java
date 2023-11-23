package com.jesus.courses.springboot.jpa.app.service;

import com.jesus.courses.springboot.jpa.app.models.dao.IClientDao;
import com.jesus.courses.springboot.jpa.app.models.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientServiceImplement implements IClientService {

    /**
     * Esto es porque podemos tener multiples DAO y desde aquí
     * podemos cambiar su cambiarlo.
     *
     * Movemos los Transactional del ClientDaoImplement hacia acá
     * esto es por si implementamos varios DAO estén bajo la misma transaction
     *
     * Este es un patron Facade
     */

    @Autowired
    private IClientDao clientDao;

    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return clientDao.findAll();
    }

    @Override
    @Transactional
    public void save(Client client) {
        clientDao.save(client);
    }

    @Override
    @Transactional(readOnly = true)
    public Client findOne(Long id) {
        return clientDao.findOne(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        clientDao.delete(id);
    }
}
