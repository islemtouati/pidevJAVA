package com.example.ghada.Model;

public class Panier {
 int id;
 int id_c_id;
 int prix_u;
 int prix_t;
 int quantite;
 String titre_product;
 String desc_product;
 String img_product;


    public Panier(int id, int id_c_id, int prix_u, int prix_t, int quantite, String titre_product, String desc_product, String img_product) {
        this.id = id;
        this.id_c_id = id_c_id;
        this.prix_u = prix_u;
        this.prix_t = prix_t;
        this.quantite = quantite;
        this.titre_product = titre_product;
        this.desc_product = desc_product;
        this.img_product = img_product;
    }

    public Panier(int id_c_id, int prix_u, int prix_t, int quantite, String titre_product, String desc_product, String img_product) {
        this.id_c_id = id_c_id;
        this.prix_u = prix_u;
        this.prix_t = prix_t;
        this.quantite = quantite;
        this.titre_product = titre_product;
        this.desc_product = desc_product;
        this.img_product = img_product;
    }

    public Integer getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_c_id() {
        return id_c_id;
    }

    public void setId_c_id(int id_c_id) {
        this.id_c_id = id_c_id;
    }

    public int getPrix_u() {
        return prix_u;
    }

    public void setPrix_u(int prix_u) {
        this.prix_u = prix_u;
    }

    public int getPrix_t() {
        return prix_t;
    }

    public void setPrix_t(int prix_t) {
        this.prix_t = prix_t;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getTitre_product() {
        return titre_product;
    }

    public void setTitre_product(String titre_product) {
        this.titre_product = titre_product;
    }

    public String getDesc_product() {
        return desc_product;
    }

    public void setDesc_product(String desc_product) {
        this.desc_product = desc_product;
    }

    public String getImg_product() {
        return img_product;
    }

    public void setImg_product(String img_product) {
        this.img_product = img_product;
    }

    @Override
    public String toString() {
        return "Panier{" +
                "id=" + id +
                ", id_c_id=" + id_c_id +
                ", prix_u=" + prix_u +
                ", prix_t=" + prix_t +
                ", quantite=" + quantite +
                ", titre_product='" + titre_product + '\'' +
                ", desc_product='" + desc_product + '\'' +
                ", img_product='" + img_product + '\'' +
                '}';
    }
}
