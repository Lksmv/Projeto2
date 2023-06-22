let habilitado = false;
let mudarVisibilidade = true;

function habilitarEdicao(elementoInput, elementoImagem, habilitado) {
    if (habilitado) {
        elementoInput.disabled = false;
        elementoImagem.src = "../resources/images/ImagemConfirmarEditarPerfilUsuario.png";
        elementoImagem.style.backgroundColor = '#F5B963';
    } else {
        atualizarInformacoesUsuario(elementoInput);
        elementoInput.disabled = true;
        elementoImagem.src = "../resources/images/ImagemEditarPerfilUsuario.png";
        elementoImagem.style.backgroundColor = '#6BB39B';
    }
}

function habilitarEventoChange() {
    pegarInformacoesUsuario();

    const elementoNomeUsuario = document.getElementById("nomeMenuUsuario");
    elementoNomeUsuario.textContent = localStorage.getItem('nomeUsuario');;
    const botaoEditarNomeUsuario = document.getElementById('botaoEditarNomeUsuario');
    const botaoEditarCpfUsuario = document.getElementById('botaoEditarCpfUsuario');
    const botaoEditarTelefoneUsuario = document.getElementById('botaoEditarTelefoneUsuario');
    const botaoEditarEmailUsuario = document.getElementById('botaoEditarEmailUsuario');
    const botaoEditarSenhaUsuario = document.getElementById('botaoEditarSenhaUsuario');

    const inputNomeUsuario = document.getElementById('inputNomeUsuario');
    const inputCpfUsuario = document.getElementById('inputCpfUsuario');
    const inputTelefoneUsuario = document.getElementById('inputTelefoneUsuario');
    const inputEmailUsuario = document.getElementById('inputEmailUsuario');
    const inputSenhaUsuario = document.getElementById('inputSenhaUsuario');
    const inputConfirmarSenhaUsuario = document.getElementById('inputConfirmarSenhaUsuario');


    botaoEditarNomeUsuario.addEventListener("click", function () {
        habilitado = !habilitado;
        habilitarEdicao(inputNomeUsuario, botaoEditarNomeUsuario, habilitado);
    });

    botaoEditarCpfUsuario.addEventListener("click", function () {
        habilitado = !habilitado;
        habilitarEdicao(inputCpfUsuario, botaoEditarCpfUsuario, habilitado);
    });

    botaoEditarTelefoneUsuario.addEventListener("click", function () {
        habilitado = !habilitado;
        habilitarEdicao(inputTelefoneUsuario, botaoEditarTelefoneUsuario, habilitado);
    });

    botaoEditarEmailUsuario.addEventListener("click", function () {
        habilitado = !habilitado;
        habilitarEdicao(inputEmailUsuario, botaoEditarEmailUsuario, habilitado);
    });

    botaoEditarSenhaUsuario.addEventListener("click", function () {
        habilitado = !habilitado;
        habilitarEdicao(inputSenhaUsuario, botaoEditarSenhaUsuario, habilitado);
        habilitarEdicao(inputConfirmarSenhaUsuario, botaoEditarSenhaUsuario, habilitado);
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

function atualizarInformacoesUsuario(campo) {
    let string = 'https://projetosoftware2.herokuapp.com/pessoa/update'
    if (campo.id == 'inputNomeUsuario') {
        string += '-nome?nome=' + campo.value
    } else if (campo.id == 'inputCpfUsuario') {
        string += '-cpf?cpf=' + campo.value
    } else if (campo.id == 'inputTelefoneUsuario') { 
        string += '-telefone?telefone=' + campo.value
    } else if (campo.id == 'inputEmailUsuario') { 
        string += '-email?email=' + campo.value
    }
    else if (campo.id == 'inputSenhaUsuario') { 
        string += '-senha?senha=' + campo.value
    }
    axios.put(string, {}, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    }).then(() => {
        alert('Campos atualizados com sucesso!')
    }).catch(erro => {
        //alert(erro);
    });
}

function pegarInformacoesUsuario() {
    axios.get(`https://projetosoftware2.herokuapp.com/pessoa/get-logged-user`, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    }).then(response => {
        document.getElementById('inputNomeUsuario').value = response.data.nome;
        document.getElementById('inputCpfUsuario').value = response.data.cpf;
        document.getElementById('inputTelefoneUsuario').value = response.data.telefone;;
        document.getElementById('inputEmailUsuario').value = response.data.email;
    }).catch(erro => {
        alert(erro);
    });
}