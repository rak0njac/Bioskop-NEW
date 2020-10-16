$("#btnLogin").click(function () {
    $("#modalLogin").modal()
})

$("#btnLogout").click(function () {
    window.location.href = "/logout";
})

$("#btnReg").click(function () {
    window.location.href = "/registracija";
})