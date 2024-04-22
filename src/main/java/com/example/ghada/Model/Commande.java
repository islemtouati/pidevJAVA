package com.example.ghada.Model;

import java.sql.Date;

public class Commande {
private int id;
private int utilisateur_id;
private String created_at;
private String transporteur_name;
private int transporteur_price;
private int is_paid;
private String method;
private String reference;
private String stipe_session_id;
private String paypal_commande_id;

    public Commande(int id, int utilisateur_id, String created_at, String transporteur_name, int transporteur_price, int is_paid, String method, String reference, String stipe_session_id, String paypal_commande_id) {
        this.id = id;
        this.utilisateur_id = utilisateur_id;
        this.created_at = created_at;
        this.transporteur_name = transporteur_name;
        this.transporteur_price = transporteur_price;
        this.is_paid = is_paid;
        this.method = method;
        this.reference = reference;
        this.stipe_session_id = stipe_session_id;
        this.paypal_commande_id = paypal_commande_id;
    }

    public Commande(int utilisateur_id, String created_at, String transporteur_name, int transporteur_price, int is_paid, String method, String reference, String stipe_session_id, String paypal_commande_id) {
        this.utilisateur_id = utilisateur_id;
        this.created_at = created_at;
        this.transporteur_name = transporteur_name;
        this.transporteur_price = transporteur_price;
        this.is_paid = is_paid;
        this.method = method;
        this.reference = reference;
        this.stipe_session_id = stipe_session_id;
        this.paypal_commande_id = paypal_commande_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUtilisateur_id() {
        return utilisateur_id;
    }

    public void setUtilisateur_id(int utilisateur_id) {
        this.utilisateur_id = utilisateur_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getTransporteur_name() {
        return transporteur_name;
    }

    public void setTransporteur_name(String transporteur_name) {
        this.transporteur_name = transporteur_name;
    }

    public int getTransporteur_price() {
        return transporteur_price;
    }

    public void setTransporteur_price(int transporteur_price) {
        this.transporteur_price = transporteur_price;
    }

    public int getIs_paid() {
        return is_paid;
    }

    public void setIs_paid(int is_paid) {
        this.is_paid = is_paid;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getStipe_session_id() {
        return stipe_session_id;
    }

    public void setStipe_session_id(String stipe_session_id) {
        this.stipe_session_id = stipe_session_id;
    }

    public String getPaypal_commande_id() {
        return paypal_commande_id;
    }

    public void setPaypal_commande_id(String paypal_commande_id) {
        this.paypal_commande_id = paypal_commande_id;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", utilisateur_id=" + utilisateur_id +
                ", created_at=" + created_at +
                ", transporteur_name='" + transporteur_name + '\'' +
                ", transporteur_price=" + transporteur_price +
                ", is_paid=" + is_paid +
                ", method='" + method + '\'' +
                ", reference='" + reference + '\'' +
                ", stipe_session_id='" + stipe_session_id + '\'' +
                ", paypal_commande_id='" + paypal_commande_id + '\'' +
                '}';
    }
}
