package it.unisa.deepfreq.dao;

import it.unisa.deepfreq.model.Prodotto;
import it.unisa.deepfreq.model.Traccia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;
import java.sql.ResultSet;

public class ProdottoDAO {


    public List<Prodotto> estraiPerCategoria(String categoria) {
        List<Prodotto> catalogo = new ArrayList<>();
        String queryCategoria = "SELECT * FROM prodotti WHERE categoria = ? AND is_attivo = 1";

        try (Connection con = ConnessioneDB.getConnection();
             PreparedStatement ps = con.prepareStatement(queryCategoria)) {

            ps.setString(1, categoria);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Prodotto p = new Prodotto();
                    p.setId(rs.getInt("id"));
                    p.setTitolo(rs.getString("titolo"));
                    p.setArtista(rs.getString("artista"));
                    p.setGenere(rs.getString("genere"));
                    p.setAnnoUscita(rs.getInt("anno_uscita"));
                    p.setDescrizione(rs.getString("descrizione"));
                    p.setPrezzo(rs.getDouble("prezzo"));
                    p.setQuantitaMagazzino(rs.getInt("quantita_magazzino"));
                    p.setUrlImmagine(rs.getString("url_immagine"));
                    p.setCategoria(rs.getString("categoria")); // Nuovo campo

                    catalogo.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return catalogo;
    }


    public List<Prodotto> estraiTutti() {
        List<Prodotto> catalogo = new ArrayList<>();
        String queryTutti = "SELECT * FROM prodotti WHERE is_attivo = 1";

        try (Connection con = ConnessioneDB.getConnection();
             PreparedStatement ps = con.prepareStatement(queryTutti);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Prodotto p = new Prodotto();
                p.setId(rs.getInt("id"));
                p.setTitolo(rs.getString("titolo"));
                p.setArtista(rs.getString("artista"));
                p.setGenere(rs.getString("genere"));
                p.setAnnoUscita(rs.getInt("anno_uscita"));
                p.setDescrizione(rs.getString("descrizione"));
                p.setPrezzo(rs.getDouble("prezzo"));
                p.setQuantitaMagazzino(rs.getInt("quantita_magazzino"));
                p.setUrlImmagine(rs.getString("url_immagine"));
                p.setCategoria(rs.getString("categoria")); // Nuovo campo

                catalogo.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return catalogo;
    }


    public Prodotto estraiPerId(int id) {
        Prodotto p = null;
        String queryProdotto = "SELECT * FROM prodotti WHERE id = ? AND is_attivo = 1";

        try (Connection con = ConnessioneDB.getConnection();
             PreparedStatement ps = con.prepareStatement(queryProdotto)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    p = new Prodotto();
                    p.setId(rs.getInt("id"));
                    p.setTitolo(rs.getString("titolo"));
                    p.setArtista(rs.getString("artista"));
                    p.setGenere(rs.getString("genere"));
                    p.setAnnoUscita(rs.getInt("anno_uscita"));
                    p.setDescrizione(rs.getString("descrizione"));
                    p.setPrezzo(rs.getDouble("prezzo"));
                    p.setQuantitaMagazzino(rs.getInt("quantita_magazzino"));
                    p.setUrlImmagine(rs.getString("url_immagine"));
                    p.setCategoria(rs.getString("categoria")); // Nuovo campo

                    p.setTracce(estraiTraccePerProdotto(id, con));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }


    private List<Traccia> estraiTraccePerProdotto(int idProdotto, Connection con) {
        List<Traccia> tracce = new ArrayList<>();
        String queryTracce = "SELECT * FROM tracce WHERE id_prodotto = ? ORDER BY numero_traccia ASC";

        try (PreparedStatement ps = con.prepareStatement(queryTracce)) {
            ps.setInt(1, idProdotto);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Traccia t = new Traccia();
                    t.setId(rs.getInt("id"));
                    t.setIdProdotto(rs.getInt("id_prodotto"));
                    t.setNumeroTraccia(rs.getInt("numero_traccia"));
                    t.setTitolo(rs.getString("titolo"));
                    t.setDurata(rs.getString("durata"));

                    tracce.add(t);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tracce;
    }


    public List<Prodotto> estraiNuoviArrivi(int limite) {
        List<Prodotto> lista = new ArrayList<>();
        // Aggiunto il ? al posto del numero fisso 8
        String query = "SELECT * FROM prodotti WHERE is_attivo = 1 ORDER BY id DESC LIMIT ?";

        try (Connection con = ConnessioneDB.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, limite);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Prodotto p = new Prodotto();
                    p.setId(rs.getInt("id"));
                    p.setTitolo(rs.getString("titolo"));
                    p.setArtista(rs.getString("artista"));
                    p.setGenere(rs.getString("genere"));
                    p.setAnnoUscita(rs.getInt("anno_uscita"));
                    p.setDescrizione(rs.getString("descrizione"));
                    p.setPrezzo(rs.getDouble("prezzo"));
                    p.setQuantitaMagazzino(rs.getInt("quantita_magazzino"));
                    p.setUrlImmagine(rs.getString("url_immagine"));
                    p.setCategoria(rs.getString("categoria"));

                    lista.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }




    public List<Prodotto> cercaProdotti(String parolaChiave) {
        List<Prodotto> risultati = new ArrayList<>();

        String queryRicerca = "SELECT * FROM prodotti WHERE (titolo LIKE ? OR artista LIKE ?) AND is_attivo = 1";

        try (Connection con = ConnessioneDB.getConnection();
             PreparedStatement ps = con.prepareStatement(queryRicerca)) {


            String searchPattern = "%" + parolaChiave + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Prodotto p = new Prodotto();
                    p.setId(rs.getInt("id"));
                    p.setTitolo(rs.getString("titolo"));
                    p.setArtista(rs.getString("artista"));
                    p.setGenere(rs.getString("genere"));
                    p.setAnnoUscita(rs.getInt("anno_uscita"));
                    p.setDescrizione(rs.getString("descrizione"));
                    p.setPrezzo(rs.getDouble("prezzo"));
                    p.setQuantitaMagazzino(rs.getInt("quantita_magazzino"));
                    p.setUrlImmagine(rs.getString("url_immagine"));
                    p.setCategoria(rs.getString("categoria"));

                    risultati.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return risultati;
    }


    public List<Prodotto> cercaProdottiConFiltri(String parolaChiave, String stock, List<String> generi, List<String> anni) {
        List<Prodotto> risultati = new ArrayList<>();


        StringBuilder sql = new StringBuilder("SELECT * FROM prodotti WHERE (titolo LIKE ? OR artista LIKE ?) AND is_attivo = 1");


        if (stock != null && stock.equals("in-stock")) {
            sql.append(" AND quantita_magazzino > 0");
        }


        if (generi != null && !generi.isEmpty()) {
            sql.append(" AND genere IN (");
            for (int i = 0; i < generi.size(); i++) {
                sql.append("?");
                if (i < generi.size() - 1) sql.append(", ");
            }
            sql.append(")");
        }


        if (anni != null && !anni.isEmpty()) {
            sql.append(" AND (");
            for (int i = 0; i < anni.size(); i++) {
                if (anni.get(i).equals("2020")) sql.append("(anno_uscita >= 2020)");
                else if (anni.get(i).equals("2010")) sql.append("(anno_uscita >= 2010 AND anno_uscita < 2020)");
                else if (anni.get(i).equals("2000")) sql.append("(anno_uscita >= 2000 AND anno_uscita < 2010)");
                else if (anni.get(i).equals("1990")) sql.append("(anno_uscita >= 1990 AND anno_uscita < 2000)");

                if (i < anni.size() - 1) sql.append(" OR ");
            }
            sql.append(")");
        }

        try (Connection con = ConnessioneDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            int paramIndex = 1;

            String wildcardQuery = "%" + (parolaChiave != null ? parolaChiave : "") + "%";


            ps.setString(paramIndex++, wildcardQuery);
            ps.setString(paramIndex++, wildcardQuery);


            if (generi != null && !generi.isEmpty()) {
                for (String genere : generi) {
                    ps.setString(paramIndex++, genere);
                }
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Prodotto p = new Prodotto();
                    p.setId(rs.getInt("id"));
                    p.setTitolo(rs.getString("titolo"));
                    p.setArtista(rs.getString("artista"));
                    p.setGenere(rs.getString("genere"));
                    p.setAnnoUscita(rs.getInt("anno_uscita"));
                    p.setDescrizione(rs.getString("descrizione"));
                    p.setPrezzo(rs.getDouble("prezzo"));
                    p.setQuantitaMagazzino(rs.getInt("quantita_magazzino"));
                    p.setUrlImmagine(rs.getString("url_immagine"));
                    p.setCategoria(rs.getString("categoria"));

                    risultati.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return risultati;
    }




    public int inserisciProdotto(Prodotto p) {
        String query = "INSERT INTO prodotti (titolo, artista, genere, anno_uscita, descrizione, prezzo, quantita_magazzino, url_immagine, categoria) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConnessioneDB.getConnection();
             // RETURN_GENERATED_KEYS è fondamentale per recuperare l'ID appena creato
             PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, p.getTitolo());
            ps.setString(2, p.getArtista());
            ps.setString(3, p.getGenere());
            ps.setInt(4, p.getAnnoUscita());
            ps.setString(5, p.getDescrizione());
            ps.setDouble(6, p.getPrezzo());
            ps.setInt(7, p.getQuantitaMagazzino());
            ps.setString(8, p.getUrlImmagine());
            ps.setString(9, p.getCategoria());

            ps.executeUpdate();


            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Ritorna -1 in caso di errore
    }


    public void inserisciTraccia(int idProdotto, int numeroTraccia, String titoloTraccia, String durata) {

        String query = "INSERT INTO tracce (id_prodotto, numero_traccia, titolo, durata) VALUES (?, ?, ?, ?)";

        try (Connection con = ConnessioneDB.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, idProdotto);
            ps.setInt(2, numeroTraccia);
            ps.setString(3, titoloTraccia);
            ps.setString(4, durata);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    public boolean eliminaProdotto(int idProdotto) {
        String query = "UPDATE prodotti SET is_attivo = 0 WHERE id = ?";

        try (Connection con = ConnessioneDB.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, idProdotto);
            int righeModificate = ps.executeUpdate();
            return righeModificate > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void eliminaTracce(int idProdotto) {
        String query = "DELETE FROM tracce WHERE id_prodotto = ?";
        try (Connection con = ConnessioneDB.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, idProdotto);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean aggiornaProdotto(Prodotto p) {
        String query = "UPDATE prodotti SET titolo=?, artista=?, genere=?, anno_uscita=?, descrizione=?, prezzo=?, quantita_magazzino=?, url_immagine=?, categoria=? WHERE id=?";
        try (Connection con = ConnessioneDB.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, p.getTitolo());
            ps.setString(2, p.getArtista());
            ps.setString(3, p.getGenere());
            ps.setInt(4, p.getAnnoUscita());
            ps.setString(5, p.getDescrizione());
            ps.setDouble(6, p.getPrezzo());
            ps.setInt(7, p.getQuantitaMagazzino());
            ps.setString(8, p.getUrlImmagine());
            ps.setString(9, p.getCategoria());
            ps.setInt(10, p.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String estraiTracklistTestuale(int idProdotto) {
        StringBuilder tracklist = new StringBuilder();
        String query = "SELECT titolo, durata FROM tracce WHERE id_prodotto = ? ORDER BY numero_traccia ASC";
        try (Connection con = ConnessioneDB.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, idProdotto);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tracklist.append(rs.getString("titolo"))
                        .append(" - ")
                        .append(rs.getString("durata"))
                        .append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tracklist.toString();
    }
}