package edu.es.ds.esiusuarios.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.es.ds.esiusuarios.model.User;

public interface UserDao extends JpaRepository<User, Long> {

    User findByNameAndPassword(String name, String password);

    User findByToken(String token);

    User findByName(String name);
    User findByEmail(String email);
}