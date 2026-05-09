package edu.esi.ds.esientradas.services;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.esi.ds.esientradas.dao.*;
import edu.esi.ds.esientradas.dto.*;
import edu.esi.ds.esientradas.model.*;

@Service
public class BusquedaService {


    @Autowired
    private EscenarioDao dao;

    @Autowired
    private EntradaTeatroDao entradaTeatroDao;

    @Autowired
    private EntradaZonaDao entradaZonaDao;
    @Autowired
    private EspectaculoDao espectaculoDao;

    @Autowired
    private EntradaDao entradaDao;

    public List<Escenario> getEscenarios() {
        return this.dao.findAll();
    }

    public List<Espectaculo> getEspectaculos(String artista) {
        return this.espectaculoDao.findByArtista(artista);
    }

    public List<Espectaculo> getEspectaculos(Long idEscenario) {
        return this.espectaculoDao.findByEscenarioId(idEscenario);
    }

    public List<Entrada> getEntradas(Long espectaculoId) {
        return this.entradaDao.findByEspectaculoId(espectaculoId);
    }

    public Integer getNumeroDeEntradas(Long espectaculoId) {
        return this.entradaDao.countByEspectaculoId(espectaculoId);
    }

    public Integer getEntradasLibres(Long espectaculoId) {
        return this.entradaDao.findByEspectaculoId(espectaculoId).stream().filter(e -> e.getEstado() == Estado.DISPONIBLE).toList().size();
    }

    
    public DtoEntradas getNumeroDeEntradasComoDto(Long espectaculoId) {

        Object result = entradaDao.getNumeroDeEntradasComoDto(espectaculoId);

        Object[] datos = (Object[]) result;

        DtoEntradas dto = new DtoEntradas();

        dto.setTotal(((Number) datos[0]).intValue());
        dto.setLibres(((Number) datos[1]).intValue());
        dto.setReservadas(((Number) datos[2]).intValue());
        dto.setVendidas(((Number) datos[3]).intValue());

        return dto;
}
   
   public List<Espectaculo> getEspectaculosPorArtista(String artista) {
        return this.espectaculoDao.findByArtista(artista);
    } 

    public List<Espectaculo> getEspectaculosPorFecha(LocalDate fecha){
    LocalDateTime inicio = fecha.atStartOfDay();
    LocalDateTime fin = fecha.atTime(23, 59, 59);

    return espectaculoDao.findByFechaBetween(inicio, fin);
}
    public List<DtoEntradas> getEntradasDisponibles(Long espectaculoId){

    List<Entrada> entradas = entradaDao
        .findByEspectaculoIdAndEstado(espectaculoId, Estado.DISPONIBLE);

    return entradas.stream().map(e -> {

        DtoEntradas dto = new DtoEntradas();

        dto.setId(e.getId());
        dto.setPrecio(e.getPrecio().doubleValue());
        dto.setEstado(e.getEstado().toString());

        Integer tipoLugar = e.getEspectaculo()
                             .getEscenario()
                             .getTipoLugar();

        if(tipoLugar != null && tipoLugar == 1){

            dto.setTipoEntrada(1);

            List<Object[]> datosTeatro = entradaDao.getDatosEntradaTeatro(e.getId());

            if(!datosTeatro.isEmpty()){
                Object[] fila = datosTeatro.get(0);

                dto.setUbicacion(
                    "Fila: " + fila[0] +
                    ", Butaca: " + fila[1] +
                    ", Planta: " + fila[2]
                );
            } else {
                dto.setUbicacion("Asiento asignado");
            }

        } else {

            dto.setTipoEntrada(0);

            List<String> datosZona = entradaDao.getDatosEntradaZona(e.getId());

            if(!datosZona.isEmpty()){
                dto.setUbicacion(datosZona.get(0));
            } else {
                dto.setUbicacion("medio campo");
            }
        }

        return dto;

    }).toList();
}
}
