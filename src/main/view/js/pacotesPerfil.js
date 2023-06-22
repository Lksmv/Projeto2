let mudarVisibilidade = true
let excluirPacotes = 0;

function criarNovoPacote(element) {
    let divPai = document.getElementById("meusPacotes");

    let novaDiv = document.createElement("div");

    let numeroPacote = divPai.childElementCount + 1;
    novaDiv.id = "pacoteCriado" + numeroPacote;
    novaDiv.className = 'pacoteCriado';

    let pontosTuristicos = '';

    element.pontoTuristicoDTOS.forEach(element => {
        pontosTuristicos += element.nome + '|';
    });

    pontosTuristicos = pontosTuristicos.substring(0, pontosTuristicos.length - 1);

    const dataIda = new Date(element.vooDTOS[0].dataPartida);
    const dataVolta = new Date(element.vooDTOS[1].dataPartida);

    const ano = dataIda.getFullYear();
    const ano2 = dataVolta.getFullYear();
    const mes = ("0" + (dataIda.getMonth() + 1)).slice(-2);
    const mes2 = ("0" + (dataVolta.getMonth() + 1)).slice(-2);
    const dia = ("0" + dataIda.getDate()).slice(-2);
    const dia2 = ("0" + dataVolta.getDate()).slice(-2);

    const dataFormatadaIda = `${dia}/${mes}/${ano}`;

    const dataFormatadaVolta = `${dia2}/${mes2}/${ano2}`;



    const data1String = dataFormatadaIda;
    const data2String = dataFormatadaVolta;
    
    const data1 = new Date(converterDataFormato(data1String));
    const data2 = new Date(converterDataFormato(data2String));
    
    const diferencaEmMilissegundos = Math.abs(data2 - data1);
    
    const diferencaEmDias = Math.ceil(diferencaEmMilissegundos / (1000 * 60 * 60 * 24));

    novaDiv.innerHTML = `
    <div style="display: flex; flex-direction: row; width: 970px; justify-content: space-between;">
    <input type="checkbox" class="checkboxSelecionarPacote" id="${element.idPacote}">
    <p style="margin: 10px; color: #F5B963;">${element.nome}</p>
        </div>

        <div id="conteudoPacote">
            <div id="passagemEscolhidaPacote">
                <div>
                    <p style="width: 200px;">Companhia Aérea:</p>
                    <p style="width: 200px;">${element.vooDTOS[0].companhiaAerea}</p>
                </div>
                <div>
                    <p style="width: 200px;">Origem:</p>
                    <p style="width: 200px;">${element.vooDTOS[0].origem}</p>
                </div>
                <div>
                    <p style="width: 200px;">Destino:</p>
                    <p style="width: 200px;">${element.vooDTOS[0].destino}</p>
                </div>
                <div>
                    <p style="width: 200px;">Valor Total:</p>
                    <p style="width: 200px;">${element.valor.toLocaleString('pt-BR', {
                        style: 'currency',
                        currency: 'BRL'
                    })}</p>
                </div>
            </div>
            <div id="passagemEscolhidaPacote">
                <div>
                    <p style="width: 200px;">Ida</p>
                    <p style="width: 200px;">${dataFormatadaIda}</p>
                </div>
                <div>
                    <p style="width: 200px;">Volta</p>
                    <p style="width: 200px;">${dataFormatadaVolta}</p>
                </div>
                <div>
                    <p style="width: 200px;">Número de Passageiros</p>
                    <p style="width: 200px;">${element.qntPessoa}</p>
                </div>
                <div>
                    <p style="width: 200px;">Duração:</p>
                    <p style="width: 200px;">${diferencaEmDias} Dias</p>
                </div>
            </div>

            <div id="hotelEscolhidoPacote">
                <div>
                    <p style="width: 200px;">Nome do Hotel:</p>
                    <p style="width: 200px;">${element.hoteis[0].nome}</p>
                </div>
                <div>
                    <p style="width: 200px;">Valor Diária:</p>
                    <p style="width: 200px;">${element.hoteis[0].diaria.toLocaleString('pt-BR', {
                        style: 'currency',
                        currency: 'BRL'
                    })}</p>
                </div>
                <div>
                    <p style="width: 440px;">Endereço:</p>
                    <p style="width: 440px;">${element.hoteis[0].endereco}</p>
                </div>
            </div>

            <div id="pontosTuristicosPacote">
                <div>
                    <p style="width: 840px;">Pontos Turísticos:</p>
                    <p style="width: 840px;">${pontosTuristicos}</p>
                </div>
            </div>

            <p>Clique <a href="https://123milhas.com" style="color: #6BB39B;" target="_blank">aqui</a>
                comprar a passagem e reservar o hotel do pacote!</p>
        </div>
    `;

    divPai.appendChild(novaDiv);
    document.getElementById(novaDiv.id).querySelector('input').addEventListener("click", function () {
        possuiCheckboxMarcados();
    });
}

function mostrarPacotesTela() {
    const elementoNomeUsuario = document.getElementById("nomeMenuUsuario");
    elementoNomeUsuario.textContent = localStorage.getItem('nomeUsuario');;
    colocarOnChangeCampoRemover();
    axios.get(`https://projetosoftware2.herokuapp.com/pessoa/pacotes`, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    }).then(response => {
        response.data.forEach(element => {
            criarNovoPacote(
                element
            );
        });
    }).catch(erro => {
        alert(erro);
    });
}

function colocarOnChangeCampoRemover() {
    let botaoRemoverPacote = document.getElementById('botaoRemoverPacote');
    botaoRemoverPacote.addEventListener('click', () => {
        if (window.confirm('Deseja excluir o pacote do seu usuário?')) {
            axios.delete(`https://projetosoftware2.herokuapp.com/pacote?idPacote=${excluirPacotes}`, {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('token')}`
                }
            }).then(() => {
                alert('Pacote excluído com sucesso!');
                window.location.reload();
            }).catch(erro => {
                alert(erro);
            });
        }
    });
}

function possuiCheckboxMarcados() {
    let botaoRemoverPacote = document.getElementById('botaoRemoverPacote');
    for (let i = 1; i <= document.getElementById('meusPacotes').childElementCount; i++) {
        let elementId = 'pacoteCriado' + i;
        let element = document.getElementById(elementId);

        if (element) {
            let input = element.querySelector('input');
            if (input.checked) {
                excluirPacotes = input.id;
                botaoRemoverPacote.disabled = false;
                botaoRemoverPacote.classList.remove('botao-desabilitado');
                break;
            } else {
                botaoRemoverPacote.disabled = true;
                botaoRemoverPacote.classList.add('botao-desabilitado');
                excluirPacotes = 0;
            }
        }
    }
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

function converterDataFormato(dataString) {
    const partes = dataString.split("/");
    return `${partes[1]}/${partes[0]}/${partes[2]}`;
}