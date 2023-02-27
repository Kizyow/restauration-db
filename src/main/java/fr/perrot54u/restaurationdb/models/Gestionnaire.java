package fr.perrot54u.restaurationdb.models;

import fr.perrot54u.restaurationdb.database.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class Gestionnaire extends Serveur {

    public Gestionnaire(int numServ, String email, String nomServ) {
        super(numServ, email, nomServ);
    }

    @Override
    public String getGrade() {
        return "gestionnaire";
    }

    public List<Integer> listServeur() throws SQLException {
        return listOf("SELECT numserv FROM serveur");
    }

    public List<Integer> listTable() throws SQLException {
        return listOf("SELECT numtab FROM tabl");
    }

    public boolean affecterServeur(int numtab, String dateStr, int numserv) throws SQLException {

        Connection connection = DBConnection.createSession();
        connection.setAutoCommit(false);

        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date date = null;
        try {
            date = sf.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Date dat = new Date(date.getTime());


        try {

            PreparedStatement ps = connection.prepareStatement("INSERT INTO affecter VALUES(?,?,?)");

            ps.setInt(1, numtab);
            ps.setDate(2, dat);
            ps.setInt(3, numserv);
            ps.executeUpdate();

            ps.close();

            connection.commit();
            connection.close();

            return true;

        } catch (SQLException e) {
            connection.rollback();
            connection.close();
            return false;
        }

    }

}
