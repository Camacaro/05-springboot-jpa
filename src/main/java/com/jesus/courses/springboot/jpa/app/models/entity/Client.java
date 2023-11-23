package com.jesus.courses.springboot.jpa.app.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "clients") // Nombre de la tabla
public class Client implements Serializable {

    /*
        -Llave primaria Primary Key
        -IDENTITY es el auto incremento en H2 y en MySQL,
        para postgres se puede usar SEQUENCE
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
        Para personalizar el mensaje del error hay que crear una
        properties y agregar lo siguiente
        NotEmpty.client.name=
        Eso se transforma a
        decorador.nombreDelaClaseEnMinúscula.nombreDelAtributo
     */
    @NotEmpty // Validar que no se inserte vacío
    @Size(min = 4, max = 12)
    private String name;

    @NotEmpty // Validar que no se inserte vacío
    private String lastname;


    /*
      especificar el nombre de la tabla si no quisiera email
      Dentro de @Column se encuentra las demás propiedades
      si es null, length
     */
    // @Column(name = "email_client")
    @NotEmpty
    @Email
    private String email;

    @Column(name = "create_at")
    @Temporal(TemporalType.DATE) // Formato en como se va a guardar la fecha en la db
    @DateTimeFormat(pattern = "yyyy-MM-dd") // Formato de como quiero que se guarde
    @NotNull
    private Date createAt;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /*
        Insertar la fecha, justo antes de guardar en la db
        de manera automática
     */
//    @PrePersist
//    public void prePersist() {
//        createAt = new Date();
//    }
}
