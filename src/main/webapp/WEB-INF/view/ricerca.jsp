<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>DeepFreq | Risultati Ricerca</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/catalogo.css">
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

<div class="catalog-header">
  <h1 class="catalog-title">Risultati ricerca per: "${queryCercata}"</h1>
</div>

<main class="catalog-page">
  <aside class="sidebar">

    <form id="filterForm" action="${pageContext.request.contextPath}/ricerca" method="GET">

      <input type="hidden" name="q" value="${queryCercata}">

      <div class="filter-group">
        <div class="filter-header">
          <span>Disponibilità</span>
          <i class="fas fa-minus" aria-hidden="true"></i>
        </div>
        <label class="filter-item">
          <input type="checkbox" name="stock" value="in-stock" ${stockSelezionato == 'in-stock' ? 'checked' : ''}> In stock
        </label>
      </div>

      <div class="filter-group">
        <div class="filter-header">
          <span>Genere / Tipo</span>
          <i class="fas fa-minus" aria-hidden="true"></i>
        </div>
        <!-- Vinili -->
        <label class="filter-item"><input type="checkbox" name="genere" value="Hip-Hop" ${generiSelezionati.contains('Hip-Hop') ? 'checked' : ''}> Hip-Hop</label>
        <label class="filter-item"><input type="checkbox" name="genere" value="Jazz" ${generiSelezionati.contains('Jazz') ? 'checked' : ''}> Jazz</label>
        <label class="filter-item"><input type="checkbox" name="genere" value="R&B" ${generiSelezionati.contains('R&B') ? 'checked' : ''}> R&B</label>
        <label class="filter-item"><input type="checkbox" name="genere" value="Elettronica" ${generiSelezionati.contains('Elettronica') ? 'checked' : ''}> Elettronica</label>
        <!-- Audio Gear -->
        <label class="filter-item"><input type="checkbox" name="genere" value="Giradischi" ${generiSelezionati.contains('Giradischi') ? 'checked' : ''}> Giradischi</label>
        <label class="filter-item"><input type="checkbox" name="genere" value="In-Ear Monitors" ${generiSelezionati.contains('In-Ear Monitors') ? 'checked' : ''}> In-Ear Monitors</label>
        <label class="filter-item"><input type="checkbox" name="genere" value="Cuffie Over-Ear" ${generiSelezionati.contains('Cuffie Over-Ear') ? 'checked' : ''}> Cuffie Over-Ear</label>
      </div>

      <div class="filter-group">
        <div class="filter-header">
          <span>Anno di Uscita</span>
          <i class="fas fa-minus" aria-hidden="true"></i>
        </div>
        <label class="filter-item"><input type="checkbox" name="anno" value="2020" ${anniSelezionati.contains('2020') ? 'checked' : ''}> Anni 2020</label>
        <label class="filter-item"><input type="checkbox" name="anno" value="2010" ${anniSelezionati.contains('2010') ? 'checked' : ''}> Anni 2010</label>
        <label class="filter-item"><input type="checkbox" name="anno" value="2000" ${anniSelezionati.contains('2000') ? 'checked' : ''}> Anni 2000</label>
        <label class="filter-item"><input type="checkbox" name="anno" value="1990" ${anniSelezionati.contains('1990') ? 'checked' : ''}> Anni '90</label>
      </div>

      <button type="submit" style="width: 100%; padding: 12px; background-color: #ffd700; color: #111; border: none; font-weight: bold; cursor: pointer; text-transform: uppercase; margin-top: 15px; border-radius: 4px; transition: background 0.3s;">
        Applica Filtri
      </button>


      <a href="${pageContext.request.contextPath}/ricerca?q=${queryCercata}" style="display: block; text-align: center; margin-top: 10px; color: #666; font-size: 0.85em; text-decoration: underline;">Resetta Filtri</a>
    </form>
  </aside>

  <section class="main-content">
    <div class="products-grid">
      <c:forEach var="disco" items="${prodotti}">
        <div class="product-card">
          <div class="product-image-wrapper">
            <a href="${pageContext.request.contextPath}/prodotto?id=${disco.id}" aria-label="Visualizza dettagli di ${disco.titolo}">
              <img src="${pageContext.request.contextPath}/images/${disco.urlImmagine}" alt="${disco.titolo}">
            </a>
          </div>

          <span class="product-artist">${disco.artista}</span>
          <h2 class="product-title">${disco.titolo}</h2>

          <c:choose>
            <c:when test="${disco.quantitaMagazzino > 0}">
              <div class="stock-status"><i class="fas fa-bars" aria-hidden="true"></i> IN STOCK</div>
            </c:when>
            <c:otherwise>
              <div class="stock-status" style="color: #d9534f;"><i class="fas fa-times" aria-hidden="true"></i> OUT OF STOCK</div>
            </c:otherwise>
          </c:choose>

          <div class="product-price">€${disco.prezzo}</div>

          <form action="${pageContext.request.contextPath}/aggiungiCarrello" method="POST" style="display: inline-block; width: 100%; margin-top: 10px;">
            <input type="hidden" name="idProdotto" value="${disco.id}">
            <button type="submit" class="btn-cart" style="width: 100%; padding: 10px; background-color: #ffd700; color: #111; border: none; font-weight: bold; cursor: pointer; text-transform: uppercase;">
              Aggiungi al carrello
            </button>
          </form>

          <!-- CONTROLLI ADMIN -->
          <c:if test="${utenteLoggato != null and utenteLoggato.ruolo == 'admin'}">
            <div style="display: flex; gap: 10px; margin-top: 10px;">
              <a href="${pageContext.request.contextPath}/modificaProdotto?id=${disco.id}" aria-label="Modifica prodotto ${disco.titolo}"
                 style="flex: 1; text-align: center; background: #3498db; color: #fff; padding: 8px; text-decoration: none; font-weight: bold; border-radius: 4px;">
                <i class="fas fa-edit" aria-hidden="true"></i>
              </a>
              <form action="${pageContext.request.contextPath}/eliminaProdotto" method="POST" style="flex: 1;">
                <input type="hidden" name="idProdotto" value="${disco.id}">
                <button type="submit" aria-label="Elimina prodotto ${disco.titolo}" onclick="return confirm('Sei sicuro di voler eliminare questo prodotto?');"
                        style="width: 100%; background: #e74c3c; color: #fff; padding: 8px; border: none; font-weight: bold; border-radius: 4px; cursor: pointer;">
                  <i class="fas fa-trash" aria-hidden="true"></i>
                </button>
              </form>
            </div>
          </c:if>

        </div>
      </c:forEach>
    </div>

    <c:if test="${empty prodotti}">
      <p style="text-align: center; margin-top: 50px;">Nessun prodotto trovato per questa ricerca o con i filtri selezionati.</p>
    </c:if>
  </section>
</main>
<!-- FOOTER -->
<footer class="site-footer">
  <p>&copy; 2026 DeepFreq. Tutti i diritti riservati.</p>
</footer>
</body>
</html>