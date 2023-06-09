function fazerLogin() {
    const username = document.getElementById('inputEmail').value;
    const password = document.getElementById('inputSenha').value;
  
    const dadosUsuario = {
      email: username,
      senha: password
    };

    fetch('http://localhost:8080/auth/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(dadosUsuario)
    })
      .then(response => response.json())
      .then(data => {
        // Verifica se o login foi bem-sucedido
        if (data.token) {
          // Login bem-sucedido, armazene o token JWT
          const token = data.token;
          // Armazene o token em algum lugar, como no localStorage ou em um cookie
          localStorage.setItem('token', token);
          // Redirecione o usuário para a página principal ou faça outras ações necessárias
          window.location.href = '/index.html';
        } else {
          // Login falhou, exiba uma mensagem de erro para o usuário
          alert('Usuário ou senha incorretos');
        }
      })
      .catch(error => {
        console.error('Ocorreu um erro:', error);
      });
  }