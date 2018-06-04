function logar() {
    var retorno;
    $.ajax({
        url: "/usuarios/login",
        type: "GET",
        async: false,
        dataType: "json",
        data:{
            "login": document.getElementById('login').value,
            "senha": document.getElementById('senha').value
        },
        success: function (data) {
            retorno = data;
        }
    });

    if(retorno.retorno === "Erro"){
        noty({text: 'Login Incorreto tente novamente', type: 'error'});
    }
    else{
        window.location.href = "../index";
    }


    
}



