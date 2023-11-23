package com.jesus.courses.springboot.jpa.app.controllers;

import com.jesus.courses.springboot.jpa.app.models.dao.IClientDao;
import com.jesus.courses.springboot.jpa.app.models.entity.Client;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("client")
public class ClientController {

    private final IClientDao clientDao;

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

    /*
        Con la anotación @Valid le decimos que valide los campos que se agregaron
        en entity/Client
        Se tiene que usar de esta forma @Valid Client client, BindingResult result
     */
    @RequestMapping(value="/form", method = RequestMethod.POST)
    public String clientForm(@Valid Client client, BindingResult result, Model model) {
        if (result.hasErrors()) {
            /*
               Le agregamos el @ModelAttribute al parámetro para indicar que ese objeto
               lo tiene que devolver a la vista como es client el nombre y es el que
               espera la vista estamos ok, pero si fuera otro lo definimos
               @ModelAttribute("otroNombre")

               Pero no hace falta agregar el @ModelAttribute, ya que se llama igual
               a como se recibe en la vista "client"

               si no va de este modo @ModelAttribute @Valid Client client
             */
            model.addAttribute("title", "Formulario de cliente");
            return "client/form";
        }
        clientDao.save(client);
        return "redirect:list";
    }

    @RequestMapping(value="/form/{id}")
    public String clientEdit(@PathVariable(value = "id") Long id, Model model) {
        Client client = null;
        if (id>0) {
            client = clientDao.findOne(id);
        } else {
            return "redirect:list";
        }
        model.addAttribute("client", client);
        model.addAttribute("title", "Formulario de cliente");
        return "client/form";
    }
}
