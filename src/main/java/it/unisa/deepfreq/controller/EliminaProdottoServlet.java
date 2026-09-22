package it.unisa.deepfreq.controller;

import it.unisa.deepfreq.dao.ProdottoDAO;
import it.unisa.deepfreq.model.Utente;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/eliminaProdotto")
public class EliminaProdottoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Utente utente = (session != null) ? (Utente) session.getAttribute("utenteLoggato") : null;


        if (utente != null && "admin".equals(utente.getRuolo())) {
            int id = Integer.parseInt(request.getParameter("idProdotto"));
            ProdottoDAO dao = new ProdottoDAO();
            dao.eliminaProdotto(id);
        }

        String referer = request.getHeader("referer");
        response.sendRedirect(referer != null ? referer : request.getContextPath() + "/catalogo");
    }
}