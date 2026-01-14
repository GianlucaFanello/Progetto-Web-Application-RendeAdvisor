package it.unical.demacs.wa.rendeadvisor_be.dao.implementazione;

import it.unical.demacs.wa.rendeadvisor_be.dao.dbManager.DBManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class DAOconfig {

    @Bean
    public Connection connection() throws SQLException {
        return DBManager.getInstance().getConnection();
    }
}
