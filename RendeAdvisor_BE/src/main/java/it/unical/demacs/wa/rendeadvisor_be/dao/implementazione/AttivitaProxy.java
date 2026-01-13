package it.unical.demacs.wa.rendeadvisor_be.dao.implementazione;

import javax.sql.DataSource;
import java.sql.Connection;

public class AttivitaProxy extends AttivitaDAO{
    private DataSource dataSource;

    public AttivitaProxy(DataSource dataSource, Connection connection) {
        super(connection);
        this.dataSource = dataSource;
    }
}
