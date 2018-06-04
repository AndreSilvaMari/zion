var id;
window.onload = function () {
    renderProdutos();
    if(sessionStorage.getItem("editar") === "true"){
        id = sessionStorage.getItem("id");
        var retorno;
        $.ajax({
            url: "/ops/get",
            type: "GET",
            async: false,
            dataType: "json",
            data: {
                "id": id
            },
            success: function (data) {
                retorno = data;
            }
        });
        document.getElementById("qtd").setAttribute("value",retorno.quantidade);

        $('#produtos select').val(retorno.produto.id);

        $('#inserirOpBtn').attr("onclick", "updateProd()");
    }
    sessionStorage.clear();
};

function inserir() {

    $.ajax({
        url: "/ops/inserir",
        type: "POST",
        async: false,
        dataType: "json",
        data: {
            "produto": document.getElementById("produtos").value,
            "qtd": document.getElementById("qtd").value
        }
    });
    var div = document.createElement("div");
    div.setAttribute("class", "alert alert-success");
    div.innerHTML = '<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button><strong>Cadastro efetuado com Sucesso!!!.</strong>';

    document.getElementById("block").appendChild(div);
    limpar();
}

function limpar() {
    document.getElementById("produtos").value = "";
    document.getElementById("qtd").value = "";
}

function updateProd(){
    $.ajax({
        url: "/ops/editarOrdensProducao",
        type: "POST",
        async: false,
        dataType: "json",
        data: {
            "id":id,
            "produto": document.getElementById("produtos").value,
            "quantidade": document.getElementById("qtd").value
        }
    });

    window.location.href = "../ops/consulta"

}

function renderProdutos(){
    var retorno;
    $.ajax({
        url: "/ops/renderProds",
        type: "GET",
        async: false,
        dataType: "json",
        success: function (data) {
            retorno = data;
        }
    });

    for(var i = 0; i < retorno.length; i++){
        var opt = document.createElement("option");
        opt.setAttribute("value", retorno[i].id);
        opt.innerHTML = retorno[i].nome;
        document.getElementById('produtos').appendChild(opt);
    }
}