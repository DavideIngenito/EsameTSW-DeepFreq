package it.unisa.deepfreq.controller;

import it.unisa.deepfreq.dao.UtenteDAO;
import it.unisa.deepfreq.model.PasswordUtils;
import it.unisa.deepfreq.model.Utente;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.regex.Pattern;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final Pattern EMAIL =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}$");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);


        if (session != null && session.getAttribute("utenteLoggato") != null) {

            response.sendRedirect(request.getContextPath() + "/profilo");
            return;
        }


        request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (email != null) {
            email = email.trim();
        }


        if (email == null || email.length() > 100 || !EMAIL.matcher(email).matches()
                || password == null || password.length() < 8) {
            request.setAttribute("errore", "Inserisci un'email valida e una password di almeno 8 caratteri.");
            request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
            return;
        }

        // 1. CIFRATURA: Cifra la password inserita nel form per poterla confrontare
        String passwordCifrata = PasswordUtils.hashPassword(password);

        UtenteDAO dao = new UtenteDAO();


        Utente utente = dao.doLogin(email, passwordCifrata);

        if (utente != null) {

            HttpSession session = request.getSession();

            request.changeSessionId();
            session.setAttribute("utenteLoggato", utente);
            response.sendRedirect(request.getContextPath() + "/home");
        } else {

            request.setAttribute("errore", "Email o password errati.");
            request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
        }
    }
}