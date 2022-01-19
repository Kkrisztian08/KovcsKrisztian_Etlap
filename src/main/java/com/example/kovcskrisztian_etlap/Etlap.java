package com.example.kovcskrisztian_etlap;

public class Etlap {
    private int id;
    private String nev;
    private String leairas;
    private int ar;
    private String kategoria;

    public Etlap(int id, String nev, String leairas, int ar, String kategoria) {
        this.id = id;
        this.nev = nev;
        this.leairas = leairas;
        this.ar = ar;
        this.kategoria = kategoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getLeairas() {
        return leairas;
    }

    public void setLeairas(String leairas) {
        this.leairas = leairas;
    }

    public int getAr() {
        return ar;
    }

    public void setAr(int ar) {
        this.ar = ar;
    }

    public String getKategoria() {
        return kategoria;
    }

    public void setKategoria(String kategoria) {
        this.kategoria = kategoria;
    }
}
