package fr.perrot54u.restaurationdb.models;

import fr.perrot54u.restaurationdb.database.DBConnection;

import java.sql.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

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

    public boolean creerPlat(String libelle, String type, double prix) throws SQLException {

        Connection connection = DBConnection.createSession();
        connection.setAutoCommit(false);

        try {

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT MAX(numplat) as maxi FROM plat");
            int max = 0;
            if (rs.next()) {
                max = rs.getInt("maxi");
            }
            statement.close();

            PreparedStatement ps = connection.prepareStatement("INSERT INTO plat VALUES(?,?,?,?,?)");

            ps.setInt(1, max+1);
            ps.setString(2, libelle);
            ps.setString(3, type);
            ps.setDouble(4, prix);
            ps.setInt(5, 0);
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

    public boolean updatePlat(int numplat, String libelle, String type, double prix) throws SQLException {

        Connection connection = DBConnection.createSession();
        connection.setAutoCommit(false);

        try {

            PreparedStatement ps = connection.prepareStatement("UPDATE plat SET libelle=?,type=?,prixunit=? WHERE numplat = ?");

            ps.setString(1, libelle);
            ps.setString(2, type);
            ps.setDouble(3, prix);
            ps.setInt(4, numplat);
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

        PreparedStatement psPrix = connection.prepareStatement("SELECT SUM(prixunit*quantite) as prixtotal " +
                "FROM COMMANDE INNER JOIN PLAT P on P.NUMPLAT = COMMANDE.NUMPLAT WHERE numres = ? GROUP BY numres");
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

    public List<ServeurData> afficherServeurCA(String dateDebut, String dateFin) throws SQLException {

        Connection connection = DBConnection.createSession();
        connection.setAutoCommit(false);

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT AFFECTER.NUMSERV as numserv, nomserv, sum(montcom) as montant, count(*) as nbcom " +
                "FROM RESERVATION " +
                "INNER JOIN AFFECTER on RESERVATION.NUMTAB = AFFECTER.NUMTAB " +
                "INNER JOIN SERVEUR on AFFECTER.NUMSERV = SERVEUR.NUMSERV " +
                "WHERE DATRES LIKE AFFECTER.DATAFF " +
                "AND DATPAIE BETWEEN to_date(?, 'dd/MM/yyyy') AND to_date(?, 'dd/MM/yyyy') " +
                "GROUP BY AFFECTER.NUMSERV, nomserv " +
                "ORDER BY montant DESC");
        preparedStatement.setString(1, dateDebut);
        preparedStatement.setString(2, dateFin);
        ResultSet rs = preparedStatement.executeQuery();

        List<ServeurData> serveurCA = new ArrayList<>();

        while (rs.next()) {
            int numserv = rs.getInt("numserv");
            String nom = rs.getString("nomserv");
            int nbcom = rs.getInt("nbcom");
            double montant = rs.getDouble("montant");
            serveurCA.add(new ServeurData(numserv, nom, nbcom, montant));
        }


        connection.commit();
        connection.close();

        return serveurCA;

    }

    public List<ServeurData> afficherServeurNonCA(String dateDebut, String dateFin) throws SQLException {

        Connection connection = DBConnection.createSession();
        connection.setAutoCommit(false);

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT NUMSERV, NOMSERV FROM SERVEUR " +
                "WHERE NUMSERV NOT IN ( " +
                "SELECT NUMSERV " +
                "FROM RESERVATION " +
                "INNER JOIN AFFECTER on RESERVATION.NUMTAB = AFFECTER.NUMTAB " +
                "WHERE DATRES LIKE AFFECTER.DATAFF " +
                "AND DATPAIE BETWEEN to_date(?, 'dd/MM/yyyy') AND to_date(?, 'dd/MM/yyyy') " +
                "GROUP BY NUMSERV)");
        preparedStatement.setString(1, dateDebut);
        preparedStatement.setString(2, dateFin);
        ResultSet rs = preparedStatement.executeQuery();

        List<ServeurData> serveurCA = new ArrayList<>();

        while (rs.next()) {
            int numserv = rs.getInt("numserv");
            String nom = rs.getString("nomserv");
            serveurCA.add(new ServeurData(numserv, nom, 0, 0));
        }

        connection.commit();
        connection.close();

        return serveurCA;

    }



}
