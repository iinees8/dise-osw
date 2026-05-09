package edu.esi.ds.esientradas.dao;

import java.util.*;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import edu.esi.ds.esientradas.model.*;

public interface EntradaDao extends JpaRepository<Entrada, Long> {
    List<Entrada> findByEspectaculoId(Long espectaculoId);

    // Método personalizado para actualizar el estado de una entrada a "RESERVADA" dado su ID.
    @Query(value = "UPDATE Entrada e SET e.estado = 'RESERVADA' WHERE e.id = :idEntrada") // Consulta personalizada para actualizar el estado de una entrada a "RESERVADA" dado su ID.
    @Modifying // Indica que esta consulta modifica datos en la base de datos, lo que es necesario para consultas de actualización.
    void updateEstado(@Param("idEntrada") Long idEntrada, @Param("estado") Estado estado);

    Integer countByEspectaculoId(Long espectaculoId);

    @Query(value = """
                    SELECT 
                        COUNT(*) AS TOTAL, 
                        SUM(estado = 'DISPONIBLE') AS LIBRES, 
                        SUM(estado = 'RESERVADA' ) AS RESERVADAS, 
                        SUM(estado = 'VENDIDA') AS VENDIDAS 
                        FROM entrada 
                    WHERE espectaculo_id = :espectaculoId""", nativeQuery = true)
    Object getNumeroDeEntradasComoDto(@Param("espectaculoId") Long espectaculoId);

    List<Entrada> findByEspectaculoIdAndEstado(Long espectaculoId, Estado estado);

    @Query(value = "SELECT fila, butaca, planta FROM entrada_teatro WHERE id = :idEntrada", nativeQuery = true)
    List<Object[]> getDatosEntradaTeatro(@Param("idEntrada") Long idEntrada);

    @Query(value = "SELECT zona FROM entrada_zona WHERE id = :idEntrada", nativeQuery = true)
    List<String> getDatosEntradaZona(@Param("idEntrada") Long idEntrada);


}
    