package edu.esi.ds.esientradas.dto;

import java.time.LocalDateTime;


// DTO (Data Transfer Object) para Espectaculo
// Este DTO se utiliza para transferir datos de Espectaculo sin exponer toda la entidad
// Contiene solo los campos necesarios para la vista o la comunicación entre capas
// En este caso, se incluye el nombre del artista, la fecha del espectáculo, el nombre del escenario y el ID del espectáculo
// El DTO ayuda a evitar problemas de serialización y a controlar qué datos se exponen a los clientes
public class DtoEspectaculo {

    private String artista;
    private LocalDateTime fecha;
    private String escenario;
    private Long id;

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public void setEscenario(String nombre) {
        this.escenario = nombre;    
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public String getArtista() {
        return artista;
    }

    public String getEscenario() {
        return escenario;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
