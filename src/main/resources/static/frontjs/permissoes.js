var id;
window.onload = function () {
    if(sessionStorage.getItem("editar") === "true"){
        id = sessionStorage.getItem("id");
        var retorno;
        $.ajax({
            url: "/permissoes/getPerm",
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
        document.getElementById("desc").setAttribute("value",retorno.descricao);

        $('#ativo select').val(retorno.ativo);

        $('#inserirPermBtn').attr("onclick", "updatePerm()");
    }
    sessionStorage.clear();
};

function inserir() {

    $.ajax({
        url: "/permissoes/inserir",
        type: "POST",
        async: false,
        dataType: "json",
        data: {
            "nome": document.getElementById("nome").value,
            "desc": document.getElementById("desc").value,
            "ativo": document.getElementById("ativo").value
        }
    });
    var div = document.createElement("div");
    div.setAttribute("class", "alert alert-success");
    div.innerHTML = '<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button><strong>Cadastro efetuado com Sucesso!!!.</strong>';

    document.getElementById("block").appendChild(div);
    limpar();
}

function limpar() {
    document.getElementById("nome").value = "";
    document.getElementById("desc").value = "";
    document.getElementById("ativo").value = "";
}

function updatePerm(){
    $.ajax({
        url: "/permissoes/editarPermissoes",
        type: "POST",
        async: false,
        dataType: "json",
        data: {
            "id":id,
            "nome": document.getElementById("nome").value,
            "desc": document.getElementById("desc").value,
            "ativo": document.getElementById("ativo").value
        }
    });

    window.location.href = "../permissoes/consulta"

}