let contadorSecoes = 1;
let tamanhoOriginal = 750;
let tamanhoAcrescentar = 350;
let mudarVisibilidade = true;
let arrayFilterIda = [];
let arrayFilterVolta = [];
let horasAleatorias = '';
let horasAleatorias2 = '';
let voos = [];
let voosParams = [];
voosParams.push({ VOLTA: 'V' });
voosParams.push({ IDA: 'I' });
let valorTotalIda = 0;
let valorTotalVolta = 0;
let contadorIda = 0;
let contadorVolta = 0;

function buscarPacotes() {
    limparResultadoTela();
    contadorSecoes = 1
    axios.get(`https://projetosoftware2.herokuapp.com/voo/get-voo?data=${new Date(document.getElementById('inputIda').value).toISOString()}&origem=${document.getElementById('inputSaida').value}&destino=${document.getElementById('inputDestino').value}`).then(response => {
        arrayFilterIda = response.data;
        axios.get(`https://projetosoftware2.herokuapp.com/voo/get-voo?data=${new Date(document.getElementById('inputVolta').value).toISOString()}&origem=${document.getElementById('inputDestino').value}&destino=${document.getElementById('inputSaida').value}`).then(response => {
            arrayFilterVolta = response.data;
            setarValorTotalPacote();

            let section = criaSectionDivGeral();

            criarFilhosSection(section);

            let divGeral = document.getElementById('divGeral');
            divGeral.appendChild(section);

            criarEventoChangeCheckbox();

            document.getElementById('botaoConfirmarPassagem').scrollIntoView({ behavior: 'smooth', block: 'end' });

        }).catch(erro => {
            alert('Não foram encontrado registros');
        });
    }).catch(erro => {
        alert('Não foram encontrado registros');
    });
}

function setarValorTotalPacote() {
    let valorPacote = document.getElementById('valorPacote');

    let divExterno = document.createElement('div');
    divExterno.style.display = 'flex';
    divExterno.style.width = '100vw';
    divExterno.style.justifyContent = 'center';

    let divInterno = document.createElement('div');
    divInterno.style.width = '512px';
    divInterno.style.alignItems = 'center';
    divInterno.style.position = 'absolute';
    divInterno.style.height = '54px';
    divInterno.style.top = tamanhoOriginal + (tamanhoAcrescentar * contadorSecoes) - 40 + 'px';
    divInterno.style.zIndex = '1';
    divInterno.style.background = '#FFFFFF';
    divInterno.style.boxShadow = '0px 1px 2px rgba(30, 30, 32, 0.2), 0px 12px 24px rgba(39, 45, 77, 0.2)';
    divInterno.style.borderRadius = '4px';

    let paragrafo = document.createElement('p');
    paragrafo.id = 'textoValorPassagem';
    paragrafo.textContent = 'R$ ' + '0,00';

    divInterno.appendChild(paragrafo);

    divExterno.appendChild(divInterno);

    valorPacote.appendChild(divExterno);
}


function criarFilhosSection(section) {
    horasAleatorias2 = Math.floor(Math.random() * 6) + 1;
    //IDA
    let div1 = document.createElement('div');
    let divOpcao1;
    let divOpcao4;

    for (let i = 0; i < arrayFilterIda.length; i++) {
        let divIdIda = document.createElement('div');
        divIdIda.setAttribute('id', arrayFilterIda[i].idVoo);
        section.appendChild(divIdIda);

        let pIda = document.createElement('p');
        pIda.setAttribute('id', 'textoIda');
        pIda.textContent = 'IDA';
        section.appendChild(pIda);


        div1.style.position = 'absolute';

        divOpcao1 = criarOpcaoAviao(i, arrayFilterIda[i]);
        divOpcao1.setAttribute('id', 'opcao' + i + 'Aviao');
        divOpcao1.style.display = 'flex';
        divOpcao1.style.flexDirection = 'row';
        divOpcao1.style.width = '666px';
        divOpcao1.style.height = '54px';
        divOpcao1.style.background = '#FFFFFF';
        div1.appendChild(divOpcao1);

        section.appendChild(div1);
        contadorIda++;

    }

    //VOLTA
    let div2 = document.createElement('div');

    for (let i = 0; i < arrayFilterVolta.length; i++) {
        let divIdVolta = document.createElement('div');
        divIdVolta.setAttribute('id', arrayFilterVolta[i].idVoo);
        section.appendChild(divIdVolta);

        let pVolta = document.createElement('p');
        pVolta.setAttribute('id', 'textoVolta');
        pVolta.textContent = 'VOLTA';
        section.appendChild(pVolta);

        div2.style.position = 'absolute';
        div2.style.right = '80px';

        divOpcao4 = criarOpcaoAviao(i, arrayFilterVolta[i]);
        divOpcao4.setAttribute('id', 'opcao' + i + 'Aviao');
        divOpcao4.style.display = 'flex';
        divOpcao4.style.flexDirection = 'row';
        divOpcao4.style.width = '666px';
        divOpcao4.style.height = '54px';
        divOpcao4.style.background = '#FFFFFF';

        div2.appendChild(divOpcao4);
        section.appendChild(div2);
        contadorVolta++;
    }
    divOpcao1.parentNode.childNodes[0].style.marginTop = '95px';
    divOpcao4.parentNode.childNodes[0].style.marginTop = '95px';
    contadorSecoes = contadorIda - contadorIda + 2;
    adicionarBotaoTela();
}

