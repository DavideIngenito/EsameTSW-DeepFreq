package it.unisa.deepfreq.model;

import java.time.LocalDateTime;

public class Ordine {
    private int id;
    private int idUtente;
    private LocalDateTime dataOrdine;
    private double totale;
    private String stato;

    public Ordine() {
    }

    public Ordine(int id, int idUtente, LocalDateTime dataOrdine, double totale, String stato) {
        this.id = id;
        this.idUtente = idUtente;
        this.dataOrdine = dataOrdine;
        this.totale = totale;
        this.stato = stato;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    public LocalDateTime getDataOrdine() {
        return dataOrdine;
    }

    public void setDataOrdine(LocalDateTime dataOrdine) {
        this.dataOrdine = dataOrdine;
    }

    public double getTotale() {
        return totale;
    }

    public void setTotale(double totale) {
        this.totale = totale;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }
}