package edu.esi.ds.esientradas.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuariosService {

    public String checkToken(String userToken) {
        String endpoint = "http://localhost:8081/external/checkToken";
        RestTemplate rest = new RestTemplate();

        try {
            String userName = rest.getForObject(endpoint +"/"+userToken, String.class);
            if (userName == null || userName.isEmpty()) {
                
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Token no válido");
            }
            return userName;
        }catch(RestClientException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Error al verificar el token: " + e.getMessage());
        }
        
    }

}
