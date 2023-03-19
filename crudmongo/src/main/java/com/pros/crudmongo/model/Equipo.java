package com.pros.crudmongo.model;

public class Equipo {
    String id;
    String nombre;
    String Entrenador;
    int goles;
    String capitan;
    String liga;
    String pais;

    public Equipo() {
    }

    public Equipo(String id, String nombre, String entrenador, int goles, String capitan, String liga, String pais) {
        this.id = id;
        this.nombre = nombre;
        Entrenador = entrenador;
        this.goles = goles;
        this.capitan = capitan;
        this.liga = liga;
        this.pais = pais;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEntrenador() {
        return Entrenador;
    }

    public void setEntrenador(String entrenador) {
        Entrenador = entrenador;
    }

    public int getGoles() {
        return goles;
    }

    public void setGoles(int goles) {
        this.goles = goles;
    }

    public String getCapitan() {
        return capitan;
    }

    public void setCapitan(String capitan) {
        this.capitan = capitan;
    }

    public String getLiga() {
        return liga;
    }

    public void setLiga(String liga) {
        this.liga = liga;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}
