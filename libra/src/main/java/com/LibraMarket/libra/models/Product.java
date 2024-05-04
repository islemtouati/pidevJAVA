package com.LibraMarket.libra.models;

public class Product {
    private int id;
    private catego category;
    private String titre;
    private double prix;
    private String img;
    private String description;
    private int category_id;

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }



    // Constructors
    public Product() {}
    public Product(String titre, double prix, String img, String description, int category_id) {
        // Initialize the fields with the provided values
        this.titre = titre;
        this.prix = prix;
        this.img = img;
        this.description = description;
        this.category_id = category_id; // Set the category ID
    }
    public Product(int id ,String titre, double prix, String img, String description, int category_id) {
        // Initialize the fields with the provided values
        this.id=id;
        this.titre = titre;
        this.prix = prix;
        this.img = img;
        this.description = description;
        this.category_id = category_id; // Set the category ID
    }
    /*public Product(String titre, float prix, String img, String description, int categoryId) {
        // Initialize the fields with the provided values
        this.titre = titre;
        this.prix = prix;
        this.img = img;
        this.description = description;
        this.category = new catego(); // You may need to initialize the category object differently
        this.category.setId(categoryId); // Set the category ID
    }*/
    public Product(catego category, String titre, double prix, String img, String description) {
        // Initialize the fields with the provided values
        this.category = category;
        this.titre = titre;
        this.prix = prix;
        this.img = img;
        this.description = description;
    }
    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public catego getCategory() {
        return category;
    }

    public void setCategory(catego category) {
        this.category = category;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
