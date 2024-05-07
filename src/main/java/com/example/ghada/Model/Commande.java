package com.example.ghada.Model;

public class Command {

    int id;
    int user_id;
    String nom_utilisateur;
    String prenom_utilisateur;
    String adresse_utilisateur;
    int prix_total;
    int Tel_utilis;
    String pays_utilis;
    String mode_p;

    public Command(int id,int user_id, String nom_utilisateur, String prenom_utilisateur, String adresse_utilisateur,  int prix_total,  int tel_utilis, String pays_utilis, String mode_p) {
        this.id = id;
        this.user_id= user_id;
        this.nom_utilisateur = nom_utilisateur;
        this.prenom_utilisateur = prenom_utilisateur;
        this.adresse_utilisateur = adresse_utilisateur;
        this.prix_total = prix_total;
        this.Tel_utilis = tel_utilis;
        this.pays_utilis = pays_utilis;
        this.mode_p = mode_p;
    }

    public Command(int user_id,String nom_utilisateur, String prenom_utilisateur, String adresse_utilisateur,  int prix_total, int tel_utilis, String pays_utilis, String mode_p) {
       this.user_id = user_id;
        this.nom_utilisateur = nom_utilisateur;
        this.prenom_utilisateur = prenom_utilisateur;
        this.adresse_utilisateur = adresse_utilisateur;
        this.prix_total = prix_total;
        this.Tel_utilis = tel_utilis;
        this.pays_utilis = pays_utilis;
        this.mode_p = mode_p;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }


    public String getNom_utilisateur() {
        return nom_utilisateur;
    }

    public void setNom_utilisateur(String nom_utilisateur) {
        this.nom_utilisateur = nom_utilisateur;
    }

    public String getPrenom_utilisateur() {
        return prenom_utilisateur;
    }

    public void setPrenom_utilisateur(String prenom_utilisateur) {
        this.prenom_utilisateur = prenom_utilisateur;
    }

    public String getAdresse_utilisateur() {
        return adresse_utilisateur;
    }

    public void setAdresse_utilisateur(String adresse_utilisateur) {
        this.adresse_utilisateur = adresse_utilisateur;
    }



    public int getPrix_total() {
        return prix_total;
    }

    public void setPrix_total(int prix_total) {
        this.prix_total = prix_total;
    }


    public int getTel_utilis() {
        return Tel_utilis;
    }

    public void setTel_utilis(int tel_utilis) {
        Tel_utilis = tel_utilis;
    }

    public String getPays_utilis() {
        return pays_utilis;
    }

    public void setPays_utilis(String pays_utilis) {
        this.pays_utilis = pays_utilis;
    }

    public String getMode_p() {
        return mode_p;
    }

    public void setMode_p(String mode_p) {
        this.mode_p = mode_p;
    }

    @Override
    public String toString() {
        return "Command{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", nom_utilisateur='" + nom_utilisateur + '\'' +
                ", prenom_utilisateur='" + prenom_utilisateur + '\'' +
                ", adresse_utilisateur='" + adresse_utilisateur + '\'' +
                ", prix_total=" + prix_total +
                ", Tel_utilis=" + Tel_utilis +
                ", pays_utilis='" + pays_utilis + '\'' +
                ", mode_p='" + mode_p + '\'' +
                '}';
    }
}