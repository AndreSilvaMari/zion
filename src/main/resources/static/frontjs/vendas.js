var id;
var table;
var modalController;
var carrinho = "vazio";
var codigoConsultado;
var produtos = [];
window.onload = function () {

    renderProdutos();

    $('#tabelaProdutos').DataTable({
        'paging': true,
        'lengthChange': true,
        'searching': true,
        'ordering': true,
        'info': true,
        'autoWidth': false,
        'dom': 'B<"top"l>frti<"top"p><"clear">',
        'buttons': [{'extend': 'print', 'text': 'Imprimir Dados', 'className': 'btn btn-default'}],
        'iDisplayLength': 10,
        "language": {
            "sEmptyTable": "Nenhum registro encontrado",
            "sInfo": "Mostrando de _START_ até _END_ de _TOTAL_ registros",
            "sInfoEmpty": "Mostrando 0 até 0 de 0 registros",
            "sInfoFiltered": "(Filtrados de _MAX_ registros)",
            "sInfoPostFix": "",
            "sInfoThousands": ".",
            "sLengthMenu": "_MENU_ resultados por página",
            "sLoadingRecords": "Carregando...",
            "sProcessing": "Processando...",
            "sZeroRecords": "Nenhum registro encontrado",
            "sSearch": "Pesquisar: ",
            "oPaginate": {
                "sNext": "Próximo",
                "sPrevious": "Anterior",
                "sFirst": "Primeiro",
                "sLast": "Último"
            },
            "oAria": {
                "sSortAscending": ": Ordenar colunas de forma ascendente",
                "sSortDescending": ": Ordenar colunas de forma descendente"
            }
        }
    });


    $('#tabelaCarrinho').DataTable({
        'paging': true,
        'lengthChange': true,
        'searching': true,
        'ordering': true,
        'info': true,
        'autoWidth': false,
        'dom': 'B<"top"l>frti<"top"p><"clear">',
        'buttons': [{'extend': 'print', 'text': 'Imprimir Dados', 'className': 'btn btn-default'}],
        'iDisplayLength': 10,
        "language": {
            "sEmptyTable": "Nenhum registro encontrado",
            "sInfo": "Mostrando de _START_ até _END_ de _TOTAL_ registros",
            "sInfoEmpty": "Mostrando 0 até 0 de 0 registros",
            "sInfoFiltered": "(Filtrados de _MAX_ registros)",
            "sInfoPostFix": "",
            "sInfoThousands": ".",
            "sLengthMenu": "_MENU_ resultados por página",
            "sLoadingRecords": "Carregando...",
            "sProcessing": "Processando...",
            "sZeroRecords": "Nenhum registro encontrado",
            "sSearch": "Pesquisar: ",
            "oPaginate": {
                "sNext": "Próximo",
                "sPrevious": "Anterior",
                "sFirst": "Primeiro",
                "sLast": "Último"
            },
            "oAria": {
                "sSortAscending": ": Ordenar colunas de forma ascendente",
                "sSortDescending": ": Ordenar colunas de forma descendente"
            }
        }
    });


}


function consultar() {
    var teste = $("#radioArea input[type='radio']:checked").val();
    if (teste === 'CPF') {
        var retorno;
        $.ajax({
            url: "/vendas/consultarCPF",
            type: "GET",
            async: false,
            dataType: "json",
            data: {
                "CPF": document.getElementById('codigo').value
            },
            success: function (data) {
                retorno = data;
            }
        });

        if (!jQuery.isEmptyObject(retorno)) {
            codigoConsultado = document.getElementById('codigo').value;
            document.getElementById("nomeCli").setAttribute("value", retorno.nome);
            document.getElementById("email").setAttribute("value", retorno.email);
            document.getElementById("tel").setAttribute("value", retorno.telefone);
            document.getElementById("end").setAttribute("value", retorno.endereco);
            $('#btnCliAvanc').prop('disabled', false);
        } else {
            noty({text: 'Cadastro Não Encontrado', type: 'error'});
            $('#btnCliAvanc').prop('disabled', true);
        }
    }
    else if (teste === 'CNPJ') {
        var retorno;
        $.ajax({
            url: "/vendas/consultarCNPJ",
            type: "GET",
            async: false,
            dataType: "json",
            data: {
                "CNPJ": document.getElementById('codigo').value
            },
            success: function (data) {
                retorno = data;
            }
        });
        if (!jQuery.isEmptyObject(retorno)) {
            codigoConsultado = document.getElementById('codigo').value;
            document.getElementById("nomeCli").setAttribute("value", retorno.nomeFant);
            document.getElementById("email").setAttribute("value", retorno.email);
            document.getElementById("tel").setAttribute("value", retorno.telefone);
            document.getElementById("end").setAttribute("value", retorno.endereco);
            $('#btnCliAvanc').prop('disabled', false);
        }
        else {
            noty({text: 'Cadastro Não Encontrado', type: 'error'});
            $('#btnCliAvanc').prop('disabled', true);
        }

    }
}

