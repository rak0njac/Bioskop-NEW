
var defaultModal = "                        <div class=\"sediste-group mb-1\">\n" +
    "                            Od <span class=\"sediste-od\">1</span> do: <input type=\"number\" min=\"2\" value=\"2\" class=\"sediste-do\" style=\"width: 40px;\">\n" +
    "                            Tip: <input type=\"text\" class=\"tip-sedista\"/>\n" +
    "                            <button style=\"display: none\" class=\"btn btn-danger btn-ukloni-sediste\">-</button>\n" +
    "                        </div>"

var brojSala = 0

$("#btn-dodaj-salu").click(function () {
    $("#modal-dodaj-salu").modal()
    $(".modal-title").text("Sala broj " + Number(brojSala + 1))
    $("#sedista").html(defaultModal)
})

$(".btn-dodaj-sediste").click(function () {
    var newGroup = $(".sediste-group").last().clone()
    var sedisteDo = $(newGroup).find(".sediste-do").val()

    console.log(sedisteDo)

    $(newGroup).find(".sediste-od").text(Number(sedisteDo) + 1)
    $(newGroup).find(".sediste-do").val(Number(sedisteDo) + 1)
    $(newGroup).find(".sediste-do").prop("min", Number(sedisteDo) + 1)
    $(newGroup).find(".tip-sedista").val("")
    $(newGroup).find(".btn-ukloni-sediste").show()

    $(".sediste-do").prop("disabled", true)
    $(".btn-ukloni-sediste").hide()

    $("#sedista").append(newGroup)
})

$("#sedista").on("click", ".btn-ukloni-sediste", function(){
    $(this).parents(".sediste-group").remove()
    var lastGroup = $(".sediste-group").last()
    if($(".sediste-group").length !== 1)
    {
        $(lastGroup).find(".btn-ukloni-sediste").show()
        $(lastGroup).find(".sediste-do").prop("disabled", false)
    }
});

$("#btn-dodaj-salu-submit").click(function () {
    var ok = true;
    $(".tip-sedista").each(function () {
        if($(this).val() === ""){
            $("#error").text("Niste popunili sva polja!")
            ok = false;
        }
    })
    if(ok === true){
        brojSala++;
        var htmlStart = "       <div class=\"col kartica\">\n" +
            "                        <div style='min-height: 155px;' class=\"card\">\n" +
            "                            <div class=\"card-body\">\n" +
            "                                <h5 class=\"card-title\">Sala broj " + brojSala + "</h5>\n" +
            "                                <p class=\"card-text\">\n"
        var htmlMiddle = ""
        var htmlEnd = "                       </p>\n" +
            "                                <a href='javascript:void(0)' data-broj-sale='" + brojSala + "' class=\"btn btn-danger btn-ukloni-salu\">Ukloni</a>\n" +
            "                            </div>\n" +
            "                        </div>\n" +
            "                    </div>"
        var htmlForm = "<div class='div-input' data-broj-sale='" + brojSala + "'>"
        $(".sediste-group").each(function () {
            var tip = $(this).find(".tip-sedista").val()
            var sedisteOd = $(this).find(".sediste-od").text()
            var sedisteDo = $(this).find(".sediste-do").val()
            htmlMiddle += tip + " (" + sedisteOd + " - " + sedisteDo + ")<br>"

            for(var i = -1; i < sedisteDo - sedisteOd; i++)
            {
                htmlForm += "<input type=\"hidden\" name=\"sediste" + brojSala + "[]\" value=\""+ tip + "\">\n"
                //$("#frm-multiplex").append("<input type=\"hidden\" name=\"sediste" + brojSala + "[]\" value=\""+ tip + "\">\n")
            }
        })
        htmlForm += "<input type=\"hidden\" name=\"sala[]\" value=\""+ brojSala +"\">\n"
        //$("#frm-multiplex").append("<input type=\"hidden\" name=\"sala[]\" value=\""+ brojSala +"\">\n")
        htmlForm += "</div>"

        $(".btn-ukloni-salu").hide()

        $("#sale").append(htmlStart + htmlMiddle + htmlEnd)
        $("#sale").append(htmlForm)

        $("#modal-dodaj-salu").modal('hide')
    }
})

$("#sale").on("click", ".btn-ukloni-salu", function(){
    $(this).parents("div[class*='kartica']").remove()
    $(".kartica").find(".btn-ukloni-salu").last().show()
});

$("#btn-dodaj-multiplex").click(function () {
    if($("#naziv-multiplex").val() === ""){
        $("#error-multiplex").text("Morate uneti naziv multiplexa!")
    }
    else if($(".card").length === 0)
    {
        $("#error-multiplex").text("Morate dodati bar jednu salu!")
    }
    else $("#frm-multiplex").submit()
})
