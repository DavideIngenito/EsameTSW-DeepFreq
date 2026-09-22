package it.unisa.deepfreq.dao;

import it.unisa.deepfreq.model.Utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtenteDAO {


    public Utente doLogin(String email, String password) {
        Utente utente = null;

        String query = "SELECT * FROM utenti WHERE email = ? AND password = ?";

        try (Connection con = ConnessioneDB.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {


            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    utente = new Utente();
                    utente.setId(rs.getInt("id"));
                    utente.setNome(rs.getString("nome"));
                    utente.setCognome(rs.getString("cognome"));
                    utente.setEmail(rs.getString("email"));
                    utente.setPassword(rs.getString("password"));
                    utente.setRuolo(rs.getString("ruolo"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return utente;
    }


    public boolean registraUtente(Utente utente) {

        String query = "INSERT INTO utenti (nome, cognome, email, password, ruolo) VALUES (?, ?, ?, ?, 'user')";

        try (Connection con = ConnessioneDB.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, utente.getNome());
            ps.setString(2, utente.getCognome());
            ps.setString(3, utente.getEmail());
            ps.setString(4, utente.getPassword());


            int righeInserite = ps.executeUpdate();
            return righeInserite > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean aggiornaPassword(String email, String nuovaPassword) {
        String query = "UPDATE utenti SET password = ? WHERE email = ?";

        try (Connection con = ConnessioneDB.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, nuovaPassword);
            ps.setString(2, email);

            int righeAggiornate = ps.executeUpdate();
            return righeAggiornate > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}