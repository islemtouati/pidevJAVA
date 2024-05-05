package com.example.javafxx.entities;

public class Utilisateur {

    private int id;
    private String email;
    private String nom;
    private String prenom;
    private String adresse;
    private int tel;
    private String password;
    private String role;
    private int is_banned;

    public Utilisateur(String updatedNom, String updatedPrenom, String updatedEmail, String updatedAdresse, int updatedTel, String role) {
    }
    public Utilisateur(int userId, String updatedNom, String updatedPrenom, String updatedEmail, String updatedAdresse, int updatedTel) {
    }
    public Utilisateur() {
    }

    public Utilisateur(String nom, String prenom, String email, String adresse, int i, String role, String mdp) {
    }

    public Utilisateur(int id, String nom, String prenom, String adresse, String email, int tel, String role, String password) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
        this.tel = tel;
        this.role = role;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getis_banned() {
        return is_banned;
    }

    public void setis_banned(int is_banned) {
        this.is_banned = is_banned;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", tel=" + tel +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", is_banned=" + is_banned +
                '}';
    }
}
