package it.unisa.deepfreq.dao;

import it.unisa.deepfreq.model.Prodotto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WishlistDAO {

    public boolean aggiungi(int idUtente, int idProdotto) {
        String sql = "INSERT IGNORE INTO preferiti (id_utente, id_prodotto) VALUES (?, ?)";
        try (Connection con = ConnessioneDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUtente);
            ps.setInt(2, idProdotto);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean rimuovi(int idUtente, int idProdotto) {
        String sql = "DELETE FROM preferiti WHERE id_utente = ? AND id_prodotto = ?";
        try (Connection con = ConnessioneDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUtente);
            ps.setInt(2, idProdotto);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isPreferito(int idUtente, int idProdotto) {
        String sql = "SELECT 1 FROM preferiti WHERE id_utente = ? AND id_prodotto = ?";
        try (Connection con = ConnessioneDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUtente);
            ps.setInt(2, idProdotto);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Prodotto> estraiPerUtente(int idUtente) {
        List<Prodotto> lista = new ArrayList<>();
        String sql = "SELECT p.* FROM prodotti p " +
                "INNER JOIN preferiti pref ON p.id = pref.id_prodotto " +
                "WHERE pref.id_utente = ? AND is_attivo = 1 ORDER BY pref.data_aggiunta DESC";

        try (Connection con = ConnessioneDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUtente);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Prodotto p = new Prodotto();
                    p.setId(rs.getInt("id"));
                    p.setTitolo(rs.getString("titolo"));
                    p.setArtista(rs.getString("artista"));
                    p.setGenere(rs.getString("genere"));
                    p.setAnnoUscita(rs.getInt("anno_uscita"));
                    p.setPrezzo(rs.getDouble("prezzo"));
                    p.setQuantitaMagazzino(rs.getInt("quantita_magazzino"));
                    p.setDescrizione(rs.getString("descrizione"));
                    p.setUrlImmagine(rs.getString("url_immagine"));
                    lista.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}