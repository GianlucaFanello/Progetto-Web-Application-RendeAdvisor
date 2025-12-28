package it.unical.demacs.wa.rendeadvisor_be.dao.dbManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {


    //Ho usato il pattern Singleton
    private static DbManager instance = null;
    public static DbManager getInstance(){
        if (instance == null){
            instance = new DbManager();
        }
        return instance;
    }

    Connection con = null;

    public Connection getConnection(){
        if (con == null){
            try {
                con = DriverManager.getConnection("");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return con;
    }


    public IUtenteDAO utenteDao(){
        return new UtenteDAO(getConnection());
    }

    public IRispostaDAO rispostaDao(){
        return new RispostaDAO(getConnection());
    }

    public IRecensioneDAO recensioneDao(){
        return new RecensioneDAO(getConnection());
    }
    public INelCuoreDAO nelCuoreDAO() {
        return new NelCuoreDAO(getConnection());
    }
    public IAttivitaDAO attivitaDAO() {
        return new AttivitaDAO(getConnection());
    }

}

