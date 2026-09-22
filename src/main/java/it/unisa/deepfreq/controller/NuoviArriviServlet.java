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

@WebServlet("/nuovi-arrivi")
public class NuoviArriviServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ProdottoDAO prodottoDAO = new ProdottoDAO();

        List<Prodotto> ultimiArrivi = prodottoDAO.estraiNuoviArrivi(20);


        String[] generiScelti = request.getParameterValues("genere");
        String[] anniScelti = request.getParameterValues("anno");
        String stock = request.getParameter("stock");

        List<String> generiList = generiScelti != null ? Arrays.asList(generiScelti) : new ArrayList<>();
        List<String> anniList = anniScelti != null ? Arrays.asList(anniScelti) : new ArrayList<>();


        List<Prodotto> prodottiFiltrati = ultimiArrivi.stream()

                .filter(p -> generiList.isEmpty() || generiList.contains(p.getGenere()))

                .filter(p -> {
                    if (anniList.isEmpty()) return true;
                    int anno = p.getAnnoUscita();
                    if (anniList.contains("2020") && anno >= 2020) return true;
                    if (anniList.contains("2010") && anno >= 2010 && anno < 2020) return true;
                    if (anniList.contains("2000") && anno >= 2000 && anno < 2010) return true;
                    if (anniList.contains("1990") && anno >= 1990 && anno < 2000) return true;
                    return false;
                })

                .filter(p -> stock == null || p.getQuantitaMagazzino() > 0)
                .collect(Collectors.toList());


        request.setAttribute("prodotti", prodottiFiltrati);
        request.setAttribute("generiSelezionati", generiList);
        request.setAttribute("anniSelezionati", anniList);
        request.setAttribute("stockSelezionato", stock);


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

        request.getRequestDispatcher("/WEB-INF/view/nuoviarrivi.jsp").forward(request, response);
    }
}