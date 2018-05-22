var id;
window.onload = function () {
    if (sessionStorage.getItem("editar") === "true") {
        if (sessionStorage.getItem("tipo") === "fis") {
            id = sessionStorage.getItem("id");
            var retorno;
            $.ajax({
                url: "/clientefis/getfis",
                type: "POST",
                async: false,
                dataType: "json",
                data: {
                    "id": id
                },
                success: function (data) {
                    retorno = data;
                }
            });
            document.getElementById("nomeFis").setAttribute("value",retorno.nome);
            document.getElementById("cpfFis").setAttribute("value",retorno.cpf);
            document.getElementById("endFis").setAttribute("value",retorno.endereco);
            document.getElementById("telFis").setAttribute("value",retorno.telefone);
            document.getElementById("emailFis").setAttribute("value",retorno.email);

            $('#inserirFisBtn').attr("onclick", "updateClienteFis()");

        }
        else if (sessionStorage.getItem("tipo") === "jur") {
            $('.nav-tabs a[href="#tab20"]').tab('show');

            id = sessionStorage.getItem("id");
            $.ajax({
                url: "/clientejur/getjur",
                type: "POST",
                async: false,
                dataType: "json",
                data: {
                    "id": id
                },
                success: function (data) {
                    retorno = data;
                }
            });
            document.getElementById("razSocial").setAttribute("value",retorno.razao);
            document.getElementById("nomeFant").setAttribute("value",retorno.nomeFant);
            document.getElementById("cnpj").setAttribute("value",retorno.cnpj);
            document.getElementById("im").setAttribute("value",retorno.im);
            document.getElementById("endJur").setAttribute("value",retorno.endereco);
            document.getElementById("telJur").setAttribute("value",retorno.telefone);
            document.getElementById("emailJur").setAttribute("value",retorno.email);

            $('#inserirJurBtn').attr("onclick", "updateClienteJur()");
        }
    }
    sessionStorage.clear();

}

function inserirClienteFis() {

    $.ajax({
        url: "/clientefis/inserir",
        type: "POST",
        async: false,
        dataType: "json",
        data: {
            "nomeFis": document.getElementById("nomeFis").value,
            "cpfFis": document.getElementById("cpfFis").value,
            "endFis": document.getElementById("endFis").value,
            "telFis": document.getElementById("telFis").value,
            "emailFis": document.getElementById("emailFis").value
        }
    });

    var div = document.createElement("div");
    div.setAttribute("class", "alert alert-success");
    div.innerHTML = '<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button><strong>Cadastro efetuado com Sucesso!!!.</strong>';

    document.getElementById("block").appendChild(div);
    limpar();
}

function inserirClienteJur() {

    $.ajax({
        url: "/clientejur/inserir",
        type: "POST",
        async: false,
        dataType: "json",
        data: {
            "razSocial": document.getElementById("razSocial").value,
            "nomeFant": document.getElementById("nomeFant").value,
            "cnpj": document.getElementById("cnpj").value,
            "im": document.getElementById("im").value,
            "endJur": document.getElementById("endJur").value,
            "telJur": document.getElementById("telJur").value,
            "emailJur": document.getElementById("emailJur").value
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
    document.getElementById("im").value = "";
    document.getElementById("endJur").value = "";
    document.getElementById("telJur").value = "";
    document.getElementById("emailJur").value = "";
    document.getElementById("razSocial").value = "";
    document.getElementById("nomeFis").value = "";
    document.getElementById("cpfFis").value = "";
    document.getElementById("endFis").value = "";
    document.getElementById("telFis").value = "";
    document.getElementById("emailFis").value = "";
}


function updateClienteFis(){
    $.ajax({
        url: "/clientefis/editarCliente",
        type: "POST",
        async: false,
        dataType: "json",
        data: {
            "id":id,
            "nomeFis": document.getElementById("nomeFis").value,
            "cpfFis": document.getElementById("cpfFis").value,
            "endFis": document.getElementById("endFis").value,
            "telFis": document.getElementById("telFis").value,
            "emailFis": document.getElementById("emailFis").value
        }
    });

    window.location.href = "../clientefis/consulta"


}

function updateClienteJur(){
    $.ajax({
        url: "/clientejur/editarCliente",
        type: "POST",
        async: false,
        dataType: "json",
        data: {
            "id":id,
            "razSocial": document.getElementById("razSocial").value,
            "nomeFant": document.getElementById("nomeFant").value,
            "cnpj": document.getElementById("cnpj").value,
            "im": document.getElementById("im").value,
            "endJur": document.getElementById("endJur").value,
            "telJur": document.getElementById("telJur").value,
            "emailJur": document.getElementById("emailJur").value
        }
    });

    window.location.href = "../clientejur/consulta"


}