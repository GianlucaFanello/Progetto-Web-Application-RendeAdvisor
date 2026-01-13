package it.unical.demacs.wa.rendeadvisor_be.model.dto;

import java.sql.SQLException;
import java.util.List;

public interface IRecensione {
    List<RecensioneDTO> getRecensioni() throws SQLException;

}
