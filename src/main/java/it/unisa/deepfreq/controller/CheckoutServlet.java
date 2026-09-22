package it.unisa.deepfreq.controller;

import it.unisa.deepfreq.dao.OrdineDAO;
import it.unisa.deepfreq.model.Ordine;
import it.unisa.deepfreq.model.Utente;
import it.unisa.deepfreq.model.Carrello;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Utente utente = (session != null) ? (Utente) session.getAttribute("utenteLoggato") : null;


        if (utente == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Carrello carrello = (session != null) ? (Carrello) session.getAttribute("carrello") : null;


        if (carrello == null || carrello.isVuoto()) {
            response.sendRedirect(request.getContextPath() + "/carrello");
            return;
        }

        try {

            Ordine ordine = new Ordine();
            ordine.setIdUtente(utente.getId());
            ordine.setDataOrdine(LocalDateTime.now());
            ordine.setTotale(carrello.getTotale());
            ordine.setStato("consegnato"); // Impostato direttamente come completato per il progetto


            OrdineDAO ordineDAO = new OrdineDAO();
            boolean salvato = ordineDAO.salvaOrdine(ordine);

            if (salvato) {

                session.removeAttribute("carrello");

                request.getRequestDispatcher("/WEB-INF/view/ordine-successo.jsp").forward(request, response);
            } else {

                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Impossibile salvare l'ordine nel database.");
            }

        } catch (Exception e) {
            e.printStackTrace();

            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Si è verificato un errore durante il checkout.");
        }
    }
}