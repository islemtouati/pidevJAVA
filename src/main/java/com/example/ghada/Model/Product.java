package com.example.ghada.Model;

public class Product {
    private int id;
    private int category_id;
    private String titre;
    private double prix;
    private String description;
    private String img;

    public Product(int id, int category_id, String titre, double prix, String description, String img) {
        this.id = id;
        this.category_id = category_id;
        this.titre = titre;
        this.prix = prix;
        this.description = description;
        this.img = img;
    }

    public Product(int category_id, String titre, double prix, String description, String img) {
        this.category_id = category_id;
        this.titre = titre;
        this.prix = prix;
        this.description = description;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", category_id=" + category_id +
                ", titre='" + titre + '\'' +
                ", prix=" + prix +
                ", description='" + description + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
