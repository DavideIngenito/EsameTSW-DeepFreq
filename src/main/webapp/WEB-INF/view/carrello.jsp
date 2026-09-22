<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="UTF-8">
    <title>DeepFreq | Il tuo Carrello</title>


    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">


    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">

    <style>
        .cart-container { max-width: 800px; margin: 40px auto; padding: 20px; background: #fff; color: #111; border-radius: 8px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); }
        .cart-item { display: flex; justify-content: space-between; align-items: center; border-bottom: 1px solid #eee; padding: 15px 0; }
        .cart-item h4 { margin: 0; font-size: 1.1em; }
        .cart-total { text-align: right; font-size: 1.5em; font-weight: bold; margin-top: 20px; }
        .btn-checkout { background: #ffd700; color: #111; padding: 15px 30px; border: none; font-weight: bold; cursor: pointer; text-transform: uppercase; width: 100%; margin-top: 20px; border-radius: 4px; }
        .btn-checkout:hover { background: #e6c200; }
    </style>


    <script>
        const contextPath = '${pageContext.request.contextPath}';
    </script>
    <!-- Carichiamo il file JavaScript esterno (Punti 9 e 10) -->
    <script src="${pageContext.request.contextPath}/js/carrello.js" defer></script>
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

<!-- CONTENUTO PRINCIPALE -->
<main class="cart-container">
    <h2><i class="fas fa-shopping-bag" aria-hidden="true"></i> Il tuo Carrello</h2>

    <c:choose>
        <c:when test="${empty sessionScope.carrello || sessionScope.carrello.vuoto}">
            <p style="margin-top: 20px;">Il tuo carrello è vuoto. <a href="${pageContext.request.contextPath}/catalogo" style="color: #3498db; text-decoration: underline; font-weight: bold;">Torna agli acquisti</a></p>
        </c:when>

        <c:otherwise>
            <!-- LISTA PRODOTTI -->
            <c:forEach var="prodotto" items="${sessionScope.carrello.prodotti}">
                <div class="cart-item">


                    <div style="flex: 2;">
                        <h4>${prodotto.titolo}</h4>
                        <small>${prodotto.artista}</small>
                    </div>

                    <!-- Input Quantità con chiamata AJAX onchange e accessibilità (Punto 21) -->
                    <div style="flex: 1; text-align: center;">
                        <label for="quantita-${prodotto.id}" style="display:none;">Quantità per ${prodotto.titolo}</label>
                        <input type="number" id="quantita-${prodotto.id}"
                               value="${prodotto.quantitaCarrello}"
                               min="1" max="${prodotto.quantitaMagazzino}"
                               aria-label="Quantità per il prodotto ${prodotto.titolo}"
                               style="width: 60px; padding: 5px; border: 1px solid #ccc; border-radius: 4px;"
                               onchange="aggiornaQuantitaAjax(${prodotto.id})">
                    </div>


                    <div id="totale-riga-${prodotto.id}" style="flex: 1; text-align: right; font-weight: bold;">
                        € ${prodotto.prezzo * prodotto.quantitaCarrello}
                    </div>

                    <!-- 4. Tasto Rimuovi  -->
                    <div style="flex: 0.5; text-align: right;">
                        <form action="${pageContext.request.contextPath}/gestioneCarrello" method="POST" style="display:inline;">
                            <input type="hidden" name="action" value="remove">
                            <input type="hidden" name="idProdotto" value="${prodotto.id}">
                            <button type="submit" aria-label="Rimuovi ${prodotto.titolo} dal carrello" title="Rimuovi" style="background:none; border:none; cursor:pointer; color:#e74c3c; font-size: 1.2em;">
                                <i class="fas fa-trash-alt" aria-hidden="true"></i>
                            </button>
                        </form>
                    </div>

                </div>
            </c:forEach>

            <!-- TOTALE GENERALE CARRELLO -->
            <div id="totale-carrello" class="cart-total">
                Totale: € ${sessionScope.carrello.totale}
            </div>

            <!-- FORM DI PAGAMENTO -->
            <form action="${pageContext.request.contextPath}/checkout" method="POST">
                <button type="submit" class="btn-checkout">Procedi al Pagamento</button>
            </form>

        </c:otherwise>
    </c:choose>
</main>

<!-- FOOTER -->
<footer class="site-footer">
    <p>&copy; 2026 DeepFreq. Tutti i diritti riservati.</p>
</footer>
</body>
</html>