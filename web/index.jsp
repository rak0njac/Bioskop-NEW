<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bioskop.model.Projekcija" %><%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
  <title>Repertoar - Cinematic</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<%--  <link rel="stylesheet" type="text/css" href="/res/css/main.css">--%>
</head>
<body class="bg-dark">

<form action="projekcija" id="frmProj" method="post">
  <input type="hidden" name="frmDatum"/>
  <input type="hidden" name="frmId"/>
</form>

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
              <label for="inputMultiplex">Multiplex</label>
              <select class="form-control mr-2" id="inputMultiplex">
<%--                <option value="ALL">Svi</option>--%>
                <c:forEach items="${requestScope.mul}" var="m">
                  <option value="${m.naziv}">${m.naziv}</option>
                </c:forEach>
              </select>
          </div>
          <div class="col-3">
            <label for="inputDatum">Datum</label>
            <select class="form-control mr-2" id="inputDatum">
<%--              <option value="ALL">Svi</option>--%>
              <c:forEach items="${requestScope.dat}" var="d">
                <option value="${d}">${d}</option>
              </c:forEach>
            </select>
          </div>
        </div>
      </form>
      <div id="movieList"></div>
    </div>
  </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

<script>
  $(document).ready(function(){
    // $.get( "/movieList", function( data ) {
    //   $( "#movieList" ).html( data );
    // });
      loadMovies()

  })

  $("select").change(function () {
    loadMovies()
  })

  function loadMovies(){
      var datum = $("#inputDatum").val()
      $('input[name ="frmDatum"]').val(datum)
      var mplex = $("#inputMultiplex").val()
      $.post( "/movieList", { datum: datum, mplex: mplex }, function( data ) {
          $("#movieList").hide()
          $( "#movieList" ).html( data )
          $("#movieList").fadeIn("250")
      }, "html");
  }

  $("#movieList").on("click", ".btn-projekcija", function() {
    $('input[name ="frmId"]').val($(this).val())
    $("#frmProj").submit();
  })
</script>
</body>
</html>