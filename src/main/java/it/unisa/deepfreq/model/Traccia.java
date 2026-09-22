package it.unisa.deepfreq.model;

public class Traccia {
    private int id;
    private int idProdotto;
    private int numeroTraccia;
    private String titolo;
    private String durata;


    public Traccia() {}


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdProdotto() { return idProdotto; }
    public void setIdProdotto(int idProdotto) { this.idProdotto = idProdotto; }

    public int getNumeroTraccia() { return numeroTraccia; }
    public void setNumeroTraccia(int numeroTraccia) { this.numeroTraccia = numeroTraccia; }

    public String getTitolo() { return titolo; }
    public void setTitolo(String titolo) { this.titolo = titolo; }

    public String getDurata() { return durata; }
    public void setDurata(String durata) { this.durata = durata; }
}