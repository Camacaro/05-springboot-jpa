package com.jesus.courses.springboot.jpa.app.controllers;

import com.jesus.courses.springboot.jpa.app.models.entity.Client;
import com.jesus.courses.springboot.jpa.app.service.IClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("client")
public class ClientController {

    /*
    Dejamos de usar DAO para implementar service, un patron de Facade
     */
//    private final IClientDao clientDao;

//    @Autowired
//    public ClientController(@Qualifier("clientDaoImplement") IClientDao clientDao) {
//        this.clientDao = clientDao;
//    }

    private final IClientService clientService;

    @Autowired
    public ClientController(IClientService clientService) {
        this.clientService = clientService;
    }

    @RequestMapping(value="list", method=RequestMethod.GET)
    public String clientList(Model model) {
        model.addAttribute("title", "Listado de clientes");
        model.addAttribute("clients", clientService.findAll());
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
    public String clientForm(@Valid Client client, BindingResult result, Model model, @RequestParam("file") MultipartFile photo) {
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

        if(!photo.isEmpty()) {
            /*
                Eliminar la ruta interna de la carpeta para subir fotos
                debe ser desde fuera del directorio del proyecto

                Path pathResources = Paths.get("src//main//resources//static/uploads");
                String rootPath = pathResources.toFile().getAbsolutePath();

                Lo vamos hacer en la capeta temporal de la mac, hay que configurarlo.
                MvcConfig.java
             */
            String rootPath = "/tmp/uploads";
            try {
                byte[] bytes = photo.getBytes();
                Path pathComplete = Paths.get(rootPath + "//" + photo.getOriginalFilename());
                Files.write(pathComplete, bytes);

                client.setPhoto(photo.getOriginalFilename());
            } catch (IOException e ) {
                e.printStackTrace();
            }
        }

        clientService.save(client);
        return "redirect:list";
    }

    @RequestMapping(value="/form/{id}")
    public String clientEdit(@PathVariable(value = "id") Long id, Model model) {
        Client client = null;
        if (id>0) {
            client = clientService.findOne(id);
        } else {
            return "redirect:list";
        }
        model.addAttribute("client", client);
        model.addAttribute("title", "Formulario de cliente");
        return "client/form";
    }

    @RequestMapping(value="/delete/{id}")
    public String clientEdit(@PathVariable(value = "id") Long id) {
        if (id>0) {
            clientService.delete(id);
        }
        return "redirect:/client/list";
    }

    @GetMapping(value = "/show/{id}")
    public String show(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
        Client client = clientService.findOne(id);
        if (client == null) {
            flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
            return "redirect:/client/list";
        }
        model.put("client", client);
        model.put("title", "Detalle cliente: " + client.getName());
        return "client/show";
    }

//    @GetMapping(value = "/uploads/{filename:.+")
//    public ResponseEntity<Resource> showPhoto(@PathVariable String filename) {
//        Path pathPhoto = Paths.get("/tmp/uploads").resolve(filename).toAbsolutePath();
//        System.out.printf("phth: " + pathPhoto);
//        Resource resource = null;
//        try {
//            resource = new UrlResource(pathPhoto.toUri());
//            if(!resource.exists() || !resource.isReadable()) {
//                throw new RuntimeException("Error: no se piede cargar la imagen: " + pathPhoto.toString());
//            }
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ resource.getFilename() +"\"")
//                .body(resource);
//    }
}
