package edu.esi.ds.esientradas.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.esi.ds.esientradas.model.EntradaZona;

public interface EntradaZonaDao extends JpaRepository<EntradaZona, Long> {
}