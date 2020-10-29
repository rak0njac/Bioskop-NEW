$(document).ready(function () {
    var distinct = new Set()
    $(".sediste").each(function () {
        distinct.add($(this).html())
    })
    $("#input-sediste").empty()
    distinct.forEach(function (value, value2, set) {
        $("#input-sediste").append("<div class='sediste' style='display: none'>" + value + "</div>")
    })
})

$("#multiplex").change(function(){
    $("#multiplex").find("option[value='']").remove()
    $("#sala").find("option[value='']").remove()

    var idMul = $(this).val()

    $("#sala > option").show()
    $("#sala > option").each(function () {
        if($(this).attr("data-mul") != idMul)
            $(this).hide()
    })

    var idSala = $("#sala").find("option[data-mul=" + idMul + "]").val()

    $("#sala").val(idSala)
    $("#sala").attr("disabled", false)

    updateSediste()
})

$("#sala").change( function () {
    updateSediste()
})

function updateSediste()
{
    $(".sediste").show()

    $(".sediste").each(function () {
        $(this).find("input").attr("name", "")
        var sed_sala = $(this).find("input").attr("data-sala")
        var sala = $("#sala").val()
        console.log(sed_sala)
        console.log(sala)
        if(sed_sala != sala)
        {
            $(this).hide()
        }
        else{
            var tip = $(this).find("input").attr("data-tip")
            $(this).find("input").attr("name", "cena" + tip)
        }
    })
}
