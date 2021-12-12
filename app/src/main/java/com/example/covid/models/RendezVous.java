package com.example.covid.models;

import java.util.Date;

public class RendezVous {
    public int id;
    public Date date;
    public String heur;
    public int userId;
    public int structureId;
    public String structureName;

    public RendezVous() {
    }

    public RendezVous(int id, Date date, String heur, int userId, int structureId) {
        this.id = id;
        this.date = date;
        this.heur = heur;
        this.userId = userId;
        this.structureId = structureId;
    }

    public RendezVous(int id, Date date, String heur, int structureId, String structureName) {
        this.id = id;
        this.date = date;
        this.heur = heur;
        this.structureId = structureId;
        this.structureName = structureName;
    }

    public RendezVous(Date date, String heur, int userId, int structureId) {
        this.date = date;
        this.heur = heur;
        this.userId = userId;
        this.structureId = structureId;
    }
}
