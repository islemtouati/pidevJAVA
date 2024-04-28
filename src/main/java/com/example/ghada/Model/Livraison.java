package com.example.ghada.Model;

import javafx.scene.control.TextField;

public class Livraison {
    private  int id;
    private String adresse;
    private int tel;
    private int first_name_id;
    private int last_name_id;
    private int postal_code;
    private String country;
    private String city;

    private String firstname_client;
    private String lastname_client;


    public Livraison(int id, String adresse, int tel, int first_name_id, int last_name_id, int postal_code, String country, String city, String firstname, String lastname) {
        this.id = id;
        this.adresse = adresse;
        this.tel = tel;
        this.first_name_id = first_name_id;
        this.last_name_id = last_name_id;
        this.postal_code = postal_code;
        this.country = country;
        this.city = city;
        this.firstname_client = firstname;
        this.lastname_client = lastname;
    }

    public Livraison(String adresse, int tel, int postal_code, String country, String city, String firstname, String lastname) {
        this.adresse = adresse;
        this.tel = tel;
        this.postal_code = postal_code;
        this.country = country;
        this.city = city;
        this.firstname_client = firstname_client;
        this.lastname_client = lastname_client;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getFirstname_client() {
        return firstname_client;
    }

    public void setFirstname_client(String firstname_client) {
        this.firstname_client = firstname_client;
    }

    public String getLastname_client() {
        return lastname_client;
    }

    public void setLastname_client(String lastname_client) {
        this.lastname_client = lastname_client;
    }

    @Override
    public String toString() {
        return "Livraison{" +
                "id=" + id +
                ", adresse='" + adresse + '\'' +
                ", tel=" + tel +
                ", first_name_id=" + first_name_id +
                ", last_name_id=" + last_name_id +
                ", postal_code=" + postal_code +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", firstname_client='" + firstname_client + '\'' +
                ", lastname_client='" + lastname_client + '\'' +
                '}';
    }
}

