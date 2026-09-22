package it.unisa.deepfreq.model;

public class DettaglioOrdine {
    private int idOrdine;
    private int idProdotto;
    private int quantita;
    private double prezzoUnitario;

    public DettaglioOrdine() {
    }

    public DettaglioOrdine(int idOrdine, int idProdotto, int quantita, double prezzoUnitario) {
        this.idOrdine = idOrdine;
        this.idProdotto = idProdotto;
        this.quantita = quantita;
        this.prezzoUnitario = prezzoUnitario;
    }

    public int getIdOrdine() {
        return idOrdine;
    }

    public void setIdOrdine(int idOrdine) {
        this.idOrdine = idOrdine;
    }

    public int getIdProdotto() {
        return idProdotto;
    }

    public void setIdProdotto(int idProdotto) {
        this.idProdotto = idProdotto;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public double getPrezzoUnitario() {
        return prezzoUnitario;
    }

    public void setPrezzoUnitario(double prezzoUnitario) {
        this.prezzoUnitario = prezzoUnitario;
    }
}