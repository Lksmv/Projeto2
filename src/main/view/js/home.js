let mudarVisibilidade = true;

function criarPacote() {
    axios.post('https://projetosoftware2.herokuapp.com/pacote/create', {}, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    }).then(response => {
        if (response.status == 200) {
            localStorage.setItem('idPacoteAtual', response.data.idPacote)
            window.location.href = '../view/passagemAerea.html';
        }
    }).catch(() => {
        alert('Usuário não possui cadastro');
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

function mostrarPacotesCadastrados() {
    const elementoNomeUsuario = document.getElementById("nomeMenuUsuario");
    elementoNomeUsuario.textContent = localStorage.getItem('nomeUsuario');
    axios.get('https://projetosoftware2.herokuapp.com/pacote/promocionais').then(response => {
        const resposta = response.data
        let registros = [];
        for (let i = 0; i < resposta.length; i++) {
            const aux = {
                cidade: resposta[i].cidade,
                dataChegada: resposta[i].dataChegada,
                dataPartida: resposta[i].dataPartida,
                hoteis: resposta[i].hoteis,
                idPacote: resposta[i].idPacote,
                nome: resposta[i].nome,
                pontoTuristicoDTOS: resposta[i].pontoTuristicoDTOS,
                promocional: resposta[i].promocional,
                valor: resposta[i].valor,
                vooDTOS: resposta[i].vooDTOS
            }
            registros.push(aux);
            montaPacotes(registros);
            registros = [];
        }
    });
}

function montaPacotes(registros) {
    const divCentralizaPacotes = document.getElementById('divCentralizaPacotes');

    registros.forEach((registro) => {
        const cidade = registro.cidade;
        const hoteis = registro.hoteis[0].nome;
        const idPacote = registro.idPacote;
        const valor = registro.valor;


        const pacote = document.createElement('div');
        pacote.className = 'pacotes';

        const imagemPacote = document.createElement('img');
        imagemPacote.className = 'imagemPacote';
        imagemPacote.src = `https://source.unsplash.com/800x600/?viagem-${cidade}`;
        imagemPacote.alt = 'Cidade Destino';
        imagemPacote.id = idPacote;
        imagemPacote.addEventListener('click', function(event) {
            if (window.confirm('Deseja incluir o pacote ao seu usuário?')) {
                axios.put(`https://projetosoftware2.herokuapp.com/pessoa/add-pacote?idPacote=${event.target.id}`, {}, {
                    headers: {
                        'Authorization': `Bearer ${localStorage.getItem('token')}`
                    }
                }).then(() => {
                    alert('Pacote salvo para o usuário');
                }).catch(erro => {
                    alert(erro);
                });
            }
        });

        const textoDentroPacote = document.createElement('div');
        textoDentroPacote.className = 'textoDentroPacote';

        const texto1Pacote = document.createElement('p');
        texto1Pacote.id = 'texto1Pacote';
        texto1Pacote.textContent = `${cidade} · ${'Brasil'}`;

        const posicaoTexto2e3 = document.createElement('div');
        posicaoTexto2e3.id = 'posicaoTexto2e3';

        const texto2Pacote = document.createElement('p');
        texto2Pacote.id = 'texto2Pacote';
        texto2Pacote.textContent = hoteis;

        const texto3Pacote = document.createElement('p');
        texto3Pacote.id = 'texto3Pacote';
        texto3Pacote.textContent = `R$ ${valor} IDA à VOLTA`;

        posicaoTexto2e3.appendChild(texto2Pacote);
        posicaoTexto2e3.appendChild(texto3Pacote);

        textoDentroPacote.appendChild(texto1Pacote);
        textoDentroPacote.appendChild(posicaoTexto2e3);

        pacote.appendChild(imagemPacote);
        pacote.appendChild(textoDentroPacote);

        divCentralizaPacotes.appendChild(pacote);
    });
}
