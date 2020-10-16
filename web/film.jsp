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
          <iframe width="560" height="315" src="https://www.youtube.com/embed/08AExF6dETA" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen=""></iframe>
        </div>
        <div class="col">
          <div class="row mb-3">
            <h1>${film.naziv}</h1>
          </div>
          <div class="row">
            <div class="col">
              Režiser: ${film.reziser}<br/>
              Žanr: ${film.zanr}<br/>
              Vreme trajanja: ${film.trajanje.hours}h ${film.trajanje.minutes}min<br/>
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
      <div class="row">
        <div class="col">
          <label for="inputDatum">Datum</label>
          <select class="form-control mr-2 mb-3" id="inputDatum">
            <c:forEach items="${requestScope.projekcije}" var="p">
              <option value="${p.vremePocetka.getTime()}"><fmt:formatDate value="${p.vremePocetka}" pattern="dd.MM.yyyy." /></option>
            </c:forEach>
          </select>
          <h1>Cena: <span id="cena">400</span> din. </h1>
        </div>
        <div class="col">
          <label for="inputProj">Projekcija</label>
          <select class="form-control mr-2" id="inputProj">
            <c:forEach items="${requestScope.projekcije}" var="p">
              <option data-id="${p.idProjekcija}" value="${p.vremePocetka.getTime()}"><fmt:formatDate value="${p.vremePocetka}" pattern="HH:mm" /> - Sala ${p.sala.broj}</option>
            </c:forEach>          </select>
        </div>
        <div class="col">
          <label for="inputSediste">Sediste</label>
          <select class="form-control mr-2 mb-3" id="inputSediste">
            <c:forEach items="${requestScope.sed}" var="sed">
              <option value="${sed.idSediste}">${sed.tip}</option>
            </c:forEach>
          </select>
          <button class="btn btn-danger" style="width: 100%">REZERVISI</button>
        </div>
      </div>
    </div>
  </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

<script>

  var options = []
  var karte = ${requestScope.karte}

  $(document).ready(function () {
    $("#inputProj > option").each(function(){
      options.push($(this))
    })
    console.log(karte)
  })

$("#inputDatum").change(function(){
  var date2
  var date1 = new Date(parseInt($(this).val()))
  date1.setHours(0,0,0,0)
  var results = []
  $(options).each(function () {
    date2 = new Date(parseInt($(this).val()))
    date2.setHours(0,0,0,0)
    console.log("prvi: " + date1.getTime() + " drugi: " + date2.getTime())

    if(date1.getTime() == date2.getTime())
    {
      results.push($(this))
      console.log("rezultati: " + results)
    }
  })
  $("#inputProj").find("option").remove()
  $(results).each(function(){
    $(inputProj).append($(this))
  })
  $('#inputProj').val($('#inputProj > option:first').val())
  getCena()
})

  $("#inputProj").change(function () {
    getCena()
  })
  $("#inputSediste").change(function () {
    getCena()
  })

  function getCena(){
    var projekcija = ($("#inputProj option:selected" ).attr("data-id"))
    console.log("Projekcija: " + projekcija)
    var sediste = $("#inputSediste option:selected" ).val()
    console.log("Sediste: " + sediste)
    $.each(karte, function (i, val) {
      console.log("Projekcija-Karta: " + karte[i].projekcija.idProjekcija)
      if(karte[i].projekcija.idProjekcija == projekcija && karte[i].sediste.idSediste == sediste){
        console.log("true")
        $("#cena").text(karte[i].cena)
        return
      }
    })
  }
</script>
</body></html>