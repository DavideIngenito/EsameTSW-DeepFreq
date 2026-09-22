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
import java.util.stream.Collectors;

@WebServlet("/audiogear")
public class AudioGearServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        ProdottoDAO prodottoDAO = new ProdottoDAO();
        List<Prodotto> listaProdotti = prodottoDAO.estraiPerCategoria("Audio Gear");


        String[] tipiScelti = request.getParameterValues("tipo");
        String stock = request.getParameter("stock");

        List<String> tipiList = tipiScelti != null ? Arrays.asList(tipiScelti) : new ArrayList<>();


        List<Prodotto> prodottiFiltrati = listaProdotti.stream()

                .filter(p -> tipiList.isEmpty() || tipiList.contains(p.getGenere()))

                .filter(p -> stock == null || p.getQuantitaMagazzino() > 0)
                .collect(Collectors.toList());


        request.setAttribute("prodotti", prodottiFiltrati);
        request.setAttribute("tipiSelezionati", tipiList);
        request.setAttribute("stockSelezionato", stock);

        // Gestione Wishlist utente
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


        request.getRequestDispatcher("/WEB-INF/view/audiogear.jsp").forward(request, response);
    }
}