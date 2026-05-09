package edu.esi.ds.esientradas.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import edu.esi.ds.esientradas.services.ReservasService;

@RestController
@RequestMapping("/compras")
@CrossOrigin(origins = "*")
public class ComprasController {

    @Autowired
    private ReservasService reservasService;

    @PostMapping("/comprar")
    public String comprar(@RequestParam String tokenEntrada,
                          @RequestParam String tokenUsuario){

        return reservasService.comprar(tokenEntrada, tokenUsuario);
    }
}