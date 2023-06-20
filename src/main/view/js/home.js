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
    axios.get('https://projetosoftware2.herokuapp.com/voo').then(response => {
        const resposta = { data: [response] };
        const registros = resposta.data.map((registro) => {
            return {
                cidade: registro.cidade,
                pais: registro.pais,
                hotel: registro.hotel,
                preco: registro.preco,
                descricao: registro.descricao
            };
        });
        montaPacotes(registros);
    });
}

function montaPacotes(registros) {
    const divCentralizaPacotes = document.getElementById('divCentralizaPacotes');

    registros.forEach((registro) => {
        const pacote = document.createElement('div');
        pacote.className = 'pacotes';

        const imagemPacote = document.createElement('img');
        imagemPacote.className = 'imagemPacote';
        imagemPacote.src = '../resources/images/ImagemInicio.png';
        imagemPacote.alt = 'Cidade Destino';

        const textoDentroPacote = document.createElement('div');
        textoDentroPacote.className = 'textoDentroPacote';

        const texto1Pacote = document.createElement('p');
        texto1Pacote.id = 'texto1Pacote';
        texto1Pacote.textContent = `${registro.cidade} · ${registro.pais}`;

        const posicaoTexto2e3 = document.createElement('div');
        posicaoTexto2e3.id = 'posicaoTexto2e3';

        const texto2Pacote = document.createElement('p');
        texto2Pacote.id = 'texto2Pacote';
        texto2Pacote.textContent = registro.hotel;

        const texto3Pacote = document.createElement('p');
        texto3Pacote.id = 'texto3Pacote';
        texto3Pacote.textContent = `R$ ${registro.preco} IDA à VOLTA`;

        const texto4Pacote = document.createElement('p');
        texto4Pacote.id = 'texto4Pacote';
        texto4Pacote.textContent = registro.descricao;

        posicaoTexto2e3.appendChild(texto2Pacote);
        posicaoTexto2e3.appendChild(texto3Pacote);

        textoDentroPacote.appendChild(texto1Pacote);
        textoDentroPacote.appendChild(posicaoTexto2e3);
        textoDentroPacote.appendChild(texto4Pacote);

        pacote.appendChild(imagemPacote);
        pacote.appendChild(textoDentroPacote);

        divCentralizaPacotes.appendChild(pacote);
    });
}
