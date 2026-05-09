package edu.esi.ds.esientradas.dto;


public class DtoEntradas {

    private Long id;
    private Double precio;
    private String estado;
    private int tipoEntrada; 
    private String ubicacion;
    private int total;
    private int libres;
    private int reservadas;
    private int vendidas;


    // 🔹 Constructor vacío (IMPORTANTE para Spring)
    public DtoEntradas() {
    }

    // 🔹 Getters y Setters
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

     public int getTipoEntrada() { return tipoEntrada; }
    public void setTipoEntrada(int tipoEntrada) { this.tipoEntrada = tipoEntrada; }

    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }

    public int getLibres() { return libres; }
    public void setLibres(int libres) { this.libres = libres; }

    public int getReservadas() { return reservadas; }
    public void setReservadas(int reservadas) { this.reservadas = reservadas; }

    public int getVendidas() { return vendidas; }
    public void setVendidas(int vendidas) { this.vendidas = vendidas; }
}