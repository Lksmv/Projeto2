let map;
let markers = [];
let infoWindowAtual = null;

function initMap() {
    map = new google.maps.Map(document.getElementById("mapaHotel"), {
        center: { lat: -23.5505, lng: -46.6333 },
        zoom: 8,
    });

    adicionarEventoChangeBotao();
}

function searchHotels() {

    for (let i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
    markers = [];

    const cidade = localStorage.getItem('cidade');
    const service = new google.maps.places.PlacesService(document.createElement('div'));
    const request = {
        query: `hotels in ${cidade}`,
        type: 'lodging'
    };

    service.textSearch(request, function (results, status) {
        if (status === google.maps.places.PlacesServiceStatus.OK) {
            let bounds = new google.maps.LatLngBounds();

            for (let hotel of results) {
                let marker = new google.maps.Marker({
                    position: hotel.geometry.location,
                    map: map,
                    title: hotel.name,
                    hotelId: hotel.place_id,
                    formatted_address: hotel.formatted_address
                });

                markers.push(marker);

                bounds.extend(marker.getPosition());

                google.maps.event.addListener(marker, 'click', function () {
                    if (!document.getElementById('checkboxHotel').checked) {
                        const service = new google.maps.places.PlacesService(document.createElement('div'));
                        const request = {
                            placeId: marker.hotelId,
                            fields: ['name', 'price_level', 'rating', 'photos', 'formatted_address']
                        };

                        service.getDetails(request, function (place, status) {
                            if (status === google.maps.places.PlacesServiceStatus.OK) {
                                if (infoWindowAtual) {
                                    infoWindowAtual.close();
                                }
                                const enderecoArray = place.formatted_address.split(',');
                                const [endereco, rua, cidade, cep, pais] = enderecoArray;
                                const enderecoFormatado = `${endereco}<br>${rua}<br>${cidade}<br>${cep}<br>${pais}`;
                                const content = `<div style="background-color: #f2f2f2; padding: 10px;">
                                <h3>${place.name}</h3>
                                <p>Preço diária: R$${getValorHotelAleatorio().toLocaleString('pt-BR')}</p>
                                <p>Avaliação: ${place.rating}</p>
                                <img src="${place.photos[0].getUrl()}" alt="Imagem do hotel" style="max-width: 200px;" />
                                <p>${enderecoFormatado}</p>
                                </div>`;

                                const infoWindow = new google.maps.InfoWindow({
                                    content: content
                                });

                                infoWindowAtual = infoWindow;

                                infoWindow.open(map, marker);
                                document.getElementById('botaoConfirmarHotel').classList.remove('botao-desabilitado');
                                document.getElementById('botaoConfirmarHotel').disabled = false;
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

function getValorHotelAleatorio() {
    const min = 101;
    const max = 3000;

    return Math.floor(Math.random() * (max - min)) + min;
}

function adicionarEventoChangeBotao() {
    const botaoFinalizar = document.getElementById('botaoConfirmarHotel');
    const checkbox = document.getElementById('checkboxHotel');
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
}

function confirmarHotel() {
    const params = {
        "nome": "string",
        "endereco": "string",
        "dataPartida": "2023-06-20T01:28:45.134Z",
        "dataChegada": "2023-06-20T01:28:45.134Z",
        "numero": 0,
        "telefone": 0,
        "diaria": 0
    }
    axios.post(`https://projetosoftware2.herokuapp.com/hotel`, params).then(() => {
        window.location.href = '../view/escolherPontosTuristicos.html';
    }).catch(erro => {
        alert(erro);
    });
}