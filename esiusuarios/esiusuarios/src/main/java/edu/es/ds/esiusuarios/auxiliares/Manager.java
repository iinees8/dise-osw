package edu.es.ds.esiusuarios.auxiliares;

import edu.es.ds.esiusuarios.services.EmailService;

public class Manager {
    private static Manager yo;
    private EmailService emailService;
    /* 
    private Manager() {
        this.emailService = new EmailServiceFalso();
        yo = this;
    }*/

    public synchronized static Manager getInstance() { //El sincronized es para que no haya problemas de concurrencia, es decir, que no haya dos hilos intentando crear una instancia al mismo tiempo
        if (yo == null) {
            yo = new Manager();
        }
        return yo;
    }

    public EmailService getEmailService() {
        return this.emailService;
    }

}
