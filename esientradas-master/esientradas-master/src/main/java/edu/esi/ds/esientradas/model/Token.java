package edu.esi.ds.esientradas.model;

import java.util.UUID;

import jakarta.persistence.*;

@Entity
public class Token {

    @Id
    @Column(length = 36)
    private String valor;

    // ⏱️ momento creación token
    private Long hora;

    // 👤 sesión navegador
    private String sessionId;

    // 🎟️ entrada asociada
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entrada_id", referencedColumnName = "id")
    private Entrada entrada;

    public Token() {

        this.valor = UUID.randomUUID().toString();

        this.hora = System.currentTimeMillis();
    }

    // GETTERS Y SETTERS

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Long getHora() {
        return hora;
    }

    public void setHora(Long hora) {
        this.hora = hora;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Entrada getEntrada() {
        return entrada;
    }

    public void setEntrada(Entrada entrada) {
        this.entrada = entrada;
    }

    
}