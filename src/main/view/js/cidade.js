$(document).ready(function() {
    carregarEstados();

    $('#inputEstado').change(function() {
        let estadoID = $(this).val();
        let estadoText = $(this).find('option:selected').text();
        localStorage.setItem('estado', estadoText);
        carregarCidades(estadoID);
    });

    $('#inputCidade').change(function() {
        let cidadeText = $(this).find('option:selected').text();
        localStorage.setItem('cidade', cidadeText);
    });

    function carregarEstados() {
        $.ajax({
            url: 'https://servicodados.ibge.gov.br/api/v1/localidades/estados',
            type: 'GET',
            dataType: 'json',
            success: function(data) {
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
