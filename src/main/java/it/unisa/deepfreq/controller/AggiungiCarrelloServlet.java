package it.unisa.deepfreq.controller;

import it.unisa.deepfreq.dao.ProdottoDAO;
import it.unisa.deepfreq.model.Carrello;
import it.unisa.deepfreq.model.Prodotto;
import it.unisa.deepfreq.model.Utente;

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
        HttpSession session = request.getSession();


        int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));
        ProdottoDAO prodottoDAO = new ProdottoDAO();
        Prodotto p = prodottoDAO.estraiPerId(idProdotto);

        if (p != null) {
            Carrello carrello = (Carrello) session.getAttribute("carrello");
            if (carrello == null) {
                carrello = new Carrello();
                session.setAttribute("carrello", carrello);
            }
            carrello.aggiungiProdotto(p);
        }

        String referer = request.getHeader("referer");
        if (referer != null) {
            response.sendRedirect(referer);
        } else {
            response.sendRedirect(request.getContextPath() + "/catalogo");
        }
    }
}