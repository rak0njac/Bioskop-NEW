
var options = {}
var opt = []

$(document).ready(function () {
    $("#input-projekcija > option").each(function(){
        if(options[$(this).attr("data-datetime")]) {
            $(this).remove();
        } else {
            options[$(this).attr("data-datetime")] = this.value;
        }
    })
    $("#input-projekcija").attr("disabled", true)
    $("#input-sediste").attr("disabled", true)
})

$("#input-datum").change(function(){
    $(this).find("option[value='']").remove()

    var date1 = new Date($(this).val())
    var date2

    $("#input-projekcija").find("option").show()

    $("#input-projekcija > option").each(function () {
        date2 = new Date($(this).attr("data-date"))

        if(date1.getTime() != date2.getTime())
        {
            $(this).hide();
        }
    })

    $("#input-projekcija").attr("disabled", false)
    $("#input-sediste").attr("disabled", false)
    $("#input-kolicina").show(250)
    $("#btn-rezervacija").show(250)

    selectFirst($("#input-projekcija"))
    getSediste()
    getCena()
})

function selectFirst(element){
    var opts = $(element).find("option")

    $(opts).each(function () {
        if($(this).css('display') == 'none')            // NE DIRAJ - Chrome ne prepoznaje sakrivene optione standardnim selektorima (:hidden itd)
        {
        }
        else{
            $(element).val($(this).val())
            return false
        }
    })
}

$("#input-projekcija").change(function () {
    getSediste()
    getCena()
})

$("#input-sediste").change(function () {
    $("#input-kolicina > input").val(1)
    var cnt = $("#input-sediste option:selected").attr("data-cnt")
    if(cnt==null)
    {
        $("#input-kolicina > input").attr("max", 1)
    }
    else $("#input-kolicina > input").attr("max", cnt)

    getCena()
})

function getSediste() {
    $("#input-sediste").find("option").remove()
    var projekcija = ($("#input-projekcija option:selected").val())
    var brojPoTipu = []

    $("#hidden-sediste").find("option[data-id=" + projekcija + "]").each(function () {
        brojPoTipu.push($(this).val())
    })


    brojPoTipu.sort();

    var current = null;
    var cnt = 0;
    for (var i = 0; i < brojPoTipu.length; i++) {
        if (brojPoTipu[i] !== current) {
            if (cnt > 0)
            {
                var cena = $("#hidden-sediste").find("option[value='" + current + "'][data-id=" + projekcija + "]").attr("data-cena")
                $("#input-sediste").append("<option value='" + current + "' data-cena='" + cena + "' data-cnt='" + cnt + "'>" + current + " (" + cnt + ")" + "</option>")
            }
            current = brojPoTipu[i];
            cnt = 1;
        } else {
            cnt++;
        }
    }
    if (cnt > 0) {
        var cena = $("#hidden-sediste").find("option[value='" + current + "'][data-id=" + projekcija + "]").attr("data-cena")
        $("#input-sediste").append("<option value='" + current + "' data-cena='" + cena + "' data-cnt='" + cnt + "'>" + current + " (" + cnt + ")" + "</option>")
    }

    selectFirst($("#input-sediste"))
}

$("#kolicina").change(function () {
    getCena()
})

$("input[name='popust']").change(function () {
    getCena()
})

function getCena(){
    var cena = $("#input-sediste option:selected").attr("data-cena")
    var kolicina = $("#input-kolicina > input").val()
    var popust = 0
    if($("input[name='popust']").is(':checked'))
    {
        popust = $("input[name='popust']:checked").val()
    }
    cena = (cena * kolicina) - ((cena * kolicina) * popust / 100)
    $("#cena").text("Cena: " + cena + " din.")
}

$('#frm-rezervacija').on('submit', function(event) {
    if($(this).attr("data-user") === "") {
        event.preventDefault();
        $("#modalLogin").modal()
    }
    else this.submit();
});