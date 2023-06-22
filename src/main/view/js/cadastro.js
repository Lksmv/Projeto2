function adicionarEventoChange() {
    if (document.getElementById('inputSenhaCadastro').value != document.getElementById('inputConfirmarSenhaCadastro').value) {
        alert('A senha está diferente da confirmação da senha!');
    }
    const botaoEditarNomeUsuario = document.getElementById('botaoCadastrar');
    botaoEditarNomeUsuario.addEventListener("click", function () {
        const params = {
            "nome": document.getElementById('inputNomeCadastro').value,
            "cpf": document.getElementById('inputCpfCadastro').value,
            "email": document.getElementById('inputEmailCadastro').value,
            "telefone": document.getElementById('inputTelefoneCadastro').value,
            "senha": document.getElementById('inputSenhaCadastro').value
        }
        axios.post(`https://projetosoftware2.herokuapp.com/pessoa/cadastro`, params).then(response => {
            if (response.status == 200) {
                alert('Usuário cadastrado com sucesso!');
                window.location.href = '../view/login.html';
            }
        }).catch(erro => {
            alert('Não foi possível salvar o usuário ' + erro);
        });
    });
}