function aggiornaQuantitaAjax(idProdotto) {
    const quantitaInput = document.getElementById('quantita-' + idProdotto);
    let quantita = quantitaInput.value;

    if (quantita < 1) {
        quantita = 1;
        quantitaInput.value = 1;
    }


    fetch(contextPath + '/api/carrello', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: new URLSearchParams({
            'idProdotto': idProdotto,
            'quantita': quantita
        })
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                document.getElementById('totale-riga-' + idProdotto).innerText = '€ ' + data.totaleRiga.toFixed(2);
                document.getElementById('totale-carrello').innerText = 'Totale: € ' + data.totaleCarrello.toFixed(2);
            } else {
                alert("Impossibile aggiornare la quantità: " + data.errore);
            }
        })
        .catch(error => {
            console.error('Errore durante la chiamata AJAX:', error);
            alert("Si è verificato un errore di rete o del server.");
        });
}