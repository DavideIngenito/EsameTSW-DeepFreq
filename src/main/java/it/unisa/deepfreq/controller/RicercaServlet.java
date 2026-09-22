package it.unisa.deepfreq.controller;

import it.unisa.deepfreq.dao.ProdottoDAO;
import it.unisa.deepfreq.dao.WishlistDAO;
import it.unisa.deepfreq.model.Prodotto;
import it.unisa.deepfreq.model.Utente;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet("/ricerca")
public class RicercaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String query = request.getParameter("q");


        String stock = request.getParameter("stock");
        String[] generiArray = request.getParameterValues("genere");
        String[] anniArray = request.getParameterValues("anno");


        List<String> generiList = (generiArray != null) ? Arrays.asList(generiArray) : new ArrayList<>();
        List<String> anniList = (anniArray != null) ? Arrays.asList(anniArray) : new ArrayList<>();

        List<Prodotto> risultati = new ArrayList<>();
        if (query != null && !query.trim().isEmpty()) {
            ProdottoDAO prodottoDAO = new ProdottoDAO();

            risultati = prodottoDAO.cercaProdottiConFiltri(query.trim(), stock, generiList, anniList);
        }


        request.setAttribute("prodotti", risultati);
        request.setAttribute("queryCercata", query);
        request.setAttribute("stockSelezionato", stock);
        request.setAttribute("generiSelezionati", generiList);
        request.setAttribute("anniSelezionati", anniList);


        HttpSession session = request.getSession(false);
        Utente utente = (session != null) ? (Utente) session.getAttribute("utenteLoggato") : null;

        if (utente != null) {
            WishlistDAO wishlistDAO = new WishlistDAO();
            List<Prodotto> preferiti = wishlistDAO.estraiPerUtente(utente.getId());
            List<Integer> idsPreferiti = new ArrayList<>();
            for (Prodotto p : preferiti) {
                idsPreferiti.add(p.getId());
            }
            request.setAttribute("idsPreferiti", idsPreferiti);
        }

        request.getRequestDispatcher("/WEB-INF/view/ricerca.jsp").forward(request, response);
    }
}