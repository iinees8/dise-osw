package edu.es.ds.esiusuarios.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import edu.es.ds.esiusuarios.services.UserService;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/external")
public class ExternalController {
    @Autowired
    private UserService service;

    @GetMapping("/checkToken/{token}")
    public String checkToken(@PathVariable String token) {
        if (token == null || token.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Se necesita el token");
        } 

        String username = this.service.checkToken(token);
        if (username == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token no válido");
        } 
        return this.service.checkToken(token);
    }
}
