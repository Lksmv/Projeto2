function fazerLogin() {
    const username = document.getElementById('textoEmailLogin').value;
    const password = document.getElementById('textoSenhaLogin').value;
  
    const dadosUsuario = {
      email: username,
      senha: password
    };

    axios.post('https://projetosoftware2.herokuapp.com/auth/login', dadosUsuario).then(response => {
      if (response.data.token != null) {
        window.location.href = '../view/index.html';
      } else {
        alert('Usuário inválido!');
      }
    }).catch(() => {
      alert('Usuário não encontrado!');
    });
}