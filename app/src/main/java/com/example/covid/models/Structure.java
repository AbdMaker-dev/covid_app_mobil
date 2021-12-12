package com.example.covid.models;

public class Structure {

    public int id;
    public double lattitude;
    public double longitude;
    public String nom;
    public String contact;
    public String adresse;

    public Structure(int id, double lattitude, double longitude, String nom, String contact, String adresse) {
        this.id = id;
        this.lattitude = lattitude;
        this.longitude = longitude;
        this.nom = nom;
        this.contact = contact;
        this.adresse = adresse;
    }

    public Structure() {
    }

    public Structure(double lattitude, double longitude, String nom, String contact, String adresse) {
        this.lattitude = lattitude;
        this.longitude = longitude;
        this.nom = nom;
        this.contact = contact;
        this.adresse = adresse;
    }
}
