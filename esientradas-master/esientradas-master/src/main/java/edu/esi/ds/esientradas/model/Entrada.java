package edu.esi.ds.esientradas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Entrada {
    @Id @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    protected Long id;
    private Long precio;     // Ojo: en céntimos de euro

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "espectaculo_id", nullable = false)
    protected Espectaculo espectaculo;

    @Enumerated(EnumType.STRING)
    protected Estado estado;

    @Transient
    protected String token; // No se mapea a la base de datos, se utiliza para

    //@Transient
    //protected Token token; // No se mapea a la base de datos, se utiliza para almacenar el token asociado a la entrada durante la reserva.

    //FetchType.LAZY para cargar el token solo cuando se necesite, y CascadeType.ALL para que al eliminar una entrada se elimine su token asociado
    @OneToOne(mappedBy = "entrada", cascade = jakarta.persistence.CascadeType.ALL, fetch = FetchType.LAZY)
    //@JoinColumn(name = "token_valor", referencedColumnName = "valor")
    //protected Token token;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public Espectaculo getEspectaculo() {
        return espectaculo;
    }
    public void setEspectaculo(Espectaculo espectaculo) {
        this.espectaculo = espectaculo;
    }
    public Estado getEstado() {
        return estado;
    }
    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    public Long getPrecio() {
        return precio;
    }
    public void setPrecio(Long precio) {
        this.precio = precio;
    }
}
