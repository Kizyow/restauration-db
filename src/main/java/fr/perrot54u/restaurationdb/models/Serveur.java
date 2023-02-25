package fr.perrot54u.restaurationdb.models;

import fr.perrot54u.restaurationdb.database.DBConnection;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Serveur {

    private int numServ;
    private String email;
    private String nomServ;

    public Serveur(int numServ, String email, String nomServ) {
        this.numServ = numServ;
        this.email = email;
        this.nomServ = nomServ;
    }

    public List<Integer> consulterTablesDisponible(String dateHeure) throws SQLException {

        Connection connection = DBConnection.createSession();
        connection.setAutoCommit(false);

        PreparedStatement ps = connection.prepareStatement("SELECT numtab FROM TABL MINUS SELECT numtab FROM RESERVATION WHERE to_char(datres, 'DD/MM/YYYY hh24:mi') = ?");
        ps.setString(1, dateHeure);
        ResultSet rs = ps.executeQuery();
        List<Integer> numTables = new ArrayList<>();
        while (rs.next()) {
            numTables.add(rs.getInt("numtab"));
        }
        connection.commit();
        connection.close();

        return numTables;

    }

    public boolean reserverTable(int numtab, String dateHeure, int nbpers) throws SQLException {

        Connection connection = DBConnection.createSession();
        connection.setAutoCommit(false);

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT MAX(numres) as maxi FROM reservation");
        int max = 0;
        if (rs.next()) {
            max = rs.getInt("maxi");
        }
        statement.close();

        List<Integer> numtabsdispos = consulterTablesDisponible(dateHeure);
        if (!numtabsdispos.contains(numtab)) {
            return false;
        }

        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        java.util.Date date = null;
        try {
            date = sf.parse(dateHeure);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Date dat = new Date(date.getTime());

        PreparedStatement pst = connection.prepareStatement("SELECT nbplace FROM tabl WHERE numtab = ?");
        pst.setInt(1, numtab);
        ResultSet rst = pst.executeQuery();
        int maxplace = 0;
        if (rst.next()) {
            maxplace = rst.getInt("nbplace");
        }
        pst.close();

        if (nbpers > maxplace) {
            return false;
        }

        PreparedStatement ps = connection.prepareStatement("INSERT INTO RESERVATION(numres, numtab, datres, nbpers) VALUES(?, ?, ?, ?)");
        ps.setInt(1, max + 1);
        ps.setInt(2, numtab);
        ps.setDate(3, dat);
        ps.setInt(4, nbpers);
        ps.executeUpdate();

        connection.commit();
        connection.close();

        return true;

    }

    public List<Plat> consulterPlats() throws SQLException {

        Connection connection = DBConnection.createSession();
        connection.setAutoCommit(false);

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM plat");
        ResultSet rs = ps.executeQuery();

        List<Plat> plats = new ArrayList<>();

        while (rs.next()) {
            int numPlat = rs.getInt("numplat");
            String libelle = rs.getString("libelle");
            Plat.PlatType platType = Plat.PlatType.fromLibelle(rs.getString("type"));
            double prixunit = rs.getDouble("prixunit");
            int qteServie = rs.getInt("qteservie");

            Plat plat = new Plat(numPlat, libelle, platType, prixunit, qteServie);
            plats.add(plat);
        }

        ps.close();

        connection.commit();
        connection.close();

        return plats;
    }

    public boolean commanderPlat(int numres, int numplat, int quantite) throws SQLException {

        Connection connection = DBConnection.createSession();
        connection.setAutoCommit(false);

        PreparedStatement ps = connection.prepareStatement("INSERT INTO commande VALUES(?,?,?)");
        ps.setInt(1, numres);
        ps.setInt(2, numplat);
        ps.setInt(3, quantite);
        ps.executeUpdate();

        ps.close();

        connection.commit();
        connection.close();

        return true;

    }

    public List<Integer> listReservation() throws SQLException {
        return listOf("SELECT numres FROM reservation");
    }

    public List<Integer> listPlat() throws SQLException {
        return listOf("SELECT numplat FROM plat");
    }

    public List<Integer> listOf(String query) throws SQLException {

        Connection connection = DBConnection.createSession();
        connection.setAutoCommit(false);

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);

        List<Integer> lists = new ArrayList<>();
        while (rs.next()) {
            lists.add(rs.getInt(1));
        }
        statement.close();


        connection.commit();
        connection.close();

        return lists;

    }

    public int getNumServ() {
        return numServ;
    }

    public String getEmail() {
        return email;
    }

    public String getNomServ() {
        return nomServ;
    }

    public String getGrade() {
        return "serveur";
    }

}
