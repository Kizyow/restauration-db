package fr.perrot54u.restaurationdb.models;

public class ServeurData {

    private int numServ;
    private String nom;
    private int nbCom;
    private double montant;

    /**
     * Permet d'avoir les infos sur le chiffre d'affaire d'un serveur par exemple
     * @param numServ
     * @param nom
     * @param nbCom
     * @param montant
     */
    public ServeurData(int numServ, String nom, int nbCom, double montant) {
        this.numServ = numServ;
        this.nom = nom;
        this.nbCom = nbCom;
        this.montant = montant;
    }

    public int getNumServ() {
        return numServ;
    }

    public String getNom() {
        return nom;
    }

    public int getNbCom() {
        return nbCom;
    }

    public double getMontant() {
        return montant;
    }

}
