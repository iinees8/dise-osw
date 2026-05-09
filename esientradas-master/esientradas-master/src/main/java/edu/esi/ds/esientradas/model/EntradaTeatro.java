package edu.esi.ds.esientradas.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class EntradaTeatro extends Entrada {

    private Integer fila;
    private Integer butaca;
    private Integer planta;

    public Integer getFila() { return fila; }
    public void setFila(Integer fila) { this.fila = fila; }

    public Integer getButaca() { return butaca; }
    public void setButaca(Integer butaca) { this.butaca = butaca; }

    public Integer getPlanta() { return planta; }
    public void setPlanta(Integer planta) { this.planta = planta; }
}