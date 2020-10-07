<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bioskop.model.Projekcija" %><%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
  <title>Repertoar - Cinematic</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
  <link rel="stylesheet" type="text/css" href="/res/css/main.css">
</head>
<body class="bg-dark">

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
      <form class="mb-5">
        <div class="row">
          <div class="col-3">
            <form action="/getProjByMul" id="frmMultiplex">
              <label for="inputMultiplex">Multiplex</label>
              <select class="form-control mr-2" id="inputMultiplex">
                <c:forEach items="${requestScope.mul}" var="m">
                  <option value="${m.naziv}">${m.naziv}</option>
                </c:forEach>
              </select>
            </form>
          </div>
          <div class="col-3">
            <form action="/getProjByDate" id="frmDatum" method="post">
            <label for="inputDatum">Datum</label>
            <select class="form-control mr-2" id="inputDatum" name="param" onchange="this.form.submit()">
              <c:forEach items="${requestScope.dat}" var="d">
                <option value="${d}">${d}</option>
              </c:forEach>
            </select>
            </form>
          </div>
        </div>
      </form>
      <div class="row bg-light p-3 film mb-2" style="outline: #343a40 solid 5px">
        <div class="col-3">
          <img src="res/img/cover/trolls.jpg" height="200">
        </div>
        <div class="col-9">
          <div class="row">
            <div class="col">
              <b style="font-size: 150%">Trolovi: Nova Avantura</b><br/>
            </div>
          </div>
          <div class="row">
            <div class="col-6">
              Režiser: Walt Dohrn<br/>
              Žanr: Animirani<br/>
              Vreme trajanja: 94 min<br/>
              Godina: 2020.<br/>
              Karte od:<b class="ml-3" style="font-size: 150%">350 din</b>
            </div>
            <div class="col-6">
              Minden csupa csillám és boldogság Trollvárosban, amíg Pipacs királynő és Ágas meglepő felfedezést nem tesznek – ő és Ágas meglepő felfedezést nem tesznek ő és Ágas meglepő  felfedezést nem tesznek ő és Ágas meglepő felfedezést nem tesznek ő és Ágas meglepő felfedezést nem tesznek ő és Ágas meglepő
              </div>
          </div>

        </div>
      </div>
      <c:forEach items="${requestScope.atr}" var="p">

        <div class="row bg-light p-3 film mb-2" style="outline: #343a40 solid 5px">
          <div class="col-3">
            <img src="res/img/cover/trolls.jpg" height="200">
          </div>
          <div class="col-9">
            <div class="row">
              <div class="col">
                <b style="font-size: 150%">${p.film.naziv}</b><br/>
              </div>
            </div>
            <div class="row">
              <div class="col-6">
                Režiser: ${p.film.reziser}<br/>
                Žanr: ${p.film.zanr}<br/>
                Vreme trajanja: ${p.film.trajanje.hours}h ${p.film.trajanje.minutes}min<br/>
                Godina: ${p.film.godina}.<br/>
                Karte od:<b class="ml-3" style="font-size: 150%">350 din</b>
              </div>
              <div class="col-6">
                ${p.film.opis}
              </div>
            </div>

          </div>
        </div>


      </c:forEach>
    </div>
  </div>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
</html>