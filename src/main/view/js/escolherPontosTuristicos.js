let map;
let markers = [];
let infoWindows = [];

function initMap() {
    map = new google.maps.Map(document.getElementById("mapaPontosTuristicos"), {
        center: { lat: -23.5505, lng: -46.6333 },
        zoom: 8,
    });

    adicionarEventoChangeBotao();
}

function buscarPontosTuristicos() {

    for (let i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
    markers = [];

    const cidade = localStorage.getItem('cidade');
    const service = new google.maps.places.PlacesService(document.createElement('div'));
    const request = {
        query: `tourist spots in ${cidade}`,
        type: 'tourist_attraction'
    };

    service.textSearch(request, function (results, status) {
        if (status === google.maps.places.PlacesServiceStatus.OK) {
            let bounds = new google.maps.LatLngBounds();

            for (let spot of results) {
                let marker = new google.maps.Marker({
                    position: spot.geometry.location,
                    map: map,
                    title: spot.name,
                    spotId: spot.place_id
                });

                markers.push(marker);

                bounds.extend(marker.getPosition());

                google.maps.event.addListener(marker, 'click', function () {
                    if (!document.getElementById('checkboxPontosTuristicos').checked) {
                        const service = new google.maps.places.PlacesService(document.createElement('div'));
                        const request = {
                            placeId: marker.spotId,
                            fields: ['name', 'rating', 'photos']
                        };

                        service.getDetails(request, function (place, status) {
                            if (status === google.maps.places.PlacesServiceStatus.OK) {
                                const content = `<div style="background-color: #f2f2f2; padding: 10px;">
                                <h3>${place.name}</h3>
                                <p>Avaliação: ${place.rating}</p>
                                <img src="${place.photos[0].getUrl()}" alt="Imagem do ponto turístico" style="max-width: 200px;" />
                                </div>`;

                                const infoWindow = new google.maps.InfoWindow({
                                    content: content
                                });

                                infoWindows.push(infoWindow);

                                google.maps.event.addListener(infoWindow, 'closeclick', function (event) {
                                    const index = infoWindows.indexOf(infoWindow);

                                    if (index > -1) {
                                        infoWindows.splice(index, 1);
                                    }
                                });

                                infoWindow.open(map, marker);
                                document.getElementById('botaoFinalizarPontosTuristicos').classList.remove('botao-desabilitado');
                                document.getElementById('botaoFinalizarPontosTuristicos').disabled = false;
                            } else {
                                console.error(status);
                            }
                        });
                    } else {
                        infoWindowAtual = null;
                    }
                });
            }

            map.fitBounds(bounds);
        } else {
            console.error(status);
        }
    });
}

function adicionarEventoChangeBotao() {
    const botaoFinalizar = document.getElementById('botaoFinalizarPontosTuristicos');
    const checkbox = document.getElementById('checkboxPontosTuristicos');
    checkbox.addEventListener('click', () => {
        if (infoWindowAtual) {
            infoWindowAtual.close();
        }
        if (checkbox.checked) {
            botaoFinalizar.classList.remove('botao-desabilitado');
            botaoFinalizar.disabled = false;
        } else {
            botaoFinalizar.classList.add('botao-desabilitado');
            botaoFinalizar.disabled = true;
        }
    });

    botaoFinalizar.addEventListener('click', () => {
        infoWindows.forEach(element => {
            const regex = /<h3>(.*?)<\/h3>/;
            const match = element.content.match(regex);

            if (match && match.length >= 2) {
                const conteudoH3 = match[1];
                //Requisição para enviar pro banco
            }
        })
    });
}
