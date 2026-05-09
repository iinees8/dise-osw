package edu.es.ds.esiusuarios.http;

import java.io.IOException;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import edu.es.ds.esiusuarios.services.UserService;
import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> credentials) {

        JSONObject jso = new JSONObject(credentials);

        String name = jso.getString("name");
        String password = jso.getString("pwd");

        String result = service.login(name, password);

        if(result == null) {

            throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Credenciales inválidas o cuenta no validada"
            );
        }

        return result;
    }

    @PostMapping("/registrar")
    public String registrar(@RequestBody Map<String, String> credentials) throws IOException {

        JSONObject jso = new JSONObject(credentials);

        String name = jso.getString("name");
        String email = jso.getString("email");
        String pwd1 = jso.getString("pwd1");
        String pwd2 = jso.getString("pwd2");

        String result = service.registrar(name, email, pwd1, pwd2);

        if(result == null) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Datos incorrectos"
            );
        }

        return result;
    }

    @GetMapping("/confirm/{tokenId}")
    public void confirm(
        HttpServletResponse response,
        @PathVariable String tokenId
    ) throws IOException {

        service.confirm(tokenId);

        response.sendRedirect("http://localhost:4200/");
    }
}