<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
  <title>Repertoar - Cinematic</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body class="bg-dark">
<c:set var="proj" value="${requestScope.film}"/>

<nav class="navbar navbar-expand-lg navbar-light navbar-fixed-top bg-light mb-5">
  <a class="navbar-brand" href="#">
    <img src="/res/img/connect-c-logo.png" width="30" height="30" class="d-inline-block align-top" alt="">
    <b>Cinematic</b>
  </a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse ml-5" id="navbarText">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item">
        <a class="nav-link" href="#"><b>REPERTOAR</b></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="#">O NAMA</a>
      </li>
    </ul>
    <span class="nav-item">
              <button type="button" class="btn btn-dark">PRIJAVA</button>
        </span>
  </div>
</nav>

<div class="container">
  <div class="row  bg-light p-3">
    <div class="col">
      <div class="row mb-5">
        <div class="col">
          <iframe width="560" height="315" src="https://www.youtube.com/embed/08AExF6dETA" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen=""></iframe>
        </div>
        <div class="col">
          <div class="row mb-3">
            <h1>${proj.film.naziv}</h1>
          </div>
          <div class="row">
            <div class="col">
              Režiser: ${proj.film.reziser}<br/>
              Žanr: ${proj.film.zanr}<br/>
              Vreme trajanja: ${proj.film.trajanje.hours}h ${proj.film.trajanje.minutes}min<br/>
              Godina: ${proj.film.godina}.<br/>
            </div>
            <div class="col">
              <img class="float-right" src="res/img/cover/${proj.film.coverPath}" height="250">
            </div>
          </div>
        </div>
      </div>
      <div class="row mb-5">
        <b>Sinopsis: </b>${proj.film.opis}
      </div>
      <div class="row">
        <div class="col">
          <label for="inputDatum">Datum</label>
          <select class="form-control mr-2 mb-3" id="inputDatum">
            <c:forEach items="${requestScope.datumi}" var="d">
              <option value="${d}">${d}</option>
            </c:forEach>
          </select>
          <h1>Cena: 400 din.</h1>
        </div>
        <div class="col">
          <label for="inputProj">Projekcija</label>
          <select class="form-control mr-2" id="inputProj">
            <c:forEach items="${requestScope.vs}" var="vs">
              <option value="${vs}">${vs}</option>
            </c:forEach>          </select>
        </div>
        <div class="col">
          <label for="inputSediste">Sediste</label>
          <select class="form-control mr-2 mb-3" id="inputSediste">
            <c:forEach items="${requestScope.sed}" var="sed">
              <option value="${sed}">${sed}</option>
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
  $("#inputDatum").change(function(){
    var datum = $(this).val()
    <%--$.get( "/projekcijaKarta", { frmId: ${proj.idProjekcija}, frmDatum: datum } ,"json").done(function( data ) {--%>

    <%--        });--%>
    $.get( "/projekcija/getKartaInfo", {frmId: ${proj.idProjekcija}, frmDatum: datum}, function( data ) {
      $.each(data, function(key, value) {
        $('#inputProj').find('option').remove()
        $('#inputProj').append($("<option></option>")
                        .attr("value", key)
                        .text(value));
    }, "json" );})
  })
</script>
</body></html>