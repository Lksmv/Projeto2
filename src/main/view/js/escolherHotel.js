let map;
let markers = [];
let infoWindowAtual = null;
let paramsHotel = {};

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
                                let enderecoString = '';
                                let enderecoFormatado = place.formatted_address.split(',');
                                for (let i = 0; i < enderecoFormatado.length; i++) {
                                    enderecoString += `${enderecoFormatado[i]}<br>`;
                                }
                                const valorRandom = getValorHotelAleatorio().toLocaleString('pt-BR');
                                const content = `<div style="background-color: #f2f2f2; padding: 10px;">
                                <h3>${place.name}</h3>
                                <p>Preço diária: R$${valorRandom}</p>
                                <p>Avaliação: ${place.rating}</p>
                                <img src="${place.photos[0].getUrl()}" alt="Imagem do hotel" style="max-width: 200px;" />
                                <p>${enderecoString}</p>
                                </div>`;

                                paramsHotel.NOME = place.name;
                                paramsHotel.VALOR = valorRandom;
                                paramsHotel.ENDERECO = enderecoString;

                                const infoWindow = new google.maps.InfoWindow({
                                    content: content
                                });

                                google.maps.event.addListener(infoWindow, 'closeclick', function (event) {
                                    document.getElementById('botaoConfirmarHotel').classList.add('botao-desabilitado');
                                    document.getElementById('botaoConfirmarHotel').disabled = true;
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
    const checkbox = document.getElementById('checkboxHotel');
    let params = {};
    if (checkbox.checked) {
        return window.location.href = '../view/escolherPontosTuristicos.html';
    }

    params = {
        "nome": paramsHotel.NOME,
        "endereco": paramsHotel.ENDERECO.replace('<br>', ''),
        "dataPartida": new Date().toISOString(),
        "dataChegada": new Date().toISOString(),
        "numero": 0,
        "telefone": 0,
        "diaria": paramsHotel.VALOR.replace('.', '').replace(',', '')
    }

    axios.post(`https://projetosoftware2.herokuapp.com/hotel`, params, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    }).then(response => {
        if (response.status == 200) {
            localStorage.setItem('idHotel', response.data.idHotel);
            axios.post(`https://projetosoftware2.herokuapp.com/pacote/add-hotel?idHotel=${localStorage.getItem('idHotel')}&idPacote=${localStorage.getItem('idPacoteAtual')}`, {}, {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('token')}`
                }
            }).then(() => {
                if (response.status == 200) {
                    window.location.href = '../view/escolherPontosTuristicos.html';
                }
            }).catch(erro => {
                alert(erro);
            });
        }
    }).catch(erro => {
        alert(erro);
    });
}