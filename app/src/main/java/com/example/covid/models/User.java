package com.example.covid.models;

public class User {
    public int id;
    public String nom;
    public String prenom;
    public String username;

    public User() {
    }

    public User(String nom, String prenom, String username) {
        this.nom = nom;
        this.prenom = prenom;
        this.username = username;
    }

    public User(int id, String nom, String prenom, String username) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.username = username;
    }
}
