function fazerLogin() {
    const username = document.getElementById('inputEmailLogin').value;
    const password = document.getElementById('inputSenhaLogin').value;
  
    const dadosUsuario = {
      email: username,
      senha: password
    };

    axios.post('https://projetosoftware2.herokuapp.com/auth/login', dadosUsuario).then(response => {
      if (response.data.token != null) {
        localStorage.clear();
        localStorage.setItem('nomeUsuario', response.data.usuario);
        localStorage.setItem('token', response.data.token);
        window.location.href = '../view/inicio.html';
      } else {
        alert('Usuário inválido!');
      }
    }).catch(() => {
      alert('Usuário não encontrado!');
    });
}