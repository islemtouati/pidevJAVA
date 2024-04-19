package com.webandit.libra.models;

import java.util.List;
import java.util.ArrayList;


public class catego {
    // Variables
    private int id;
    private String titre;
    private List<Product> products;

    // Constructors
    public catego() {

    }

    public catego(int id, String titre) {
        this.id = id;
        this.titre = titre;
        this.products = new ArrayList<>();
    }

    public catego(String titre) {
        this.titre = titre;
        this.products = new ArrayList<>();
    }

    // Methods
    public void addProduct(Product product) {
        products.add(product);
        product.setCategory(this);
    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
