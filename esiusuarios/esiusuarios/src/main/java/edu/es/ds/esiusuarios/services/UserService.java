package edu.es.ds.esiusuarios.services;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import edu.es.ds.esiusuarios.dao.Token2Dao;
import edu.es.ds.esiusuarios.dao.UserDao;
import edu.es.ds.esiusuarios.model.Token2;
import edu.es.ds.esiusuarios.model.User;
import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserDao dao;

    @Autowired
    private Token2Dao tokenDao;

    @Autowired
    private EmailService emailService;

    

    // 🔐 LOGIN
    public String login(String name, String password) {

        User user = dao.findByNameAndPassword(name, password);

        if(user == null || user.getValidationDate() == null) {
            return null;
        }

        String token = UUID.randomUUID().toString();

        user.setToken(token);

        dao.save(user);

        return token;
    }

    // 🔎 CHECK TOKEN
    public String checkToken(String token) {

        User user = dao.findByToken(token);

        if(user == null) {
            return null;
        }

        return user.getName();
    }

    // 🆕 REGISTRO
    @Transactional
public String registrar(
    String name,
    String email,
    String pwd1,
    String pwd2
) {

    if(!pwd1.equals(pwd2)){
        return null;
    }

    User existente =
        dao.findByEmail(email);

    if(existente != null){
        return null;
    }

    User user = new User();

    user.setName(name);
    user.setEmail(email);
    user.setPassword(pwd1);
    user.setValidationDate(null);

    dao.save(user);

    Token2 token = new Token2();

    token.setUser(user);

    tokenDao.save(token);

    try {

    System.out.println("ANTES EMAIL");

    emailService.sendValidationEmail(user, token);

    System.out.println("DESPUES EMAIL");

} catch(Exception e){

    e.printStackTrace();

}

    return "Usuario registrado";
}

    // ✅ CONFIRMAR CUENTA
    public void confirm(String tokenId) {

        Optional<Token2> opt = tokenDao.findById(tokenId);

        if(!opt.isPresent()) {

            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Token no encontrado"
            );
        }

        Token2 token = opt.get();

        long ahora = System.currentTimeMillis();

        // ⏰ CADUCIDAD 24H
        if(ahora - token.getTime() > 24 * 60 * 60 * 1000) {

            tokenDao.delete(token);

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Token caducado"
            );
        }

        User user = token.getUser();

        user.setValidationDate(
            System.currentTimeMillis()
        );

        dao.save(user);

        tokenDao.delete(token);
    }
}