package it.unisa.deepfreq.controller;

import it.unisa.deepfreq.dao.ProdottoDAO;
import it.unisa.deepfreq.model.Prodotto;
import it.unisa.deepfreq.model.Utente;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Utente utente = (session != null) ? (Utente) session.getAttribute("utenteLoggato") : null;


        if (utente != null && "admin".equals(utente.getRuolo())) {
            request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/home");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Utente utente = (session != null) ? (Utente) session.getAttribute("utenteLoggato") : null;

        if (utente == null || !"admin".equals(utente.getRuolo())) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }


        String titolo = request.getParameter("titolo");
        String artista = request.getParameter("artista");
        String categoria = request.getParameter("categoria");
        String genere = request.getParameter("genere");
        String annoStr = request.getParameter("anno_uscita");
        String prezzoStr = request.getParameter("prezzo");
        String quantitaStr = request.getParameter("quantita");
        String immagine = request.getParameter("immagine");
        String descrizione = request.getParameter("descrizione");
        String tracklist = request.getParameter("tracklist");


        if (titolo == null || titolo.trim().isEmpty() || artista == null || artista.trim().isEmpty()) {
            request.setAttribute("errore", "I campi Titolo e Artista sono obbligatori.");
            request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
            return;
        }


        double prezzo = 0;
        int quantita = 0;
        int annoUscita = 0;

        try {
            prezzo = Double.parseDouble(prezzoStr);
            quantita = Integer.parseInt(quantitaStr);
            annoUscita = Integer.parseInt(annoStr);


            if (prezzo < 0 || quantita < 0) {
                request.setAttribute("errore", "Prezzo e quantità non possono essere negativi.");
                request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
                return;
            }
        } catch (NumberFormatException | NullPointerException e) {

            request.setAttribute("errore", "Formato numerico non valido per Prezzo, Quantità o Anno.");
            request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
            return;
        }


        Prodotto p = new Prodotto();
        p.setTitolo(titolo);
        p.setArtista(artista);
        p.setCategoria(categoria);
        p.setGenere(genere);
        p.setAnnoUscita(annoUscita);
        p.setPrezzo(prezzo);
        p.setQuantitaMagazzino(quantita);
        p.setUrlImmagine(immagine);
        p.setDescrizione(descrizione);

        ProdottoDAO dao = new ProdottoDAO();
        int idGenerato = dao.inserisciProdotto(p);

        if (idGenerato > 0) {
            if (tracklist != null && !tracklist.trim().isEmpty()) {
                String[] righe = tracklist.split("\\r?\\n");
                int contatoreTraccia = 1;
                for (String riga : righe) {
                    if (!riga.trim().isEmpty()) {
                        String[] parti = riga.split("\\s*-\\s*");
                        String titoloTraccia = parti[0].trim();
                        String durata = (parti.length > 1) ? parti[1].trim() : "00:00";
                        dao.inserisciTraccia(idGenerato, contatoreTraccia, titoloTraccia, durata);
                        contatoreTraccia++;
                    }
                }
            }
            
            response.sendRedirect(request.getContextPath() + "/catalogo?successo=inserito");
        } else {
            request.setAttribute("errore", "Errore nel database durante l'inserimento del prodotto.");
            request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
        }
    }
}