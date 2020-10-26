<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<html>
<head>
  <title>Repertoar - Cinematic</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body class="bg-dark">
<c:set var="film" value="${requestScope.film}"/>

<jsp:include page="navbar.jsp"/>

<div class="container">
  <div class="row  bg-light p-3">
    <div class="col">
      <div class="row mb-5">
        <div class="col">
          <iframe width="560" height="315" src="${film.urlTrailer}" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen=""></iframe>
        </div>
        <div class="col">
          <div class="row mb-3">
            <h1>${film.naziv}</h1>
          </div>
          <div class="row">
            <div class="col">
              Režiser: ${film.reziser}<br/>
              Žanr: ${film.zanr}<br/>
              Vreme trajanja: ${film.trajanje.hour}h ${film.trajanje.minute}min<br/>
              Godina: ${film.godina}.<br/>
            </div>
            <div class="col">
              <img class="float-right" src="res/img/cover/${film.coverPath}" height="250">
            </div>
          </div>
        </div>
      </div>
      <div class="row mb-5">
        <b>Sinopsis: </b>${film.opis}
      </div>
      <div class="col">
        <form id="frmRez" data-user="${sessionScope.user}" action="/rezervacija" method="post" class="mb-5">
          <div class="row">
            <div class="col">
              <label for="inputDatum">Datum</label>
              <select class="form-control mr-2 mb-3" id="inputDatum">
                <option value="">Izaberite...</option>
                <c:forEach items="${requestScope.datumi}" var="d">
                  <option value="${d.date}">${d.displayDate}</option>
                </c:forEach>
              </select>
              <h1 id="cena"></h1>
            </div>
            <div class="col">
              <label for="inputProj">Projekcija</label>
              <select name="projekcija" class="form-control mr-2 mb-3" id="inputProj">
                <option value="">Odaberite datum...</option>
                <c:forEach items="${requestScope.karte}" var="k">
                  <option data-timestamp="${k.projekcija.vremePocetka.toLocalDate()}" value="${k.projekcija.idProjekcija}">${k.projekcija.vremePocetka.toLocalTime()} - Sala ${k.projekcija.sala.broj}</option>
<%--                  <fmt:formatDate value="${k.projekcija.vremePocetka}" pattern="HH:mm" />--%>
                </c:forEach>
              </select>
              <div id="inputKolicina" style="display: none">
                Kolicina:<input type="number" value="1" name="kolicina" class="form-group ml-2">
              </div>
            </div>
            <select style="display: none" id="hiddenSediste">
              <c:forEach items="${requestScope.karte}" var="k">
                <option data-id="${k.projekcija.idProjekcija}" data-cena="${k.cena}" value="${k.sediste.tip}">${k.sediste.tip}</option>
              </c:forEach>
            </select>
            <div class="col">
              <label for="inputSediste">Sediste</label>
              <select name="sediste" class="form-control mr-2 mb-3" id="inputSediste">
                <option value="">Odaberite datum...</option>
              </select>
              <input type="submit" id="btnRez" style="display: none" class="btn btn-danger" data-user="${sessionScope.user}" style="width: 100%" value="REZERVISI">
            </div>

          </div>
                        <c:if test="${sessionScope.user.getBrPoena} > 100">
          <input type="checkbox" name="popust10"> Aktiviraj 10% popusta (100 poena)<br>
                        </c:if>
                        <c:if test="${sessionScope.user.getBrPoena} > 200">
          <input type="checkbox" name="popust25"> Aktiviraj 25% popusta (200 poena)
                        </c:if>

        </form>
      </div>
    </div>
  </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

<script>

  var options = {}
  var opt = []

  $(document).ready(function () {
   $("#inputProj > option").each(function(){
     if(options[$(this).attr("data-timestamp")]) {
       $(this).remove();
     } else {
       options[$(this).attr("data-timestamp")] = this.value;
     }
   })
    $("#inputProj").attr("disabled", true)
    $("#inputSediste").attr("disabled", true)
  })

