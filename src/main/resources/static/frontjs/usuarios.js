var id;
window.onload = function () {
    if(sessionStorage.getItem("editar") === "true"){
        id = sessionStorage.getItem("id");
        var retorno;
        $.ajax({
            url: "/usuarios/getUsr",
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
        document.getElementById("login").setAttribute("value",retorno.login);
        document.getElementById("email").setAttribute("value",retorno.email);
        document.getElementById("senha").setAttribute("value",retorno.senha);

        $('#ativo select').val(retorno.ativo);

        $('#inserirUsrBtn').attr("onclick", "updateUsr()");
    }
    sessionStorage.clear();
};

function inserir() {

    $.ajax({
        url: "/usuarios/inserir",
        type: "POST",
        async: false,
        dataType: "json",
        data: {
            "nome": document.getElementById("nome").value,
            "login": document.getElementById("login").value,
            "email": document.getElementById("email").value,
            "senha": document.getElementById("senha").value,
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
    document.getElementById("login").value = "";
    document.getElementById("email").value = "";
    document.getElementById("senha").value = "";
    document.getElementById("ativo").value = "";
}

function updateUsr(){
    $.ajax({
        url: "/usuarios/editarUsuario",
        type: "POST",
        async: false,
        dataType: "json",
        data: {
            "id":id,
            "nome": document.getElementById("nome").value,
            "login": document.getElementById("login").value,
            "email": document.getElementById("email").value,
            "senha": document.getElementById("senha").value,
            "ativo": document.getElementById("ativo").value
        }
    });

    window.location.href = "../usuarios/consulta"

}