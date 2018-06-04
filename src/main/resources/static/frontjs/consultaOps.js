var table;
var modalController;

window.onload = function () {

    renderOps();

    $('#tabelaOps').DataTable({
        'paging': true,
        'lengthChange': true,
        'searching': true,
        'ordering': true,
        'info': true,
        'autoWidth': false,
        'dom': 'B<"top"l>frti<"top"p><"clear">',
        'buttons': [{'extend': 'print', 'text': 'Imprimir Dados', 'className': 'btn btn-default'}],
        'iDisplayLength': 25,
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

    var table = $('tabelaOps').Database;


};

function renderOps()
{
    var retorno;
    $.ajax({
        url: "/ops/consulta/lista",
        type: "GET",
        async: false,
        dataType: "json",
        success: function(data){
            retorno = data;
        }
    });

    for(var i = 0; i < retorno.length; i++){


        var tr = document.createElement("tr");
        tr.innerHTML = "<td>"+retorno[i].produto+"</td>"+
            "<td>"+retorno[i].quantidade+"</td>"+
            "<td>"+formatarData(retorno[i].dataCriacao)+"</td>"+
            "<td><button id='btnEd+"+retorno[i].id+"' onclick='editar(\""+retorno[i].id+"\")'><i class='fa fa-pencil'></i></button></td>"+
            "<td><button id='btnEx+"+retorno[i].id+"' onclick='excluir(\""+retorno[i].id+"\")'><i class='fa fa-trash-o'></i></button></td>";
        document.getElementById("tbodyOps").appendChild(tr);
    }

}

function excluir(id){
    removeParent(modalController);
    var dynamicModal = document.createElement("div");
    dynamicModal.setAttribute("class", "modal fade");
    dynamicModal.id = "modal-excluir";
    modalController = "modal-excluir";

    dynamicModal.innerHTML = "<div class='modal-dialog' style='width:30%'>" +
        "<div class='modal-content'>" +
        "<div class='modal-header'>" +
        "<button type='button' class='close' data-dismiss='modal' aria-label='Fechar'>" +
        "<span aria-hidden='true'>&times;</span>" +
        "</button>" +
        "<h4 class='modal-title'> Tem certeza que deseja excluir este cadastro?</h4>" +
        "</div>" +
        "<div class='modal-footer'>" +
        "<button type='button' class='btn btn-default pull-left' data-dismiss='modal'>Cancelar</button>" +
        "<button type='button' class='btn btn-primary' onclick='confirmaExcluir(\""+id+"\")'>Confirmar</button>" +
        "</div>" +
        "</div>" +
        "</div>";
    document.getElementById("modalArea").appendChild(dynamicModal);
    $('#modal-excluir').modal('toggle');
}

function confirmaExcluir(id){
    var retorno;
    $.ajax({
        url: "/ops/excluir",
        type: "POST",
        async: false,
        dataType: "json",
        data:{
            "id": id
        },
        success: function(data){
            retorno = data;
        }
    });

    window.location.reload();

}

function editar(id){
    sessionStorage.setItem("editar", "true");
    sessionStorage.setItem("id",id);

    window.location.href = "../ops"

}

function removeParent(id) {
    var removeModal = document.getElementById(id);
    if (removeModal != null) {
        removeModal.parentNode.removeChild(removeModal);
    }
}
function formatarData(data) {

    var final = new Date(data.time);

    var dia = final.getDate();
    if (dia.toString().length == 1)
        dia = "0" + dia;
    var mes = final.getMonth() + 1;
    if (mes.toString().length == 1)
        mes = "0" + mes;
    var ano = final.getFullYear();
    return dia + "/" + mes + "/" + ano;

}