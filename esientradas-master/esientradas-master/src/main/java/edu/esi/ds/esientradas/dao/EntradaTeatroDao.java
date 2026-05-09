package edu.esi.ds.esientradas.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.esi.ds.esientradas.model.EntradaTeatro;

public interface EntradaTeatroDao extends JpaRepository<EntradaTeatro, Long> {
}