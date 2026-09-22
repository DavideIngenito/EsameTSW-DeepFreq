package it.unisa.deepfreq.controller;

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

@WebServlet("/wishlist")
public class WishlistServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();


        Utente utente = (Utente) session.getAttribute("utenteLoggato");

        if (utente == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        WishlistDAO wishlistDAO = new WishlistDAO();
        List<Prodotto> preferiti = wishlistDAO.estraiPerUtente(utente.getId());
        request.setAttribute("preferiti", preferiti);

        request.getRequestDispatcher("/WEB-INF/view/wishlist.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();


        Utente utente = (Utente) session.getAttribute("utenteLoggato");

        if (utente == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");
        String idParam = request.getParameter("idProdotto");

        if (idParam != null && !idParam.isEmpty()) {
            try {
                int idProdotto = Integer.parseInt(idParam);
                WishlistDAO dao = new WishlistDAO();
                if ("remove".equals(action)) {
                    dao.rimuovi(utente.getId(), idProdotto);
                } else {
                    dao.aggiungi(utente.getId(), idProdotto);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        String redirect = request.getParameter("redirect");
        if ("wishlist".equals(redirect)) {
            response.sendRedirect(request.getContextPath() + "/wishlist");
        } else {
            response.sendRedirect(request.getHeader("Referer") != null ? request.getHeader("Referer") : request.getContextPath() + "/catalogo");
        }
    }
}