function avancarCli() {
    $('.nav-tabs a[href="#tab6"]').tab('show');
}

function voltarCli() {
    $('.nav-tabs a[href="#tab5"]').tab('show');
}

function avancarProd() {
    $('.nav-tabs a[href="#tab7"]').tab('show');
}

function voltarProd() {
    $('.nav-tabs a[href="#tab6"]').tab('show');
}

function avancarCarr() {
    $('.nav-tabs a[href="#tab8"]').tab('show');
}

function voltarCarr() {
    $('.nav-tabs a[href="#tab7"]').tab('show');
}


function renderProdutos() {
    var retorno;
    $.ajax({
        url: "/produtos/consulta/lista",
        type: "GET",
        async: false,
        dataType: "json",
        success: function (data) {
            retorno = data;
        }
    });

    for (var i = 0; i < retorno.length; i++) {
        var tr = document.createElement("tr");
        tr.innerHTML = "<td>" + retorno[i].nome + "</td>" +
            "<td>" + retorno[i].categoria + "</td>" +
            "<td>R$ " + retorno[i].preco + "</td>" +
            "<td>" + retorno[i].quantidade + "</td>" +
            "<td><button id='btnAd+" + retorno[i].id + "' onclick='adicionar(\"" + retorno[i].id + "\",\"" + retorno[i].quantidade + "\",\"" + retorno[i].preco + "\",\"" + retorno[i].nome + "\",\"" + retorno[i].categoria + "\","+i+")'><i class='fa fa-shopping-cart'></i></button></td>";
        document.getElementById("tbodyProdutos").appendChild(tr);
    }

}

function adicionar(id, quantidade, preco, nome, categoria, cell) {
    removeParent(modalController);
    var dynamicModal = document.createElement("div");
    dynamicModal.setAttribute("class", "modal fade");
    dynamicModal.id = "modal-adicionar";
    modalController = "modal-adicionar";

    dynamicModal.innerHTML = "<div class='modal-dialog' style='width:30%'>" +
        "<div class='modal-content'>" +
        "<div class='modal-header'>" +
        "<button type='button' class='close' data-dismiss='modal' aria-label='Fechar'>" +
        "<span aria-hidden='true'>&times;</span>" +
        "</button>" +
        "<h4 class='modal-title'> Digite a quantidade que deseja adicionar?</h4>" +
        "</div>" +
        "<div class='modal-body table-responsive' id='modalBody'>" +
        "<p id='mensagem'></p>" +
        "<input type='number' max='" + quantidade + "' min='0' id='qtdSol'/>" +
        "</div>" +
        "<div class='modal-footer'>" +
        "<button type='button' class='btn btn-default pull-left' data-dismiss='modal'>Cancelar</button>" +
        "<button type='button' class='btn btn-primary' onclick='confirmaAdicionar(\"" + id + "\",\"" + parseInt(quantidade) + "\",\"" + preco + "\",\"" + nome + "\",\"" + categoria + "\", \""+cell+"\")'>Confirmar</button>" +
        "</div>" +
        "</div>" +
        "</div>";
    document.getElementById("modalArea").appendChild(dynamicModal);
    $('#modal-adicionar').modal('toggle');
}

function confirmaAdicionar(id, quantidade, preco, nome, cat, cell) {
    var qtdSol = document.getElementById('qtdSol').value;
    if (parseInt(quantidade) >= qtdSol) {
        noty({text: 'Produto Adicionado Com Sucesso', type: 'success'});
        $('#modal-adicionar').modal('toggle');
        var table = $('#tabelaCarrinho').DataTable();

        table.row.add([
            nome,
            cat,
            preco,
            '<p id="p' + id + '">' + qtdSol + '</p>',
            '<button id="btnEd+' + id + '" onclick="editar(\'' + id + '\',\'' + quantidade + '\',\'' + qtdSol + '\', \''+cell+'\')"><i class="fa fa-pencil"></i></button>',
            '<button id="btnRe+' + id + '" onclick="remover(\'' + id + '\')"><i class="fa fa-trash-o"></i></button>'
        ]).node().id = 'tr' + id;
        table.draw();


    } else if (parseInt(quantidade) < qtdSol) {
        noty({text: 'Quantidade Insuficiente', type: 'error'});
    }
}

function removeParent(id) {
    var removeModal = document.getElementById(id);
    if (removeModal != null) {
        removeModal.parentNode.removeChild(removeModal);
    }
}

