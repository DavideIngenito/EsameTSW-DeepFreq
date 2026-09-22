<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<c:if test="${empty utenteLoggato}">
    <c:redirect url="/login"/>
</c:if>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="UTF-8">
    <title>DeepFreq | Profilo Utente</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profilo.css">
</head>
<body>

<!-- HEADER -->
<header>
    <a href="${pageContext.request.contextPath}/home" class="logo" aria-label="Torna alla Home">
        <img src="${pageContext.request.contextPath}/images/logo_deepfreq.png" alt="DeepFreq Logo">
    </a>

    <nav class="nav-links">
        <a href="${pageContext.request.contextPath}/nuovi-arrivi">Nuovi Arrivi</a>
        <a href="${pageContext.request.contextPath}/catalogo">Catalogo</a>
        <a href="${pageContext.request.contextPath}/audiogear">Audio Gear</a>
    </nav>

    <div class="header-icons" style="display: flex; align-items: center; gap: 15px;">
        <!-- Barra di ricerca accessibile -->
        <form action="${pageContext.request.contextPath}/ricerca" method="GET" style="display: flex; align-items: center; border-bottom: 1px solid #ffffff;">
            <label for="search-input-home" style="display:none;">Cerca nel sito</label>
            <input type="text" id="search-input-home" name="q" placeholder="Cerca..." required aria-label="Cerca nel sito" style="border: none; outline: none; background: transparent; width: 120px; font-family: 'Montserrat', sans-serif; font-size: 0.9em; color: #ffffff;">
            <button type="submit" title="Cerca" aria-label="Avvia la ricerca" style="background: none; border: none; cursor: pointer; color: #ffffff;">
                <i class="fas fa-search" aria-hidden="true"></i>
            </button>
        </form>

        <!-- Wishlist -->
        <a href="${pageContext.request.contextPath}/wishlist" title="Preferiti" aria-label="Vai alla tua lista dei desideri">
            <i class="far fa-heart" aria-hidden="true"></i>
        </a>

        <!-- Omino Utente (Dinamico) -->
        <c:choose>
            <c:when test="${not empty sessionScope.utenteLoggato}">
                <a href="${pageContext.request.contextPath}/profilo" title="Il mio Profilo" aria-label="Vai alla tua area personale">
                    <i class="fas fa-user-check" style="color: #ffd700;" aria-hidden="true"></i>
                </a>
                <a href="${pageContext.request.contextPath}/logout" title="Esci" aria-label="Effettua il logout">
                    <i class="fas fa-sign-out-alt" aria-hidden="true"></i>
                </a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/login" title="Accedi" aria-label="Accedi al tuo account">
                    <i class="fas fa-user" aria-hidden="true"></i>
                </a>
            </c:otherwise>
        </c:choose>

        <!-- Carrello -->
        <a href="${pageContext.request.contextPath}/carrello" title="Carrello" aria-label="Vai al carrello">
            <i class="fas fa-shopping-bag" aria-hidden="true"></i>
        </a>
    </div>
</header>

<main class="profile-container">

    <div class="profile-header">
        <h2>Benvenuto, ${utenteLoggato.nome}</h2>
        <a href="${pageContext.request.contextPath}/logout" class="btn-logout">Logout</a>
    </div>

    <!-- Pannello Admin -->
    <c:if test="${utenteLoggato.ruolo == 'admin'}">
        <div class="admin-panel">
            <i class="fas fa-cog" aria-hidden="true"></i> Modalità Amministratore
            <a href="${pageContext.request.contextPath}/admin">Gestisci Catalogo (Aggiungi Prodotti)</a>
        </div>
    </c:if>

    <!-- Griglia sezioni profilo -->
    <div class="dashboard-grid">

        <!-- I tuoi dati -->
        <a href="${pageContext.request.contextPath}/dati-personali" class="dashboard-card">
            <i class="fas fa-id-card" aria-hidden="true"></i>
            <h3>I Tuoi Dati</h3>
            <p>Gestisci le tue informazioni personali, la tua email e la password.</p>
        </a>

        <!-- Preferiti -->
        <a href="${pageContext.request.contextPath}/wishlist" class="dashboard-card">
            <i class="fas fa-heart" aria-hidden="true"></i>
            <h3>Preferiti</h3>
            <p>Accedi alla tua Wishlist e tieni d'occhio i dischi che vuoi comprare.</p>
        </a>

        <!-- Carrello -->
        <a href="${pageContext.request.contextPath}/carrello" class="dashboard-card-full">
            <i class="fas fa-box" aria-hidden="true"></i>
            <div class="card-content">
                <h3>Carrello </h3>
                <p>Visualizza il tuo carrello</p>
            </div>
        </a>

    </div>

</main>

<!-- FOOTER -->
<footer class="site-footer">
    <p>&copy; 2026 DeepFreq. Tutti i diritti riservati.</p>
</footer>

</body>
</html>