$("#inputDatum").change(function(){
  $(this).find("option[value='']").remove()

  var date1 = new Date($(this).val())
  var date2

  $("#inputProj").find("option").show()

  $("#inputProj > option").each(function () {
    date2 = new Date($(this).attr("data-timestamp"))

    if(date1.getTime() != date2.getTime())
    {
      $(this).hide();
    }
  })

  $("#inputProj").attr("disabled", false)
  $("#inputSediste").attr("disabled", false)
  $("#inputKolicina").show(250)
  $("#btnRez").show(250)

  selectFirst($("#inputProj"))
  getSediste()
  getCena()
})

  function selectFirst(element){
    var opts = $(element).find("option")

    $(opts).each(function () {
      if($(this).css('display') == 'none')            // NE DIRAJ - Chrome ne prepoznaje sakrivene optione standardnim selektorima (:hidden itd)
      {
        //console.log("hidden!")
      }
      else{
        //console.log("visible!")
        $(element).val($(this).val())
        return false
      }
    })
  }

  $("#inputProj").change(function () {
    getSediste()
    getCena()
  })

  $("#inputSediste").change(function () {
    getCena()
  })

  function getSediste() {
    $("#inputSediste").find("option").remove()
    var projekcija = ($("#inputProj option:selected").val())
    // var tipovi = new Set()
    var brojPoTipu = []

    $("#hiddenSediste").find("option[data-id=" + projekcija + "]").each(function () {
      brojPoTipu.push($(this).val())
    })

    //var stringArray = count(brojPoTipu)

    //var array_elements = array;
    var stringArray = []

    brojPoTipu.sort();

    var current = null;
    var cnt = 0;
    for (var i = 0; i < brojPoTipu.length; i++) {
      if (brojPoTipu[i] != current) {
        if (cnt > 0)
        {
          stringArray.push(current + ' (' + cnt + ')')
          var cena = $("#hiddenSediste").find("option[value='" + current + "'][data-id=" + projekcija + "]").attr("data-cena")
          $("#inputSediste").append("<option value='" + current + "' data-cena='" + cena + "'>" + current + " (" + cnt + ")" + "</option>")
          //$("#inputKolicina > input").val(1)
          //$("#inputKolicina > input").attr("max", cnt)
          //console.log(current + ' (' + cnt + ')');
        }
        current = brojPoTipu[i];
        cnt = 1;
      } else {
        cnt++;
      }
    }
   if (cnt > 0) {
    stringArray.push(current + ' (' + cnt + ')')
    var cena = $("#hiddenSediste").find("option[value='" + current + "'][data-id=" + projekcija + "]").attr("data-cena")
    $("#inputSediste").append("<option value='" + current + "' data-cena='" + cena + "' data-cnt='" + cnt + "'>" + current + " (" + cnt + ")" + "</option>")
    //$("#inputKolicina > input").val(1)
    //$("#inputKolicina > input").attr("max", cnt)
   }


    //console.log(stringArray)

    selectFirst($("#inputSediste"))
  }

  //////////////////////////////////
  function count(array) {
    var array_elements = array;
    var stringArray = []

    array_elements.sort();

    var current = null;
    var cnt = 0;
    for (var i = 0; i < array_elements.length; i++) {
      if (array_elements[i] != current) {
        if (cnt > 0) {
          stringArray.push(current + ' (' + cnt + ')')
          var cena = $("#hiddenSediste").find("option[value='" + current + "'").attr("data-cena")
          $("#inputSediste").append("<option value='" + current + "' data-cena='" + cena + "'>" + current + " (" + cnt + ")" + "</option>")
          //console.log(current + ' (' + cnt + ')');
        }
        current = array_elements[i];
        cnt = 1;
      } else {
        cnt++;
      }
    }
    if (cnt > 0) {
      stringArray.push(current + ' (' + cnt + ')')
      var cena = $("#hiddenSediste").find("option[value='" + current + "'").attr("data-cena")
      $("#inputSediste").append("<option value='" + current + "' data-cena='" + cena + "'>" + current + " (" + cnt + ")" + "</option>")
      //console.log(current + ' (' + cnt + ')');
    }

    return stringArray
  }


  /////////////////////////////////

  function getCena(){
    $("#inputKolicina > input").val(1)
    var cnt = $("#inputSediste option:selected").attr("data-cnt")
    if(cnt==null)
    {
      $("#inputKolicina > input").attr("max", 1)
    }
    else $("#inputKolicina > input").attr("max", cnt)
    var cena = $("#inputSediste option:selected").attr("data-cena")
        $("#cena").text("Cena: " + cena + " din.")
  }

  $('#frmRez').on('submit', function(event) {
    if($(this).attr("data-user") === "") {
      event.preventDefault();
      $("#modalLogin").modal()
    }
    else this.submit(); //now submit the form
  });
</script>

<script src="../../js/login.js"></script>

</body></html>