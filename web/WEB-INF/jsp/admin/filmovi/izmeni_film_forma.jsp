<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bioskop.model.Projekcija" %><%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
  <title>Izmena filma - Cinematic</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body class="bg-dark">
<c:set var="film" value="${requestScope.film}"/>

<jsp:include page="../../navbar.jsp"/>
<div class="container">
  <div class="row  bg-light p-3">
    <div class="col">
        <form action="/admin/filmovi/izmeni_film" method="post" class="form-horizontal" role="form">
            <input type="hidden" name="id" value="${film.idFilm}">
            <h2>Novi film</h2>
            <div class="form-group">
                <label for="naziv" class="col-sm-3 control-label">Naziv</label>
                <div class="col-sm-9">
                    <input type="text" id="naziv" name="naziv" placeholder="Naziv" value="${film.naziv}" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <label for="zanr" class="col-sm-3 control-label">Zanr</label>
                <div class="col-sm-9">
                    <input type="text" id="zanr" name="zanr" placeholder="Zanr" value="${film.zanr}" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <label for="reziser" class="col-sm-3 control-label">Reziser</label>
                <div class="col-sm-9">
                    <input type="text" id="reziser" name="reziser" placeholder="Reziser" value="${film.reziser}" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <label for="godina" class="col-sm-3 control-label">Godina</label>
                <div class="col-sm-9">
                    <input type="text" id="godina" placeholder="Godina" class="form-control" value="${film.godina}" name= "godina">
                </div>
            </div>
            <div class="form-group">
                <label for="trajanje" class="col-sm-3 control-label">Trajanje (format - HH:MM)</label>
                <div class="col-sm-9">
                    <input type="text" id="trajanje" name="trajanje" value="${film.trajanje}" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <label for="opis" class="col-sm-3 control-label">Opis</label>
                <div class="col-sm-9">
                    <textarea rows="10" id="opis" name="opis" class="form-control">${film.opis}</textarea>
                </div>
            </div>
            <div class="form-group">
                <label for="urltrailer" class="col-sm-3 control-label">YouTube Embed URL</label>
                <div class="col-sm-9">
                    <input type="text" id="urltrailer" name="urltrailer" value="${film.urlTrailer}" placeholder="YouTube Embed URL" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <label for="coverpath" class="col-sm-3 control-label">Cover slika</label>
                <div class="col-sm-9">
                    <input type="hidden" name="oldCover" value="${film.coverPath}">
                    <input type="file" id="coverpath" name="coverpath" placeholder="Broj telefona" class="form-control">
                </div>
            </div>
            <button type="submit" class="btn btn-primary btn-block">Gotovo</button>
        </form>

    </div>
  </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

<script src="/js/login.js"></script>

</body>
</html>