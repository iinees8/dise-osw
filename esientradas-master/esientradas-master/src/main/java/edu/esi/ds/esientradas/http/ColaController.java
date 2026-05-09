package edu.esi.ds.esientradas.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.esi.ds.esientradas.services.ColaService;

@RestController
@RequestMapping("/cola")
@CrossOrigin(origins = "*")
public class ColaController {

    @Autowired
    private ColaService colaService;

    // entrar en cola
    @PostMapping("/entrar")
    public int entrar(@RequestParam String tokenUsuario){
        return colaService.entrarEnCola(tokenUsuario);
    }

    // ver posición
    @GetMapping("/posicion")
    public int posicion(@RequestParam String tokenUsuario){
        return colaService.verPosicion(tokenUsuario);
    }

    // comprobar turno
    @GetMapping("/turno")
    public String turno(){
        return colaService.obtenerTurno();
    }
}
