package fr.perrot54u.restaurationdb.models;

public class Plat {

    private int numPlat;
    private String nomPlat;
    private PlatType platType;
    private double prix;
    private int quantiteServie;

    public Plat(int numPlat, String nomPlat, PlatType platType, double prix, int quantiteServie) {
        this.numPlat = numPlat;
        this.nomPlat = nomPlat;
        this.platType = platType;
        this.prix = prix;
        this.quantiteServie = quantiteServie;
    }

    public int getNumPlat() {
        return numPlat;
    }

    public String getNomPlat() {
        return nomPlat;
    }

    public PlatType getPlatType() {
        return platType;
    }

    public double getPrix() {
        return prix;
    }

    public int getQuantiteServie() {
        return quantiteServie;
    }

    public enum PlatType {

        ENTREE("Entrée"),
        DESSERT("Dessert"),
        VIANDE("Viande"),
        PLAT("Plat"),
        POISSON("Poisson"),
        FROMAGE("Fromage");

        private String libelle;

        PlatType(String libelle) {
            this.libelle = libelle;
        }

        public static PlatType fromLibelle(String lib) {
            for (PlatType type : values()) {
                if (type.libelle.equalsIgnoreCase(lib)) {
                    return type;
                }
            }
            return null;
        }

        public String getLibelle() {
            return libelle;
        }
    }

}
