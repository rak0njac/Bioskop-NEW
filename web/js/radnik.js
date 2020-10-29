
$(document).ready(function(){

    $(".tabela-raspolozivih").each(function () {
        var map = new Map()
        $(this).find(".tip-sedista").each(function () {
            var tip = $(this).text()
            if(map.get(tip) > 0)
                map.set(tip, map.get(tip) + 1)
            else map.set(tip, 1)
        })
        var seen = {}
        $(this).find(".red").each(function () {
            var red = $(this)
            map.forEach(function (value, key, map) {
                var tip = $(red).find(".tip-sedista").text()
                console.log(tip)
                if(key === tip){
                    $(red).find(".broj-slobodnih").text(value)
                }
            })
            var txt = $(red).text();
            if (seen[txt])
                $(red).remove();
            else
                seen[txt] = true;
        })
        $(this).show()
    })
})

$(".btn-potvrdi").click(function () {
    $("#frm-rezervacija").attr("action", "/radnik/potvrdi_rezervaciju")
    $("#frm-rezervacija > input").attr("value", $(this).attr("data-id"))
    $("#frm-rezervacija").submit()
})

$(".btn-otkazi").click(function () {
    $("#frm-rezervacija").attr("action", "/radnik/otkazi_rezervaciju")
    $("#frm-rezervacija > input").attr("value", $(this).attr("data-id"))
    $("#frm-rezervacija").submit()
})

$(".btn-obrisi-sve").click(function () {
    $("#frm-rezervacija").attr("action", "/radnik/otkazi_sve_rezervacije")
    $("#frm-rezervacija > input").attr("value", $(this).attr("data-id"))
    $("#frm-rezervacija").submit()
})