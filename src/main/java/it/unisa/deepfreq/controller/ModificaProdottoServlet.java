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

@WebServlet("/modificaProdotto")
public class ModificaProdottoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Utente utente = (session != null) ? (Utente) session.getAttribute("utenteLoggato") : null;

        if (utente != null && "admin".equals(utente.getRuolo())) {
            int id = Integer.parseInt(request.getParameter("id"));
            ProdottoDAO dao = new ProdottoDAO();
            Prodotto p = dao.estraiPerId(id);

            if (p != null) {
                request.setAttribute("prodotto", p);
                request.setAttribute("tracklist", dao.estraiTracklistTestuale(id));
                request.getRequestDispatcher("/WEB-INF/view/modifica-prodotto.jsp").forward(request, response);
                return;
            }
        }
        response.sendRedirect(request.getContextPath() + "/catalogo");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Utente utente = (session != null) ? (Utente) session.getAttribute("utenteLoggato") : null;

        if (utente == null || !"admin".equals(utente.getRuolo())) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));
        Prodotto p = new Prodotto();
        p.setId(idProdotto);
        p.setTitolo(request.getParameter("titolo"));
        p.setArtista(request.getParameter("artista"));
        p.setCategoria(request.getParameter("categoria"));
        p.setGenere(request.getParameter("genere"));
        p.setAnnoUscita(Integer.parseInt(request.getParameter("anno_uscita")));
        p.setPrezzo(Double.parseDouble(request.getParameter("prezzo")));
        p.setQuantitaMagazzino(Integer.parseInt(request.getParameter("quantita")));
        p.setUrlImmagine(request.getParameter("immagine"));
        p.setDescrizione(request.getParameter("descrizione"));

        ProdottoDAO dao = new ProdottoDAO();
        if (dao.aggiornaProdotto(p)) {

            dao.eliminaTracce(idProdotto);
            String tracklist = request.getParameter("tracklist");

            if (tracklist != null && !tracklist.trim().isEmpty()) {
                String[] righe = tracklist.split("\\r?\\n");
                int contatoreTraccia = 1;

                for (String riga : righe) {
                    if (!riga.trim().isEmpty()) {
                        String[] parti = riga.split("\\s*-\\s*");
                        String titoloTraccia = parti[0].trim();
                        String durata = (parti.length > 1) ? parti[1].trim() : "00:00";
                        dao.inserisciTraccia(idProdotto, contatoreTraccia, titoloTraccia, durata);
                        contatoreTraccia++;
                    }
                }
            }
            response.sendRedirect(request.getContextPath() + "/catalogo");
        } else {
            request.setAttribute("errore", "Errore durante la modifica.");
            request.setAttribute("prodotto", p); // Rimanda i dati per non farli riscrivere
            request.getRequestDispatcher("/WEB-INF/view/modifica-prodotto.jsp").forward(request, response);
        }
    }
}