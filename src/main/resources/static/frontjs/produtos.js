var id;
window.onload = function () {
    if(sessionStorage.getItem("editar") === "true"){
        id = sessionStorage.getItem("id");
        var retorno;
        $.ajax({
            url: "/produtos/getprod",
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
        document.getElementById("nome").setAttribute("value",retorno.nome);
        document.getElementById("cat").setAttribute("value",retorno.categoria);
        document.getElementById("qtd").setAttribute("value",retorno.quantidade);
        document.getElementById("valor").setAttribute("value",retorno.preco);
        document.getElementById("desc").innerHTML = retorno.descricao;

        $('#inserirProdBtn').attr("onclick", "updateProd()");
    }
    sessionStorage.clear();
};

function inserir() {

    $.ajax({
        url: "/produtos/inserir",
        type: "POST",
        async: false,
        dataType: "json",
        data: {
            "nome": document.getElementById("nome").value,
            "cat": document.getElementById("cat").value,
            "qtd": document.getElementById("qtd").value,
            "valor": document.getElementById("valor").value,
            "desc": document.getElementById("desc").value,
        }
    });
    var div = document.createElement("div");
    div.setAttribute("class", "alert alert-success");
    div.innerHTML = '<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button><strong>Cadastro efetuado com Sucesso!!!.</strong>';

    document.getElementById("block").appendChild(div);
    limpar();
}

function limpar() {
    document.getElementById("cat").value = "";
    document.getElementById("desc").value = "";
    document.getElementById("nome").value = "";
    document.getElementById("valor").value = "";
    document.getElementById("qtd").value = "";
}

function updateProd(){
    $.ajax({
        url: "/produtos/editarProduto",
        type: "POST",
        async: false,
        dataType: "json",
        data: {
            "id":id,
            "cat": document.getElementById("cat").value,
            "desc": document.getElementById("desc").value,
            "nome": document.getElementById("nome").value,
            "valor": document.getElementById("valor").value,
            "qtd": document.getElementById("qtd").value
        }
    });

    window.location.href = "../produtos/consulta"

}