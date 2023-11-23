package com.jesus.courses.springboot.jpa.app.controllers;

import com.jesus.courses.springboot.jpa.app.models.dao.IClientDao;
import com.jesus.courses.springboot.jpa.app.models.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("client")
public class ClientController {

    private IClientDao clientDao;

    @Autowired
    public ClientController(@Qualifier("clientDaoImplement") IClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @RequestMapping(value="list", method=RequestMethod.GET)
    public String clientList(Model model) {
        model.addAttribute("title", "Listado de clientes");
        model.addAttribute("clients", clientDao.findAll());
        return "client/list";
    }

    @RequestMapping(value="/form")
    public String clientForm(Model model) {
        Client client = new Client();
        model.addAttribute("client", client);
        model.addAttribute("title", "Formulario de cliente");
        return "client/form";
    }

    @RequestMapping(value="/form", method = RequestMethod.POST)
    public String clientForm(Client client) {
        clientDao.save(client);
        return "redirect:list";
    }
}
