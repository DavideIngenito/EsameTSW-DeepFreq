package it.unisa.deepfreq.dao;

import it.unisa.deepfreq.model.Ordine;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class OrdineDAO {

    public boolean salvaOrdine(Ordine ordine) {
        String query = "INSERT INTO ordini (id_utente, data_ordine, totale, stato) VALUES (?, ?, ?, ?)";

        try (Connection con = ConnessioneDB.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, ordine.getIdUtente());


            ps.setTimestamp(2, java.sql.Timestamp.valueOf(ordine.getDataOrdine()));

            ps.setDouble(3, ordine.getTotale());
            ps.setString(4, ordine.getStato());

            int righeInserite = ps.executeUpdate();
            return righeInserite > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}