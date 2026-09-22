package it.unisa.deepfreq.controller;

import it.unisa.deepfreq.model.Carrello;
import it.unisa.deepfreq.model.Prodotto;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

@WebServlet("/api/carrello")
public class CarrelloAjaxServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        Carrello carrello = (session != null) ? (Carrello) session.getAttribute("carrello") : null;

        if (carrello == null) {
            out.print("{\"success\": false, \"errore\": \"Carrello non trovato\"}");
            return;
        }

        try {
            int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));
            int quantita = Integer.parseInt(request.getParameter("quantita"));

            if (quantita < 1) {
                throw new NumberFormatException();
            }

            carrello.aggiornaQuantita(idProdotto, quantita);

            double totaleCarrello = carrello.getTotale();
            double totaleRiga = 0.0;

            for (Prodotto p : carrello.getProdotti()) {
                if (p.getId() == idProdotto) {
                    totaleRiga = p.getPrezzo() * p.getQuantitaCarrello();
                    break;
                }
            }

            // Formattazione con Locale.US per garantire il punto decimale nel JSON
            String jsonStr = String.format(Locale.US, "{\"success\": true, \"totaleRiga\": %.2f, \"totaleCarrello\": %.2f}", totaleRiga, totaleCarrello);

            // Invio della risposta al client
            out.print(jsonStr);

        } catch (NumberFormatException e) {
            out.print("{\"success\": false, \"errore\": \"Dati non validi\"}");
        } finally {
            out.flush();
        }
    }
}