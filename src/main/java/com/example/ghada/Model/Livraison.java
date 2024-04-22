package com.example.ghada.Model;

import javafx.scene.control.TableColumn;

public class Livraison {
    private  int id;
    private int idlivreur;
    private String adresse;
    private int tel;
    private int first_name_id;
    private int last_name_id;
    private String address;
    private int postal_code;
    private String country;
    private String city;
    private int status;


    public Livraison(int id, int idlivreur, String adresse, int tel, int first_name_id, int last_name_id, String address, int postal_code, String country, String city, int status) {
        this.id = id;
        this.idlivreur = idlivreur;
        this.adresse = adresse;
        this.tel = tel;
        this.first_name_id = first_name_id;
        this.last_name_id = last_name_id;
        this.address = address;
        this.postal_code = postal_code;
        this.country = country;
        this.city = city;
        this.status = status;
    }

    public Livraison(String adresse, int tel, String address, int postal_code, String country, String city, int status) {
        this.adresse = adresse;
        this.tel = tel;
        this.address = address;
        this.postal_code = postal_code;
        this.country = country;
        this.city = city;
        this.status = status;
    }

    public Livraison() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdlivreur() {
        return idlivreur;
    }

    public void setIdlivreur(int idlivreur) {
        this.idlivreur = idlivreur;
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

    public int getFirst_name_id() {
        return first_name_id;
    }

    public void setFirst_name_id(int first_name_id) {
        this.first_name_id = first_name_id;
    }

    public int getLast_name_id() {
        return last_name_id;
    }

    public void setLast_name_id(int last_name_id) {
        this.last_name_id = last_name_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(int postal_code) {
        this.postal_code = postal_code;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Livraison{" +
                "id=" + id +
                ", idlivreur=" + idlivreur +
                ", adresse='" + adresse + '\'' +
                ", tel=" + tel +
                ", first_name_id=" + first_name_id +
                ", last_name_id=" + last_name_id +
                ", address='" + address + '\'' +
                ", postal_code=" + postal_code +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", status=" + status +
                '}';
    }
}

