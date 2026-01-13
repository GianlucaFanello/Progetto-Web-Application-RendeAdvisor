package it.unical.demacs.wa.rendeadvisor_be.model.dto;

import it.unical.demacs.wa.rendeadvisor_be.dao.dbManager.DBManager;
import it.unical.demacs.wa.rendeadvisor_be.dao.implementazione.RecensioneDAO;

import java.sql.SQLException;
import java.util.List;

public class AttivitaProxy extends AttivitaDTO {

    RecensioneDAO recensioneDAO;

    public AttivitaProxy() throws SQLException {
        recensioneDAO = new RecensioneDAO(DBManager.getInstance().getConnection());
    }

    @Override
    public List<RecensioneDTO> getRecensioni() throws SQLException{
        List<RecensioneDTO> recensioni = super.getRecensioni();
        if(recensioni==null){
            super.setRecensioni(recensioneDAO.findByLocale(super.getNomeLocale()));
        }
        return super.getRecensioni();
    }

}
