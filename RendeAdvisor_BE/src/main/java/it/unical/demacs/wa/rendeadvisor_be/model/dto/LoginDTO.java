package it.unical.demacs.wa.rendeadvisor_be.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class LoginDTO {

    String email;
    String password;
}
