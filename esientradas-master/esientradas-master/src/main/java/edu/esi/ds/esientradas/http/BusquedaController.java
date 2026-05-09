package edu.esi.ds.esientradas.http;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import edu.esi.ds.esientradas.dao.EntradaDao;
import edu.esi.ds.esientradas.dto.*;
import edu.esi.ds.esientradas.model.*;
import edu.esi.ds.esientradas.services.*;

@RestController
@RequestMapping("/busqueda")
@CrossOrigin(origins = "*")
public class BusquedaController {
    @Autowired
    private BusquedaService service;

        @Autowired
    private EntradaDao entradaDao;

    @GetMapping("/getEntradas")
    public List<Entrada> getEntradas(@RequestParam Long espectaculoId){
        return this.service.getEntradas(espectaculoId);
    }

    @GetMapping("/getNumeroDeEntradas")
    public Integer getNumeroDeEntradas(@RequestParam Long espectaculoId){
        return this.service.getNumeroDeEntradas(espectaculoId);
    }


    @GetMapping("/getEntradasLibres")
    public Integer getEntradasLibres(@RequestParam Long espectaculoId){
        return this.service.getEntradasLibres(espectaculoId);
    }

    @GetMapping("/getNumeroDeEntradasComoDto")
    public DtoEntradas getNumeroDeEntradasComoDto(@RequestParam Long espectaculoId){
        DtoEntradas dto = this.service.getNumeroDeEntradasComoDto(espectaculoId);
        
        return dto;
    }

    @GetMapping("/getEspectaculos")
    public List<DtoEspectaculo> getEspectaculos(@RequestParam String artista){
        List<Espectaculo> espectaculos =  this.service.getEspectaculos(artista);
        List<DtoEspectaculo> dtos = espectaculos.stream().map(e -> { // Convertir Espectaculo a DtoEspectaculo
            DtoEspectaculo dto = new DtoEspectaculo();
            dto.setId(e.getId());
            dto.setArtista(e.getArtista());
            dto.setFecha(e.getFecha());
            dto.setEscenario(e.getEscenario().getNombre());
            return dto;
        }).toList();
        return dtos;
    }


    @GetMapping("/getEspectaculos/{idEscenario}")
    public List<DtoEspectaculo> getEspectaculos(@PathVariable Long idEscenario){
        List<Espectaculo> espectaculos =  this.service.getEspectaculos(idEscenario);
        List<DtoEspectaculo> dtos = espectaculos.stream().map(e -> { // Convertir Espectaculo a DtoEspectaculo
            DtoEspectaculo dto = new DtoEspectaculo();
            dto.setId(e.getId());
            dto.setArtista(e.getArtista());
            dto.setFecha(e.getFecha());
            dto.setEscenario(e.getEscenario().getNombre());
            return dto;
        }).toList();
        return dtos;
    }
    @GetMapping("/getEspectaculosPorArtista/{artista}")
    public List<DtoEspectaculo> getEspectaculosPorArtista(@PathVariable String artista){
        List<Espectaculo> espectaculos = this.service.getEspectaculosPorArtista(artista);

        List<DtoEspectaculo> dtos = espectaculos.stream().map(e -> {
            DtoEspectaculo dto = new DtoEspectaculo();
            dto.setId(e.getId());
            dto.setArtista(e.getArtista());
            dto.setFecha(e.getFecha());
            dto.setEscenario(e.getEscenario().getNombre());
            return dto;
        }).toList();

    return dtos;
}

    @GetMapping("/getEscenarios")
    public List<Escenario> getEscenarios(){
        return this.service.getEscenarios();
    }

    @GetMapping("/saludar/{nombre}")
    public String saludar(@PathVariable String nombre, @RequestParam String apellido) {
        return "Hola, "+nombre+ ""+ apellido+  ",esta es la búsqueda de entradas.";
    }

    @GetMapping("/getEspectaculosPorFecha")
    public List<DtoEspectaculo> getEspectaculosPorFecha(@RequestParam String fecha){

    LocalDate fechaBuscada = LocalDate.parse(fecha);

    List<Espectaculo> espectaculos = this.service.getEspectaculosPorFecha(fechaBuscada);

    return espectaculos.stream().map(e -> {
        DtoEspectaculo dto = new DtoEspectaculo();
        dto.setId(e.getId());
        dto.setArtista(e.getArtista());
        dto.setFecha(e.getFecha());
        dto.setEscenario(e.getEscenario().getNombre());
        return dto;
    }).toList();
}

@GetMapping("/getEntradasDisponibles")
public List<DtoEntradas> getEntradasDisponibles(@RequestParam Long espectaculoId){
    return this.service.getEntradasDisponibles(espectaculoId);
}
}
