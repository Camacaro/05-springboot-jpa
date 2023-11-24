package com.jesus.courses.springboot.jpa.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    /*
        Esta es una configuración para registrar un recurso static
        y ser accedido desde la plantilla a la imagen, este registro
        es de una carpeta fuera del proyecto, pudiera ser un link
        en este caso es a un directorio temporal de una mac
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:/tmp/uploads/");
    }
}
