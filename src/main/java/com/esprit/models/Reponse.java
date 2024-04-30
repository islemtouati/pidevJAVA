package com.esprit.models;

import java.util.Date;
import java.util.Objects;

public class Reponse {
    private int idrep;
    private int id;  // This is the ID of the related Reclamation
    private Date dateRep;
    private String description;

    // Constructors
    public Reponse(int idrep, int id, Date dateRep, String description) {
        this.idrep = idrep;
        this.id = id;
        this.dateRep = dateRep;
        this.description = description;
    }

    public Reponse() {}

    // Getters and Setters
    public int getIdrep() {
        return idrep;
    }

    public void setIdrep(int idrep) {
        this.idrep = idrep;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateRep() {
        return dateRep;
    }

    public void setDateRep(Date dateRep) {
        this.dateRep = dateRep;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reponse reponse = (Reponse) o;
        return idrep == reponse.idrep &&
                id == reponse.id &&
                Objects.equals(dateRep, reponse.dateRep) &&
                Objects.equals(description, reponse.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idrep, id, dateRep, description);
    }

    // toString Method
    @Override
    public String toString() {
        return "Reponse{" +
                "idrep=" + idrep +
                ", id=" + id +
                ", dateRep=" + dateRep +
                ", description='" + description + '\'' +
                '}';
    }
}
