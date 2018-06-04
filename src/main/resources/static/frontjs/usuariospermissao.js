var id;
window.onload = function () {
    if(sessionStorage.getItem("editar") === "true"){
        id = sessionStorage.getItem("id");
        var retorno;
        $.ajax({
            url: "/usuariopermissao/get",
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

        $('#inserirUsrPermBtn').attr("onclick", "updateUsrPerm()");
    }
    sessionStorage.clear();

    renderUsuarios();
    renderPermissoes();
};

function inserir() {

    $.ajax({
        url: "/usuariopermissao/inserir",
        type: "POST",
        async: false,
        dataType: "json",
        data: {
            "usuario": document.getElementById("usuario").value,
            "permissao": document.getElementById("perm").value
        }
    });
    var div = document.createElement("div");
    div.setAttribute("class", "alert alert-success");
    div.innerHTML = '<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button><strong>Cadastro efetuado com Sucesso!!!.</strong>';

    document.getElementById("block").appendChild(div);
    limpar();
}

function updateUsrPerm(){
    $.ajax({
        url: "/usuariopermissao/editar",
        type: "POST",
        async: false,
        dataType: "json",
        data: {
            "id":id,
            "nome": document.getElementById("nome").value,
            "login": document.getElementById("login").value
        }
    });

    window.location.href = "../usuariospermissao/consulta"

}

function renderUsuarios(){
    var retorno;
    $.ajax({
        url: "/usuariopermissao/renderUsr",
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
        document.getElementById('usuario').appendChild(opt);
    }
}

function renderPermissoes(){
    var retorno;
    $.ajax({
        url: "/usuariopermissao/renderPerm",
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
        document.getElementById('perm').appendChild(opt);
    }
}