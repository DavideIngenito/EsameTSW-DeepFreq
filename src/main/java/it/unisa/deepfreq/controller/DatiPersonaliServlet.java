package it.unisa.deepfreq.controller;

import it.unisa.deepfreq.dao.UtenteDAO;
import it.unisa.deepfreq.model.PasswordUtils;
import it.unisa.deepfreq.model.Utente;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/dati-personali")
public class DatiPersonaliServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("utenteLoggato") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/view/datiPersonali.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("utenteLoggato") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Utente utente = (Utente) session.getAttribute("utenteLoggato");
        String vecchiaPassword = request.getParameter("vecchiaPassword");
        String nuovaPassword = request.getParameter("nuovaPassword");


        String errore = validaCambioPassword(vecchiaPassword, nuovaPassword);

        if (errore == null) {
            UtenteDAO utenteDAO = new UtenteDAO();


            Utente verificato = utenteDAO.doLogin(utente.getEmail(), PasswordUtils.hashPassword(vecchiaPassword));

            if (verificato == null) {
                errore = "La vecchia password non è corretta.";
            } else {

                String nuovoHash = PasswordUtils.hashPassword(nuovaPassword);

                if (utenteDAO.aggiornaPassword(utente.getEmail(), nuovoHash)) {
                    utente.setPassword(nuovoHash); // allinea anche l'oggetto in sessione
                    request.setAttribute("successo", "Password aggiornata con successo!");
                } else {
                    errore = "Errore del server durante l'aggiornamento.";
                }
            }
        }

        if (errore != null) {
            request.setAttribute("errore", errore);
        }


        request.getRequestDispatcher("/WEB-INF/view/datiPersonali.jsp").forward(request, response);
    }

    private static String validaCambioPassword(String vecchia, String nuova) {
        if (vecchia == null || vecchia.trim().isEmpty() || nuova == null || nuova.trim().isEmpty()) {
            return "Compila entrambi i campi.";
        }
        if (nuova.length() < 8) {
            return "La nuova password deve avere almeno 8 caratteri.";
        }
        if (nuova.length() > 100) {
            return "La nuova password può avere al massimo 100 caratteri.";
        }
        if (vecchia.equals(nuova)) {
            return "La nuova password deve essere diversa da quella attuale.";
        }
        return null;
    }
}