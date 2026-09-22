<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>DeepFreq | I Tuoi Preferiti</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/catalogo.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/wishlist.css">
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

<main class="wishlist-container">
  <h1 class="wishlist-title">La Tua Wishlist</h1>

  <div class="products-grid">
    <c:forEach var="disco" items="${preferiti}">
      <div class="product-card">
        <div class="product-image-wrapper">
          <a href="${pageContext.request.contextPath}/prodotto?id=${disco.id}" aria-label="Visualizza dettagli di ${disco.titolo}">
            <img src="${pageContext.request.contextPath}/images/${disco.urlImmagine}" alt="${disco.titolo}">
          </a>
        </div>

        <span class="product-artist">${disco.artista}</span>
        <h2 class="product-title">${disco.titolo}</h2>
        <div class="product-price">€ ${disco.prezzo}</div>

        <form action="${pageContext.request.contextPath}/aggiungiCarrello" method="POST" style="display: inline-block; width: 100%; margin-top: 10px;">
          <input type="hidden" name="idProdotto" value="${disco.id}">
          <button type="submit" class="btn-cart" style="width: 100%; padding: 10px; margin-bottom: 10px; background-color: #ffd700; color: #111; border: none; font-weight: bold; cursor: pointer; text-transform: uppercase;">
            Aggiungi al carrello
          </button>
        </form>

        <form action="${pageContext.request.contextPath}/wishlist" method="POST">
          <input type="hidden" name="idProdotto" value="${disco.id}">
          <input type="hidden" name="action" value="remove">
          <input type="hidden" name="redirect" value="wishlist">
          <button type="submit" class="btn-remove-wishlist" aria-label="Rimuovi ${disco.titolo} dai preferiti"><i class="fas fa-trash-alt" aria-hidden="true"></i> Rimuovi dai preferiti</button>
        </form>
      </div>
    </c:forEach>
  </div>

  <c:if test="${empty preferiti}">
    <p style="text-align: center; margin-top: 50px; color: #666666;">La tua lista dei preferiti è attualmente vuota.</p>
  </c:if>
</main>
<!-- FOOTER -->
<footer class="site-footer">
  <p>&copy; 2026 DeepFreq. Tutti i diritti riservati.</p>
</footer>
</body>
</html>