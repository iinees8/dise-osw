package edu.esi.ds.esientradas.services;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void enviarEntrada() {
        System.out.println("📧 Email enviado con la entrada");
    }

}
