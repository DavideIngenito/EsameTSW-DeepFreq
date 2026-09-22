<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>DeepFreq | Ordine Completato</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
    <style>
        .success-container {
            max-width: 600px;
            margin: 80px auto;
            text-align: center;
            padding: 50px 30px;
            background: #fff;
            box-shadow: 0 4px 15px rgba(0,0,0,0.08);
            border-top: 4px solid #2e7d32;
        }
        .success-container i {
            font-size: 4em;
            color: #2e7d32;
            margin-bottom: 20px;
        }
        .success-container h1 {
            font-weight: 900;
            text-transform: uppercase;
            margin-bottom: 15px;
            color: #111;
        }
        .success-container p {
            color: #555;
            margin-bottom: 30px;
            font-size: 1.1em;
            line-height: 1.6;
        }
        .btn-return {
            display: inline-block;
            background-color: var(--accent, #ffd700);
            color: #111;
            padding: 15px 30px;
            text-decoration: none;
            font-weight: 800;
            text-transform: uppercase;
            transition: background 0.3s;
        }
        .btn-return:hover {
            background-color: #e6c200;
        }
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

<main>
    <div class="success-container">
        <i class="fas fa-check-circle"></i>
        <h1>Ordine Completato!</h1>
        <p>Il tuo pagamento è andato a buon fine e l'ordine è stato registrato nei nostri sistemi. Riceverai presto i tuoi dischi.</p>
        <a href="${pageContext.request.contextPath}/catalogo" class="btn-return">Torna al Catalogo</a>
    </div>
</main>

<!-- FOOTER -->
<footer class="site-footer">
    <p>&copy; 2026 DeepFreq. Tutti i diritti riservati.</p>
</footer>
</body>
</html>