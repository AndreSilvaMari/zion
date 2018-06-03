var id;
window.onload = function () {
    if(sessionStorage.getItem("editar") === "true"){
        id = sessionStorage.getItem("id");
        var retorno;
        $.ajax({
            url: "/fornecedores/getfor",
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
        document.getElementById("razSocial").setAttribute("value",retorno.razsocial);
        document.getElementById("nomeFant").setAttribute("value",retorno.nomefant);
        document.getElementById("cnpj").setAttribute("value",retorno.cnpj);
        document.getElementById("endFor").setAttribute("value",retorno.endereco);
        document.getElementById("telFor").setAttribute("value",retorno.telefone);
        document.getElementById("emailFor").setAttribute("value",retorno.email);

        $('#inserirForBtn').attr("onclick", "updateFor()");
    }
    sessionStorage.clear();
};

function inserir() {

    $.ajax({
        url: "/fornecedores/inserir",
        type: "POST",
        async: false,
        dataType: "json",
        data: {
            "razSocial": document.getElementById("razSocial").value,
            "nomeFant": document.getElementById("nomeFant").value,
            "cnpj": document.getElementById("cnpj").value,
            "endFor": document.getElementById("endFor").value,
            "telFor": document.getElementById("telFor").value,
            "emailFor": document.getElementById("emailFor").value
        }
    });
    var div = document.createElement("div");
    div.setAttribute("class", "alert alert-success");
    div.innerHTML = '<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button><strong>Cadastro efetuado com Sucesso!!!.</strong>';

    document.getElementById("block").appendChild(div);
    limpar();
}

function limpar() {
    document.getElementById("razSocial").value = "";
    document.getElementById("nomeFant").value = "";
    document.getElementById("cnpj").value = "";
    document.getElementById("endFor").value = "";
    document.getElementById("telFor").value = "";
    document.getElementById("emailFor").value = "";
}

function updateFor(){
    $.ajax({
        url: "/fornecedores/editarFornecedor",
        type: "POST",
        async: false,
        dataType: "json",
        data: {
            "id":id,
            "razSocial": document.getElementById("razSocial").value,
            "nomeFant": document.getElementById("nomeFant").value,
            "cnpj": document.getElementById("cnpj").value,
            "endFor": document.getElementById("endFor").value,
            "telFor": document.getElementById("telFor").value,
            "emailFor": document.getElementById("emailFor").value
        }
    });

    window.location.href = "../fornecedores/consulta"

}