package edu.esi.ds.esientradas.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class EntradaZona extends Entrada {

    private String zona;

    public String getZona() { return zona; }
    public void setZona(String zona) { this.zona = zona; }
}