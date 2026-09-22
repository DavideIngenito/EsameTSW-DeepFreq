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
import java.util.List;

@WebServlet("/prodotto")
public class ProdottoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");

        if (idParam != null && !idParam.isEmpty()) {
            try {
                int id = Integer.parseInt(idParam);
                ProdottoDAO prodottoDAO = new ProdottoDAO();


                Prodotto prodotto = prodottoDAO.estraiPerId(id);

                if (prodotto != null) {
                    request.setAttribute("prodotto", prodotto);


                    HttpSession session = request.getSession(false);
                    Utente utente = (session != null) ? (Utente) session.getAttribute("utenteLoggato") : null;

                    boolean isPreferito = false;
                    if (utente != null) {
                        WishlistDAO wishlistDAO = new WishlistDAO();
                        List<Prodotto> preferiti = wishlistDAO.estraiPerUtente(utente.getId());
                        for (Prodotto p : preferiti) {
                            if (p.getId() == prodotto.getId()) {
                                isPreferito = true;
                                break;
                            }
                        }
                    }
                    request.setAttribute("isPreferito", isPreferito);

                    request.getRequestDispatcher("/WEB-INF/view/prodotto.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }


        response.sendRedirect(request.getContextPath() + "/catalogo");
    }
}