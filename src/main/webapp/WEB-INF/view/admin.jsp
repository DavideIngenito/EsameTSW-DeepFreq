<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>


<c:if test="${empty utenteLoggato or utenteLoggato.ruolo != 'admin'}">
  <c:redirect url="/home"/>
</c:if>

<!DOCTYPE html>
<html lang="it">
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta charset="UTF-8">
  <title>DeepFreq | Pannello Admin</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
  <style>
    .admin-container { max-width: 700px; margin: 40px auto; padding: 30px; background: #fff; border-radius: 8px; color: #111; box-shadow: 0 4px 10px rgba(0,0,0,0.1); }
    .admin-form label { font-weight: bold; display: block; margin-top: 15px; }
    .admin-form input, .admin-form select, .admin-form textarea { width: 100%; padding: 10px; margin-top: 5px; border: 1px solid #ccc; border-radius: 4px; font-family: inherit; }
    .admin-form button { background: #ffd700; color: #111; padding: 15px; border: none; width: 100%; font-weight: bold; cursor: pointer; margin-top: 25px; text-transform: uppercase; }
    .admin-form button:hover { background: #e6c200; }
    .error-msg { color: #d9534f; font-weight: bold; padding: 10px; background: #fdf7f7; border: 1px solid #d9534f; border-radius: 4px; }
  </style>
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

    <!-- Barra di ricerca -->
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

<main class="admin-container">
  <h2><i class="fas fa-plus-circle" aria-hidden="true"></i> Aggiungi Nuovo Prodotto</h2>

  <c:if test="${not empty errore}">
    <div class="error-msg" role="alert">${errore}</div>
  </c:if>

  <form action="${pageContext.request.contextPath}/admin" method="POST" class="admin-form">
    <label for="titolo">Titolo Prodotto:</label>
    <input type="text" id="titolo" name="titolo" required>

    <label for="artista">Artista o Marca:</label>
    <input type="text" id="artista" name="artista" required>

    <div style="display: flex; gap: 20px;">
      <div style="flex: 1;">
        <label for="categoria">Categoria:</label>
        <select id="categoria" name="categoria">
          <option value="Vinile">Vinile</option>
          <option value="Audio Gear">Audio Gear</option>
        </select>
      </div>
      <div style="flex: 1;">
        <label for="genere">Genere / Tipo:</label>
        <input type="text" id="genere" name="genere" placeholder="Es. Conscious Hip-Hop" required>
      </div>
    </div>

    <div style="display: flex; gap: 20px;">
      <div style="flex: 1;">
        <label for="anno_uscita">Anno di uscita</label>
        <input type="number" id="anno_uscita" name="anno_uscita" min="1900" max="2030" step="1" required>
      </div>
      <div style="flex: 1;">
        <label for="prezzo">Prezzo di vendita (€)</label>
        <input type="number" id="prezzo" name="prezzo" min="0.01" step="0.01" required aria-label="Prezzo del prodotto in euro">
      </div>
      <div style="flex: 1;">
        <label for="quantita">Quantità in Magazzino</label>
        <input type="number" id="quantita" name="quantita" min="0" step="1" required aria-label="Quantità disponibile">
      </div>
    </div>

    <label for="immagine">Nome Immagine:</label>
    <input type="text" id="immagine" name="immagine" placeholder="Es: denzel_melt.jpg" required>

    <label for="tracklist">Tracklist (Titolo - MM:SS. Lascia vuoto per Audio Gear):</label>
    <textarea id="tracklist" name="tracklist" rows="6" placeholder="Es:&#10;Melt Session #1 - 04:01&#10;Walkin - 04:40"></textarea>

    <label for="descrizione">Descrizione:</label>
    <textarea id="descrizione" name="descrizione" rows="4" required></textarea>

    <button type="submit">Salva nel Database</button>
  </form>
</main>

<!-- FOOTER -->
<footer class="site-footer">
  <p>&copy; 2026 DeepFreq. Tutti i diritti riservati.</p>
</footer>
</body>
</html>