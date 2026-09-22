package it.unisa.deepfreq.model;

import java.util.ArrayList;
import java.util.List;

public class Carrello {
    private List<Prodotto> prodotti;

    public Carrello() {
        this.prodotti = new ArrayList<>();
    }

    public void aggiungiProdotto(Prodotto p) {

        for (Prodotto prod : prodotti) {
            if (prod.getId() == p.getId()) {
                prod.setQuantitaCarrello(prod.getQuantitaCarrello() + 1);
                return;
            }
        }

        p.setQuantitaCarrello(1);
        prodotti.add(p);
    }

    public void rimuoviProdotto(int idProdotto) {
        prodotti.removeIf(p -> p.getId() == idProdotto);
    }

    public void aggiornaQuantita(int idProdotto, int nuovaQuantita) {
        if (nuovaQuantita <= 0) {
            rimuoviProdotto(idProdotto);
            return;
        }
        for (Prodotto prod : prodotti) {
            if (prod.getId() == idProdotto) {
                prod.setQuantitaCarrello(nuovaQuantita);
                break;
            }
        }
    }

    public List<Prodotto> getProdotti() { return prodotti; }

    public double getTotale() {
        double totale = 0;
        for (Prodotto p : prodotti) {
            totale += (p.getPrezzo() * p.getQuantitaCarrello());
        }
        return Math.round(totale * 100.0) / 100.0;
    }

    public boolean isVuoto() { return prodotti.isEmpty(); }
}