function criaSectionDivGeral() {
    let section = document.createElement('section');
    let secaoId = 'secaoOpcoesPassagem' + contadorSecoes;
    section.setAttribute('id', secaoId);
    section.style.position = 'absolute';
    section.style.width = '100vw';
    section.style.height = '285px';
    section.style.left = 0;
    section.style.top = tamanhoOriginal + (tamanhoAcrescentar * contadorSecoes) + 'px';
    section.style.background = '#FFFFFF';
    return section;
}

function adicionarBotaoTela() {
    let divBotaoConfirmar = document.getElementById('botaoDeConfirmar');

    let divElementExistente = divBotaoConfirmar.querySelector('div');
    if (divElementExistente) {
        divBotaoConfirmar.removeChild(divElementExistente);
    }

    let divElement = document.createElement('div');
    divElement.style.width = '100vw';
    divElement.style.display = 'flex';
    divElement.style.justifyContent = 'center';
    divElement.style.position = 'absolute';
    divElement.style.top = tamanhoOriginal + (tamanhoAcrescentar * contadorSecoes) + 'px';

    let inputElement = document.createElement('input');
    inputElement.type = 'button';
    inputElement.value = 'Confirmar';
    inputElement.id = 'botaoConfirmarPassagem';

    divElement.appendChild(inputElement);

    divBotaoConfirmar.appendChild(divElement);
}

function criarOpcaoAviao(index, data) {
    let divOpcao = document.createElement('div');
    divOpcao.setAttribute('class', 'opcaoAviao');
    divOpcao.setAttribute('id', 'opcaoAviao' + index);

    let checkbox = document.createElement('input');
    checkbox.setAttribute('type', 'checkbox');
    checkbox.setAttribute('class', 'checkboxAviao');
    checkbox.setAttribute('id', data.idVoo);
    divOpcao.appendChild(checkbox);

    let p1 = document.createElement('p');
    p1.setAttribute('class', 'textosAviao');
    p1.style.marginLeft = '12px';
    p1.textContent = data.companhiaAerea;
    divOpcao.appendChild(p1);

    let p2 = document.createElement('p');
    p2.setAttribute('class', 'textosAviao');
    p2.style.marginLeft = '87px';
    p2.textContent = data.origem;
    divOpcao.appendChild(p2);

    let p3 = document.createElement('p');
    p3.setAttribute('class', 'textosAviao');
    p3.style.marginLeft = '17px';
    p3.textContent = new Date(data.dataPartida).toISOString().substring(11, 16);
    divOpcao.appendChild(p3);

    let div2 = document.createElement('div');
    div2.style.textAlign = 'center';
    div2.style.marginLeft = '82px';
    div2.style.marginTop = '-11px';
    div2.style.width = '70px';

    let p4 = document.createElement('p');
    p4.setAttribute('class', 'textosAviao');
    p4.textContent = 'Direto';
    div2.appendChild(p4);

    const horaPartidaAleatoria = adicionarHorasAleatorias(data.dataPartida);

    let p5 = document.createElement('p');
    p5.setAttribute('class', 'textosAviao');
    p5.innerHTML = horasAleatorias + ':00';
    div2.appendChild(p5);

    divOpcao.appendChild(div2);

    let p6 = document.createElement('p');
    p6.setAttribute('class', 'textosAviao');
    p6.style.marginLeft = '82px';
    p6.textContent = horaPartidaAleatoria;
    divOpcao.appendChild(p6);

    let p7 = document.createElement('p');
    p7.setAttribute('class', 'textosAviao');
    p7.style.marginLeft = '17px';
    p7.textContent = data.destino;
    divOpcao.appendChild(p7);

    return divOpcao;
}

function adicionarHorasAleatorias(instant) {
    let data = new Date(instant);
    let horasAtuais = data.getHours();
    horasAleatorias = horasAleatorias2;
    let novasHoras = horasAtuais + horasAleatorias;

    data.setHours(novasHoras);

    let novaDataFormatada = data.toISOString().substring(11, 16);

    return novaDataFormatada;
}

