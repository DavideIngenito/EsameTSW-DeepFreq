package it.unisa.deepfreq.controller;

import it.unisa.deepfreq.dao.UtenteDAO;
import it.unisa.deepfreq.model.PasswordUtils;
import it.unisa.deepfreq.model.Utente;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.regex.Pattern;

@WebServlet("/registrazione")
public class RegistrazioneServlet extends HttpServlet {

    private static final Pattern EMAIL =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}$");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/view/registrazione.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String email = request.getParameter("email");
        String password = request.getParameter("password");


        String errore = valida(nome, cognome, email, password);
        if (errore != null) {
            request.setAttribute("errore", errore);
            request.getRequestDispatcher("/WEB-INF/view/registrazione.jsp").forward(request, response);
            return;
        }


        String passwordCifrata = PasswordUtils.hashPassword(password);


        Utente utente = new Utente();
        utente.setNome(nome.trim());
        utente.setCognome(cognome.trim());
        utente.setEmail(email.trim());
        utente.setPassword(passwordCifrata);
        utente.setRuolo("utente");


        UtenteDAO dao = new UtenteDAO();
        if (dao.registraUtente(utente)) {
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            request.setAttribute("errore", "Errore durante la registrazione. Forse l'email è già in uso?");
            request.getRequestDispatcher("/WEB-INF/view/registrazione.jsp").forward(request, response);
        }
    }

    private static boolean vuoto(String s) {
        return s == null || s.trim().isEmpty();
    }

    /** Restituisce il messaggio d'errore, oppure null se i dati sono validi. */
    private static String valida(String nome, String cognome, String email, String password) {
        if (vuoto(nome) || vuoto(cognome) || vuoto(email) || vuoto(password)) {
            return "Tutti i campi sono obbligatori.";
        }
        if (nome.trim().length() > 50 || cognome.trim().length() > 50) {
            return "Nome e cognome possono avere al massimo 50 caratteri.";
        }
        if (email.trim().length() > 100 || !EMAIL.matcher(email.trim()).matches()) {
            return "Inserisci un indirizzo email valido.";
        }
        if (password.length() < 8) {
            return "La password deve avere almeno 8 caratteri.";
        }
        if (password.length() > 100) {
            return "La password può avere al massimo 100 caratteri.";
        }
        return null;
    }
}