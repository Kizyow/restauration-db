package fr.perrot54u.restaurationdb.models;

public class Gestionnaire extends Serveur {

    public Gestionnaire(int numServ, String email, String nomServ) {
        super(numServ, email, nomServ);
    }

    @Override
    public String getGrade() {
        return "gestionnaire";
    }

}
