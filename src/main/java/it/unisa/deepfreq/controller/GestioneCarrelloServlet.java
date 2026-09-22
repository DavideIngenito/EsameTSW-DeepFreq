package it.unisa.deepfreq.controller;

import it.unisa.deepfreq.model.Carrello;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/gestioneCarrello")
public class GestioneCarrelloServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Carrello carrello = (session != null) ? (Carrello) session.getAttribute("carrello") : null;

        if (carrello != null) {
            String action = request.getParameter("action");
            int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));

            if ("remove".equals(action)) {
                carrello.rimuoviProdotto(idProdotto);
            } else if ("update".equals(action)) {
                int quantita = Integer.parseInt(request.getParameter("quantita"));
                carrello.aggiornaQuantita(idProdotto, quantita);
            }
        }

        response.sendRedirect(request.getContextPath() + "/carrello");
    }
}