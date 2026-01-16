package it.unical.demacs.wa.rendeadvisor_be.controller;

import it.unical.demacs.wa.rendeadvisor_be.model.dto.UtenteDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class SessioneCorrente {

    private String username;

    public void set(String username){
        this.username = username;
    }

    public String get(){
        return this.username;
    }

    public boolean isLogged() {
        return username != null;
    }

}
