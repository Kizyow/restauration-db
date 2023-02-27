package fr.perrot54u.restaurationdb.models;

import fr.perrot54u.restaurationdb.database.DBConnection;

import java.sql.*;
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

    public double calculMontantReservation(int numres, String dateHeure, String modePaiement) throws SQLException {

        Connection connection = DBConnection.createSession();
        connection.setAutoCommit(false);

        PreparedStatement psPrix = connection.prepareStatement("SELECT SUM(prixunit*quantite) as prixtotal FROM COMMANDE INNER JOIN PLAT P on P.NUMPLAT = COMMANDE.NUMPLAT WHERE numres = ? GROUP BY numres");
        psPrix.setInt(1, numres);

        ResultSet rsPrix = psPrix.executeQuery();
        double prixTotal = 0.0;

        if (rsPrix.next()) {
            prixTotal = rsPrix.getDouble("prixtotal");
        }
        psPrix.close();

        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        java.util.Date date = null;
        try {
            date = sf.parse(dateHeure);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Date dat = new Date(date.getTime());

        try {

            PreparedStatement ps = connection.prepareStatement("UPDATE reservation SET montcom = ?, datpaie = ?, modpaie = ? WHERE numres = ?");

            ps.setDouble(1, prixTotal);
            ps.setDate(2, dat);
            ps.setString(3, modePaiement);
            ps.setInt(4, numres);
            ps.executeUpdate();

            ps.close();

            connection.commit();
            connection.close();

            return prixTotal;

        } catch (SQLException e) {
            connection.rollback();
            connection.close();
            return -1;
        }

    }

}
