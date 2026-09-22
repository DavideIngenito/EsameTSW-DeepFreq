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
  <title>DeepFreq | Dati Personali</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profilo.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
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

<main class="profile-container" style="margin-top: 40px;">
  <div class="profile-header">
    <h2>I Tuoi Dati</h2>
    <a href="${pageContext.request.contextPath}/profilo" class="btn-logout" style="background-color: #555;">Torna al Profilo</a>
  </div>

  <div class="user-info" style="margin-bottom: 40px;">
    <p><strong>Nome:</strong> ${utenteLoggato.nome}</p>
    <p><strong>Cognome:</strong> ${utenteLoggato.cognome}</p>
    <p><strong>Email:</strong> ${utenteLoggato.email}</p>
  </div>

  <!-- Box Cambio Password -->
  <div class="login-box" style="margin: 0; box-shadow: none; border: 2px solid #eee; width: 100%; max-width: 100%;">
    <h3 style="text-transform: uppercase; margin-bottom: 20px;"><i class="fas fa-lock" aria-hidden="true"></i> Cambia Password</h3>

    <c:if test="${not empty errore}">
      <div class="error-msg" role="alert"><i class="fas fa-times-circle" aria-hidden="true"></i> ${errore}</div>
    </c:if>
    <c:if test="${not empty successo}">
      <div class="success-msg" role="status"><i class="fas fa-check-circle" aria-hidden="true"></i> ${successo}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/dati-personali" method="POST">
      <div class="form-group">
        <label for="vecchiaPassword">Vecchia Password</label>
        <input type="password" id="vecchiaPassword" name="vecchiaPassword" minlength="8" required>
      </div>
      <div class="form-group">
        <label for="nuovaPassword">Nuova Password</label>
        <input type="password" id="nuovaPassword" name="nuovaPassword" minlength="8" required>
      </div>
      <button type="submit" class="btn-login" style="width: auto; padding: 10px 30px;">Aggiorna Password</button>
    </form>
  </div>

</main>

<!-- FOOTER -->
<footer class="site-footer">
  <p>&copy; 2026 DeepFreq. Tutti i diritti riservati.</p>
</footer>
</body>
</html>