function criarFooter() {

    let oldFooter = document.getElementById("fimPacote");

    if (oldFooter) {
        oldFooter.remove();
    }
    let footer = document.createElement("footer");
    footer.id = "fimPacote";
    footer.style.position = "absolute";
    footer.style.width = "100vw";
    footer.style.height = "85px";
    footer.style.left = "0";
    footer.style.top = "1846px";
    footer.style.background = "#252525";

    let divCentralizaCreditos = document.createElement("div");
    divCentralizaCreditos.id = "divCentralizaCreditos";

    let pogViagensFooter = document.createElement("p");
    pogViagensFooter.id = "pogViagensFooter";
    pogViagensFooter.textContent = "POG VIAGENS";

    let pogViagensCreditos = document.createElement("p");
    pogViagensCreditos.id = "pogViagensCreditos";
    pogViagensCreditos.textContent = "© 2023 POG Viagens";

    let pogViagensCreditos2 = document.createElement("p");
    pogViagensCreditos2.style.color = "#252525";
    pogViagensCreditos2.textContent = "POG VIAGENS";

    divCentralizaCreditos.appendChild(pogViagensFooter);
    divCentralizaCreditos.appendChild(pogViagensCreditos);
    divCentralizaCreditos.appendChild(pogViagensCreditos2);
    footer.appendChild(divCentralizaCreditos);

    document.getElementById('footerAqui').appendChild(footer);
}

function limparResultadoTela() {
    document.getElementById('divGeral').innerHTML = '';
    document.getElementById('valorPacote').innerHTML = '';
}

function criarEventoChangeCheckbox() {
    const checkboxes = document.querySelectorAll('.opcaoAviao input[type="checkbox"]');

    checkboxes.forEach((checkbox) => {
        checkbox.addEventListener('click', (event) => {
            const clickedCheckbox = event.target;
            const parentSection = clickedCheckbox.parentNode.parentNode;
            const checkboxesInSection = parentSection.querySelectorAll('.opcaoAviao input[type="checkbox"]');

            checkboxesInSection.forEach((cb) => {
                if (cb !== clickedCheckbox) {
                    cb.checked = false;
                } else {
                    if (cb.parentNode.parentNode.previousElementSibling.textContent == 'IDA') {
                        voosParams[0].id = cb.id;
                    } else {
                        voosParams[1].id = cb.id;
                    }
                    localStorage.setItem('idVooClicado', JSON.stringify(voosParams));
                }
            });

            const idVooClicado = JSON.parse(localStorage.getItem('idVooClicado'));
            arrayFilterIda.forEach(element => {
                if (element.idVoo == idVooClicado[0].id) {
                    valorTotalIda = element.valor;
                }
            });

            arrayFilterVolta.forEach(element => {
                if (element.idVoo == idVooClicado[1].id) {
                    valorTotalVolta = element.valor;
                }
            });

            let valorTotalPassagens = valorTotalIda + valorTotalVolta
            let numeroPassageiros = document.getElementById('inputPassageiros').value || 1
            let valorMultiplicadoPassagens = valorTotalPassagens * numeroPassageiros;

            document.getElementById('textoValorPassagem').textContent = valorMultiplicadoPassagens.toLocaleString('pt-BR', {
                style: 'currency',
                currency: 'BRL'
            });
        });
    });
}

function redirecionarInformacoesPassagem() {
    const json = localStorage.getItem('idVooClicado');
    const data = JSON.parse(json);
    const idsVetor = []

    const ids = data.map(({ id }) => id);
    ids.forEach(element => {
        idsVetor.push(element);
    });

    const params = {
        "idVoos": idsVetor,
        "idPacote": localStorage.getItem('idPacoteAtual')
    }
    axios.put(`https://projetosoftware2.herokuapp.com/pacote/atualizar?idPacote=${localStorage.getItem('idPacoteAtual')}&qntPessoa=${document.getElementById('inputPassageiros').value || 1}`, {}, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    }).then(() => {
        axios.post(`https://projetosoftware2.herokuapp.com/pacote/add-voo`, params, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            }
        }).then(() => {
            window.location.href = '../view/cidade.html';
        }).catch(erro => {
            alert(erro);
        });
    }).catch(erro => {
        alert(erro);
    });
}

function ajustarVisibilidadePerfil() {
    const menuUsuario = document.getElementById('menuUsuario');

    if (mudarVisibilidade) {
        menuUsuario.style.display = "block"
        mudarVisibilidade = false;
    } else {
        menuUsuario.style.display = "none";
        mudarVisibilidade = true;
    }
}

function getValorHotelAleatorio() {
    const min = 101;
    const max = 3000;

    return Math.floor(Math.random() * (max - min)) + min;
}

function colocarNomeUsuario() {
    const elementoNomeUsuario = document.getElementById("nomeMenuUsuario");

    elementoNomeUsuario.textContent = localStorage.getItem('nomeUsuario');;
}