package edu.esi.ds.esientradas.services;

import java.util.LinkedList;
import java.util.Queue;

import org.springframework.stereotype.Service;

@Service
public class ColaService {

    private Queue<String> cola = new LinkedList<>();

    private String usuarioEnTurno = null;
    private long tiempoInicioTurno = 0;

    // ⏱ tiempo máximo (ej: 2 min)
    private final long TIEMPO_MAX = 120000;

    // 🚪 entrar en cola
    public int entrarEnCola(String tokenUsuario){
        cola.add(tokenUsuario);
        return cola.size(); // posición
    }

    // 🔍 ver posición
    public int verPosicion(String tokenUsuario){
        int pos = 1;
        for(String t : cola){
            if(t.equals(tokenUsuario)) return pos;
            pos++;
        }
        return -1;
    }

    // 🎯 obtener turno
    public String obtenerTurno(){

    // 🔥 si hay alguien en turno
    if(usuarioEnTurno != null){

        long ahora = System.currentTimeMillis();

        // ⏰ si NO ha caducado → sigue
        if(ahora - tiempoInicioTurno < TIEMPO_MAX){
            return usuarioEnTurno;
        }

        // ❌ si ha caducado → pasa al siguiente
    }

    // 👉 siguiente usuario
    usuarioEnTurno = cola.poll();

    if(usuarioEnTurno != null){
        tiempoInicioTurno = System.currentTimeMillis();
    }

    return usuarioEnTurno;
}

    public boolean puedeComprar(String tokenUsuario){

        // 🔥 actualiza turno antes
        obtenerTurno();

        return tokenUsuario.equals(usuarioEnTurno);
    }

    public void liberarTurno(){
        usuarioEnTurno = null;
    }
}
