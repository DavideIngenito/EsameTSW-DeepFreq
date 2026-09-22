package it.unisa.deepfreq.model;

import java.util.ArrayList;
import java.util.List;

public class Prodotto {
    private int id;
    private String titolo;
    private String artista;
    private String genere;
    private int annoUscita;
    private String descrizione;
    private double prezzo;
    private int quantitaMagazzino;
    private String urlImmagine;
    private String categoria;
    private int quantitaCarrello = 1;


    private List<Traccia> tracce;

    public Prodotto() {
        this.tracce = new ArrayList<>();
    }

    // Getter e Setter standard
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitolo() { return titolo; }
    public void setTitolo(String titolo) { this.titolo = titolo; }

    public String getArtista() { return artista; }
    public void setArtista(String artista) { this.artista = artista; }

    public String getGenere() { return genere; }
    public void setGenere(String genere) { this.genere = genere; }

    public int getAnnoUscita() { return annoUscita; }
    public void setAnnoUscita(int annoUscita) { this.annoUscita = annoUscita; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public double getPrezzo() { return prezzo; }
    public void setPrezzo(double prezzo) { this.prezzo = prezzo; }

    public int getQuantitaMagazzino() { return quantitaMagazzino; }
    public void setQuantitaMagazzino(int quantitaMagazzino) { this.quantitaMagazzino = quantitaMagazzino; }

    public String getUrlImmagine() { return urlImmagine; }
    public void setUrlImmagine(String urlImmagine) { this.urlImmagine = urlImmagine; }

    // Getter e Setter per le tracce
    public List<Traccia> getTracce() { return tracce; }
    public void setTracce(List<Traccia> tracce) { this.tracce = tracce; }
    public void addTraccia(Traccia traccia) { this.tracce.add(traccia); }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getQuantitaCarrello() { return quantitaCarrello; }
    public void setQuantitaCarrello(int quantitaCarrello) { this.quantitaCarrello = quantitaCarrello; }
}