function editar(id, quantidade, qtdSol, cell) {
    removeParent(modalController);
    var dynamicModal = document.createElement("div");
    dynamicModal.setAttribute("class", "modal fade");
    dynamicModal.id = "modal-editar";
    modalController = "modal-editar";

    dynamicModal.innerHTML = "<div class='modal-dialog' style='width:30%'>" +
        "<div class='modal-content'>" +
        "<div class='modal-header'>" +
        "<button type='button' class='close' data-dismiss='modal' aria-label='Fechar'>" +
        "<span aria-hidden='true'>&times;</span>" +
        "</button>" +
        "<h4 class='modal-title'> Digite a quantidade para qual deseja alterar?</h4>" +
        "</div>" +
        "<div class='modal-body table-responsive' id='modalBody'>" +
        "<p id='mensagem'></p>" +
        "<input type='number' max='" + quantidade + "' min='0' value='" + qtdSol + "' id='qtdSol'/>" +
        "</div>" +
        "<div class='modal-footer'>" +
        "<button type='button' class='btn btn-default pull-left' data-dismiss='modal'>Cancelar</button>" +
        "<button type='button' class='btn btn-primary' onclick='confirmaEditar(\"" + id + "\",\"" + parseInt(quantidade) + "\", \""+cell+ "\")'>Confirmar</button>" +
        "</div>" +
        "</div>" +
        "</div>";
    document.getElementById("modalArea").appendChild(dynamicModal);
    $('#modal-editar').modal('toggle');
}


function confirmaEditar(id, quantidade, cell) {
    var qtdSol = document.getElementById('qtdSol').value;
    if (parseInt(quantidade) >= qtdSol) {
        noty({text: 'Produto Editado Com Sucesso', type: 'success'});
        document.getElementById('p' + id).innerHTML = qtdSol;
        $('#modal-editar').modal('toggle');
        var table = $('#tabelaCarrinho').DataTable();
        table.row(0).cell(0,3).data("<p id=p"+id+">"+qtdSol+"</p>").draw;
    }
    else if (parseInt(quantidade) < qtdSol) {
        noty({text: 'Quantidade Insuficiente', type: 'error'});
    }


}

function remover(id) {
    removeParent(modalController);
    var dynamicModal = document.createElement("div");
    dynamicModal.setAttribute("class", "modal fade");
    dynamicModal.id = "modal-remover";
    modalController = "modal-remover";

    dynamicModal.innerHTML = "<div class='modal-dialog' style='width:30%'>" +
        "<div class='modal-content'>" +
        "<div class='modal-header'>" +
        "<button type='button' class='close' data-dismiss='modal' aria-label='Fechar'>" +
        "<span aria-hidden='true'>&times;</span>" +
        "</button>" +
        "<h4 class='modal-title'> Tem certeza que deseja remover este item do carrinho?</h4>" +
        "</div>" +
        "<div class='modal-footer'>" +
        "<button type='button' class='btn btn-default pull-left' data-dismiss='modal'>Cancelar</button>" +
        "<button type='button' class='btn btn-primary' onclick='confirmaRemover(\"" + id + "\")'>Confirmar</button>" +
        "</div>" +
        "</div>" +
        "</div>";
    document.getElementById("modalArea").appendChild(dynamicModal);
    $('#modal-remover').modal('toggle');
}

function confirmaRemover(id) {
    var table = $('#tabelaCarrinho').DataTable();
    table.row("#tr" + id).remove().draw();
    $('#modal-remover').modal('toggle');
}


function finalizar() {
    var teste = $("#radioArea input[type='radio']:checked").val();
    var codigo = document.getElementById('codigo').value;

    var table = $('#tabelaCarrinho').DataTable();

    var prods = table.rows().data();
    var produtos= [];
    for(var i = 0; i < prods.length; i++){
        var temp = prods[i];
        produtos.push(temp[3]);
    }

    var parcelas = document.getElementById('numParc').value;
    var nf = document.getElementById('nf').value;

    if (teste === 'CPF') {
        $.ajax({
            url: "/vendas/finalizarCPF",
            type: "POST",
            async: false,
            dataType: "json",
            data: {
                "codigo": codigo,
                "prods": JSON.stringify(produtos),
                "nf": nf,
                "parcelas": parcelas
            }
        });
        noty({text: 'Venda Efetuada com sucesso, Redirecionando....', type: 'Success'});
        setTimeout(function(){window.location.reload();}, 1400);
    }
    else if (teste === 'CNPJ') {
        $.ajax({
            url: "/vendas/finalizarCNPJ",
            type: "POST",
            async: false,
            dataType: "json",
            data: {
                "CPF": document.getElementById('codigo').value
            }
        });
        noty({text: 'Venda Efetuada com sucesso, Redirecionando....', type: 'Success'});
        setTimeout(function () {
            window.location.reload();
        }, 1400);
    }
}