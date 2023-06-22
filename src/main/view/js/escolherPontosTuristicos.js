let map;
let markers = [];
let infoWindows = [];
let listaPontosTuristicos = [];

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
                            fields: ['name', 'rating', 'photos', 'formatted_address']
                        };

                        service.getDetails(request, function (place, status) {
                            if (status === google.maps.places.PlacesServiceStatus.OK) {
                                let enderecoString = '';
                                let enderecoFormatado = place.formatted_address.split(',');
                                for (let i = 0; i < enderecoFormatado.length; i++) {
                                    enderecoString += `${enderecoFormatado[i]}<br>`;
                                }
                                const content = `<div style="background-color: #f2f2f2; padding: 10px;">
                                <h3>${place.name}</h3>
                                <p>Avaliação: ${place.rating}</p>
                                <img src="${place.photos[0].getUrl()}" alt="Imagem do ponto turístico" style="max-width: 200px;" />
                                <p>Avaliação: ${enderecoString}</p>
                                </div>`;

                                const parameter = {
                                    DS_NOME: place.name,
                                    DS_ENDERECO: enderecoString,
                                    DS_DESCRICAO: ''
                                }
                                listaPontosTuristicos.push(parameter);

                                const infoWindow = new google.maps.InfoWindow({
                                    content: content
                                });

                                infoWindows.push(infoWindow);

                                google.maps.event.addListener(infoWindow, 'closeclick', function (event) {
                                    const index = infoWindows.indexOf(infoWindow);

                                    if (index > -1) {
                                        listaPontosTuristicos.splice(index, 1);
                                        infoWindows.splice(index, 1);
                                    }

                                    if (listaPontosTuristicos.length == 0 && document.getElementById('botaoFinalizarPontosTuristicos').classList.value != 'botao-desabilitado') {
                                        document.getElementById('botaoFinalizarPontosTuristicos').classList.add('botao-desabilitado');
                                        document.getElementById('botaoFinalizarPontosTuristicos').disabled = true;
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
        if (checkbox.checked) {
            botaoFinalizar.classList.remove('botao-desabilitado');
            botaoFinalizar.disabled = false;
            if (infoWindows.length > 0) {
                infoWindows.forEach(element => {
                    infoWindows.pop();
                    listaPontosTuristicos.pop();
                    element.close();
                });
            }
        } else {
            botaoFinalizar.classList.add('botao-desabilitado');
            botaoFinalizar.disabled = true;
        }
    });

    botaoFinalizar.addEventListener('click', () => {
        if (document.getElementById('checkboxPontosTuristicos').checked) {
            return window.location.href = '../view/pacoteCriado.html';
        }
        let id = 0;
        for (let i = 0; i < listaPontosTuristicos.length; i++) {
            const params = {
                "nome": listaPontosTuristicos[i].DS_NOME,
                "endereco": listaPontosTuristicos[i].DS_ENDERECO.replace('<br>', ''),
                "descricao": ''
            }
            axios.post(`https://projetosoftware2.herokuapp.com/ponto-turistico/cadastro`, params, {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('token')}`
                }
            }).then(response => {
                if (response.status == 200) {
                    axios.post(`https://projetosoftware2.herokuapp.com/pacote/add-ponto?idPonto=${response.data.idPontoTuristico}&idPacote=${localStorage.getItem('idPacoteAtual')}`, {}, {
                        headers: {
                            'Authorization': `Bearer ${localStorage.getItem('token')}`
                        }
                    }).then(() => {
                        if (response.status == 200) {
                            id++;

                            if (id == listaPontosTuristicos.length) {
                                window.location.href = '../view/pacoteCriado.html';
                            }
                        }
                    }).catch(erro => {
                        alert(erro);
                    });
                }
            }).catch(erro => {
                alert(erro);
            });
        }
    });
}
