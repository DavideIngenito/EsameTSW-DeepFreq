<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>DeepFreq | ${prodotto.titolo}</title>

  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/prodotto.css">
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

<main class="product-detail-container">

  <div class="product-gallery">
    <img src="${pageContext.request.contextPath}/images/${prodotto.urlImmagine}" alt="${prodotto.titolo}">
  </div>

  <div class="product-info-detail">
    <span class="detail-artist">${prodotto.artista}</span>
    <h1 class="detail-title">${prodotto.titolo}</h1>
    <div class="detail-price">€ ${prodotto.prezzo}</div>

    <c:choose>
      <c:when test="${prodotto.quantitaMagazzino > 0}">
        <div class="detail-stock stock-in"><i class="fas fa-bars"></i> In Stock (${prodotto.quantitaMagazzino} copie)</div>
      </c:when>
      <c:otherwise>
        <div class="detail-stock stock-out"><i class="fas fa-times"></i> Out of Stock</div>
      </c:otherwise>
    </c:choose>

    <div class="detail-meta">
      <strong>Genere:</strong> ${prodotto.genere}<br>
      <strong>Anno:</strong> ${prodotto.annoUscita}
    </div>

    <div class="detail-description">
      ${prodotto.descrizione}
    </div>

    <div class="action-buttons">
      <form action="${pageContext.request.contextPath}/aggiungiCarrello" method="POST" style="flex: 3; display: flex;">
        <input type="hidden" name="idProdotto" value="${prodotto.id}">
        <button type="submit" class="btn-add-cart-detail">Aggiungi al Carrello</button>
      </form>

      <form action="${pageContext.request.contextPath}/wishlist" method="POST" style="flex: 1; display: flex;">
        <input type="hidden" name="idProdotto" value="${prodotto.id}">

        <c:choose>
          <c:when test="${isPreferito}">
            <input type="hidden" name="action" value="remove">
            <input type="hidden" name="redirect" value="prodotto?id=${prodotto.id}">
            <button type="submit" class="btn-wishlist" title="Rimuovi dai Preferiti" style="color: #e74c3c;">
              <i class="fas fa-heart"></i>
            </button>
          </c:when>
          <c:otherwise>
            <input type="hidden" name="action" value="add">
            <input type="hidden" name="redirect" value="prodotto?id=${prodotto.id}">
            <button type="submit" class="btn-wishlist" title="Salva nei Preferiti">
              <i class="far fa-heart"></i>
            </button>
          </c:otherwise>
        </c:choose>
      </form>
    </div>
  </div>

  <!-- ================= TRACKLIST (Mostrata solo per i Vinili) ================= -->
  <c:if test="${prodotto.categoria == 'Vinile'}">
    <section class="tracklist-section">
      <h3>Tracklist</h3>
      <c:choose>
        <c:when test="${not empty prodotto.tracce}">
          <c:forEach var="traccia" items="${prodotto.tracce}">
            <div class="track-item">
              <div>
                <span class="track-number">${traccia.numeroTraccia}.</span>
                <span class="track-title">${traccia.titolo}</span>
              </div>
              <span class="track-duration">${traccia.durata}</span>
            </div>
          </c:forEach>
        </c:when>
        <c:otherwise>
          <p style="color: #777777;">Nessuna traccia disponibile per questo prodotto.</p>
        </c:otherwise>
      </c:choose>
    </section>
  </c:if>

</main>

</body>
</html>

</main>
<!-- FOOTER -->
<footer class="site-footer">
  <p>&copy; 2026 DeepFreq. Tutti i diritti riservati.</p>
</footer>
</body>
</html>