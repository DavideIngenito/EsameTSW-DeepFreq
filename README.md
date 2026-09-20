# EsameTSW_DeepFreq
Progetto realizzato per l'esame di Tecnologie Software per il WEB - resto 2 2025/2026, del corso di laurea in Informatica presso l'Università degli Studi di Salerno.
**DeepFreq** è una piattaforma di e-commerce dedicata alla vendita di dischi in vinile e hardware audio ad alta fedeltà (giradischi, in-ear monitors, cuffie over-ear). 


## 🛠 Tecnologie e Architettura
Il progetto è sviluppato seguendo il pattern architetturale **MVC** (Model-View-Controller) per separare la logica di business dalla presentazione.

* **Backend:** Java 
* **Frontend:** HTML, CSS, JSP, JSTL
* **Database:** MySQL (interfacciato tramite pattern DAO e JDBC)
* **Server di Deploy:** Apache Tomcat 11.0 (Standalone configurato in HTTPS)

## ✨ Funzionalità Principali

### Utente / Cliente
* **Ricerca e Filtri:** Catalogo navigabile con ricerca testuale dinamica e filtri incrociati (Genere, Anno di uscita, Disponibilità in magazzino).
* **Gestione Carrello e Wishlist:** Aggiunta e rimozione di prodotti dal carrello e lista dei desideri per utenti registrati.
* **Area Personale:** Aggiornamento dei dati anagrafici e modifica sicura della password.

### Amministratore
* **Dashboard Prodotti:** Operazioni di CRUD (Creazione, Lettura, Aggiornamento, Eliminazione logica/fisica) sull'intero catalogo.
* **Gestione Tracklist:** Inserimento e formattazione dinamica delle tracce audio per le schede dei vinili.
