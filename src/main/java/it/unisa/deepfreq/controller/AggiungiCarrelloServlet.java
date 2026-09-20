package it.unisa.deepfreq.controller;

import it.unisa.deepfreq.dao.ProdottoDAO;
import it.unisa.deepfreq.model.Carrello;
import it.unisa.deepfreq.model.Prodotto;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/aggiungiCarrello")
public class AggiungiCarrelloServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));

        ProdottoDAO prodottoDAO = new ProdottoDAO();
        Prodotto p = prodottoDAO.estraiPerId(idProdotto);

        if (p != null) {
            HttpSession session = request.getSession();
            Carrello carrello = (Carrello) session.getAttribute("carrello");

            // Se non esiste ancora un carrello in sessione, lo creiamo
            if (carrello == null) {
                carrello = new Carrello();
                session.setAttribute("carrello", carrello);
            }

            carrello.aggiungiProdotto(p);
        }

        // Dopo aver aggiunto, rimandiamo l'utente alla pagina del carrello
        response.sendRedirect(request.getContextPath() + "/carrello");
    }
}