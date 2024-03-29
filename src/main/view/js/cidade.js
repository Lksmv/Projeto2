$(document).ready(function() {
    carregarEstados();

    $('#inputEstado').change(function() {
        let estadoID = $(this).val();
        carregarCidades(estadoID);
    });

    function carregarEstados() {
        $.ajax({
            url: 'https://servicodados.ibge.gov.br/api/v1/localidades/estados',
            type: 'GET',
            dataType: 'json',
            success: function(data) {
                data.sort(function(a, b) {
                    var nomeA = a.nome.toUpperCase();
                    var nomeB = b.nome.toUpperCase();
                    if (nomeA < nomeB) {
                        return -1;
                    }
                    if (nomeA > nomeB) {
                        return 1;
                    }
                    return 0;
                });
                let options = '<option value="">Selecione o Estado</option>';
                for (let i = 0; i < data.length; i++) {
                    options += '<option value="' + data[i].id + '">' + data[i].nome + '</option>';
                }
                $('#inputEstado').html(options);
            },
            error: function() {
                alert('Erro ao carregar estados.');
            }
        });
    }

    function carregarCidades(estadoID) {
        if (estadoID === '') {
            $('#inputCidade').html('<option value="">Selecione a Cidade</option>');
            $('#inputCidade').prop('disabled', true);
            return;
        }

        $.ajax({
            url: 'https://servicodados.ibge.gov.br/api/v1/localidades/estados/' + estadoID + '/municipios',
            type: 'GET',
            dataType: 'json',
            data: {
                sort: 'nome'
            },
            success: function(data) {
                let options = '<option value="">Selecione a Cidade</option>';
                for (let i = 0; i < data.length; i++) {
                    options += '<option value="' + data[i].id + '">' + data[i].nome + '</option>';
                }
                $('#inputCidade').html(options);
                $('#inputCidade').prop('disabled', false);
            },
            error: function() {
                alert('Erro ao carregar cidades.');
            }
        });
    }
});

function confirmarCidade() {
    axios.put(`https://projetosoftware2.herokuapp.com/pacote/atualizar?idPacote=${localStorage.getItem('idPacoteAtual')}&cidade=${localStorage.getItem('cidade')}`, {}, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    }).then(() => {
        window.location.href = '../view/escolherHotel.html';
    }).catch(erro => {
        alert(erro);
    });
}

function obterValorOpcao() {
    const selectElement = document.getElementById('inputCidade');
    const opcaoSelecionada = selectElement.options[selectElement.selectedIndex];
    localStorage.setItem('cidade', opcaoSelecionada.text);
}