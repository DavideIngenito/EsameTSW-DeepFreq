package it.unisa.deepfreq.controller;

import it.unisa.deepfreq.dao.ProdottoDAO;
import it.unisa.deepfreq.model.Prodotto;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ProdottoDAO prodottoDAO = new ProdottoDAO();


        List<Prodotto> ultimiArrivi = prodottoDAO.estraiTutti();


        request.setAttribute("dischi", ultimiArrivi);


        request.getRequestDispatcher("/WEB-INF/view/home.jsp").forward(request, response);
    }
}