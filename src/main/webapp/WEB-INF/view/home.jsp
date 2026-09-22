<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>DeepFreq | Underground & Gear</title>


    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
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

<!-- CAROSELLO -->
<div class="carousel-container">
    <div class="slide active" style="background-image: url('https://images.unsplash.com/photo-1665939108882-f271465fa142?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D');">
        <div class="slide-content">
            <h2>Il suono crudo e diretto.</h2>
            <p>Scopri la nostra selezione di classici hip-hop, jazz e tanto altro</p>
        </div>
    </div>

    <div class="slide" style="background-image: url('https://images.unsplash.com/photo-1505740420928-5e560c06d30e?auto=format&fit=crop&w=1920&q=80');">
        <div class="slide-content">
            <h2>Spingi le basse frequenze.</h2>
            <p>In-Ear Monitors con bassi profondi e cuffie over-ear ad alta fedeltà. L'hardware definitivo per il tuo ascolto.</p>
        </div>
    </div>

    <button class="prev" aria-label="Slide precedente" onclick="cambiaSlide(-1)"><i class="fas fa-chevron-left" aria-hidden="true"></i></button>
    <button class="next" aria-label="Slide successiva" onclick="cambiaSlide(1)"><i class="fas fa-chevron-right" aria-hidden="true"></i></button>
</div>

<!-- GRIGLIA PRODOTTI -->
<main id="in-evidenza">
    <h2 class="section-title">In Evidenza</h2>

    <div class="grid-container">
        <c:if test="${empty dischi}">
            <p style="text-align:center; width: 100%;">Nessun prodotto trovato nel database.</p>
        </c:if>

        <c:forEach var="disco" items="${dischi}">
            <div class="card">
                <div class="card-img-wrapper">
                    <c:if test="${disco.quantitaMagazzino > 0 && disco.quantitaMagazzino <= 5}">
                        <div class="badge">Ultimi Pezzi</div>
                    </c:if>
                    <a href="${pageContext.request.contextPath}/prodotto?id=${disco.id}" aria-label="Visualizza dettagli di ${disco.titolo}">
                        <img src="${pageContext.request.contextPath}/images/${disco.urlImmagine}" alt="${disco.titolo}">
                    </a>
                </div>

                <div class="card-body">
                    <h3 class="titolo" title="${disco.titolo}">
                        <a href="${pageContext.request.contextPath}/prodotto?id=${disco.id}" style="color: inherit; text-decoration: none;">
                                ${disco.titolo}
                        </a>
                    </h3>
                    <p class="artista">${disco.artista}</p>
                    <p class="prezzo">€ ${disco.prezzo}</p>

                    <form action="${pageContext.request.contextPath}/aggiungiCarrello" method="POST">
                        <input type="hidden" name="idProdotto" value="${disco.id}">
                        <button type="submit" class="btn-cart">Aggiungi</button>
                    </form>
                </div>
            </div>
        </c:forEach>
    </div>
</main>

<!-- SCRIPT CAROSELLO -->
<script>
    let slideIndex = 0;
    const slides = document.querySelectorAll('.slide');

    function mostraSlide(index) {
        slides.forEach(slide => slide.classList.remove('active'));
        if (index >= slides.length) slideIndex = 0;
        if (index < 0) slideIndex = slides.length - 1;
        slides[slideIndex].classList.add('active');
    }

    function cambiaSlide(n) {
        slideIndex += n;
        mostraSlide(slideIndex);
    }

    setInterval(() => {
        cambiaSlide(1);
    }, 5000);
</script>

<!-- FOOTER -->
<footer class="site-footer">
    <p>&copy; 2026 DeepFreq. Tutti i diritti riservati.</p>
</footer>
</body>
</html>