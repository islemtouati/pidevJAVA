package com.esprit.models;

import java.time.LocalDate;
import java.util.Objects;

public class Reclamation {
    private int id;
    private int idUser;
    private LocalDate dateR;
    private String description;
    private String etat;

    // Constructors
    public Reclamation(int id, int idUser, LocalDate dateR, String description, String etat) {
        this.id = id;
        this.idUser = idUser;
        this.dateR = dateR;
        this.description = description;
        this.etat = etat;
    }

    public Reclamation() {}

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public LocalDate getDateR() {
        return dateR;
    }

    public void setDateR(LocalDate dateR) {
        this.dateR = dateR;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    // Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reclamation that = (Reclamation) o;
        return id == that.id &&
                idUser == that.idUser &&
                Objects.equals(dateR, that.dateR) &&
                Objects.equals(description, that.description) &&
                Objects.equals(etat, that.etat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idUser, dateR, description, etat);
    }

    // toString Method
    @Override
    public String toString() {
        return "Reclamation{" +
                "id=" + id +
                ", idUser=" + idUser +
                ", dateR=" + dateR +
                ", description='" + description + '\'' +
                ", etat='" + etat + '\'' +
                '}';
    }
}
