let contadorSecoes = 1;
let tamanhoOriginal = 750;
let tamanhoAcrescentar = 350;

function buscarPacotes() {
    limparResultadoTela();
    contadorSecoes = 1
    valorTotal = 0;
    axios.get('https://projetosoftware2.herokuapp.com/voo').then(response => {
        let arrayFilter = response.data.filter(element => element.origem == document.getElementById('inputSaida').value &&
            element.destino == document.getElementById('inputDestino').value);

        let arrayFilterDatas = arrayFilter.filter(element => filtrarDatasTela(element.dataPartida, 'I') || filtrarDatasTela(element.dataChegada, 'V'));

        arrayFilterDatas.forEach(data => {
            setarValorTotalPacote(data);

            let section = criaSectionDivGeral();

            criarFilhosSection(section, data);

            let divGeral = document.getElementById('divGeral');
            divGeral.appendChild(section);

            adicionarBotaoTela();

            document.getElementById('botaoConfirmarPassagem').scrollIntoView({ behavior: 'smooth', block: 'end' });
        })
    }).catch(() => {
        alert('Erro!');
    });
}

function setarValorTotalPacote(data) {
    let valorTotal = 0;
    valorTotal += data.valor;

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
    paragrafo.textContent = 'R$ ' + valorTotal.toLocaleString('pt-BR');

    divInterno.appendChild(paragrafo);

    divExterno.appendChild(divInterno);

    valorPacote.appendChild(divExterno);
}


function criarFilhosSection(section, data) {
    //IDA
    let pIda = document.createElement('p');
    pIda.setAttribute('id', 'textoIda');
    pIda.textContent = 'IDA';
    section.appendChild(pIda);

    let div1 = document.createElement('div');
    div1.style.position = 'absolute';

    let divOpcao1 = criarOpcaoAviao(1, data);
    divOpcao1.setAttribute('id', 'opcao1Aviao');
    divOpcao1.style.marginTop = '95px';
    div1.appendChild(divOpcao1);

    let divOpcao2 = criarOpcaoAviao(2, data);
    divOpcao2.setAttribute('id', 'opcao2Aviao');
    div1.appendChild(divOpcao2);

    let divOpcao3 = criarOpcaoAviao(3, data);
    divOpcao3.setAttribute('id', 'opcao3Aviao');
    div1.appendChild(divOpcao3);

    section.appendChild(div1);

    //VOLTA
    let pVolta = document.createElement('p');
    pVolta.setAttribute('id', 'textoVolta');
    pVolta.textContent = 'VOLTA';
    section.appendChild(pVolta);

    let div2 = document.createElement('div');
    div2.style.position = 'absolute';
    div2.style.right = '80px';

    let divOpcao4 = criarOpcaoAviao(4, data);
    divOpcao4.setAttribute('id', 'opcao4Aviao');
    divOpcao4.style.marginTop = '95px';
    div2.appendChild(divOpcao4);

    let divOpcao5 = criarOpcaoAviao(5, data);
    divOpcao5.setAttribute('id', 'opcao5Aviao');
    div2.appendChild(divOpcao5);

    let divOpcao6 = criarOpcaoAviao(6, data);
    divOpcao6.setAttribute('id', 'opcao6Aviao');
    div2.appendChild(divOpcao6);

    section.appendChild(div2);
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
    contadorSecoes++;
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

    let { horaFormatada1, diferencaFormatada, horaFormatada2 } = getDatas(data);

    let divOpcao = document.createElement('div');
    divOpcao.setAttribute('class', 'opcaoAviao');
    divOpcao.setAttribute('id', 'opcaoAviao' + index);

    let checkbox = document.createElement('input');
    checkbox.setAttribute('type', 'checkbox');
    checkbox.setAttribute('class', 'checkboxAviao');
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
    p3.textContent = horaFormatada1;
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

    let p5 = document.createElement('p');
    p5.setAttribute('class', 'textosAviao');
    p5.innerHTML = diferencaFormatada;
    div2.appendChild(p5);

    divOpcao.appendChild(div2);

    let p6 = document.createElement('p');
    p6.setAttribute('class', 'textosAviao');
    p6.style.marginLeft = '82px';
    p6.textContent = horaFormatada2;
    divOpcao.appendChild(p6);

    let p7 = document.createElement('p');
    p7.setAttribute('class', 'textosAviao');
    p7.style.marginLeft = '17px';
    p7.textContent = data.destino;
    divOpcao.appendChild(p7);

    return divOpcao;
}

function getDatas(data) {
    const partida = new Date(data.dataPartida);
    const chegada = new Date(data.dataChegada);

    const diferencaEmMilissegundos = chegada - partida;

    const horas = Math.floor(diferencaEmMilissegundos / (1000 * 60 * 60));
    const minutos = Math.floor((diferencaEmMilissegundos % (1000 * 60 * 60)) / (1000 * 60));

    let diferencaFormatada;
    if (minutos <= 0) {
        diferencaFormatada = `${horas}h`;
    } else {
        diferencaFormatada = `${horas}h ${minutos}min`;
    }

    const horas1 = ("0" + partida.getHours()).slice(-2);
    const minutos1 = ("0" + partida.getMinutes()).slice(-2);
    const horaFormatada1 = `${horas1}:${minutos1}`;

    const horas2 = ("0" + chegada.getHours()).slice(-2);
    const minutos2 = ("0" + chegada.getMinutes()).slice(-2);
    const horaFormatada2 = `${horas2}:${minutos2}`;
    return { horaFormatada1, diferencaFormatada, horaFormatada2 };
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

function filtrarDatasTela(data, ehIdaVolta) {
    //Data Ida
    if (ehIdaVolta == 'I') {
        const dataIda1 = document.getElementById('inputIda').value;
        const dataIda2 = data.substring(0, 10);
        return dataIda1 == dataIda2
    } else {
        //Data Volta
        const dataVolta1 = document.getElementById('inputVolta').value;
        const dataVolta2 = data.substring(0, 10);
        return dataVolta1 == dataVolta2;
    }
}