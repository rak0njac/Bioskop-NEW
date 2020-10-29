<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bioskop.model.Projekcija" %><%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
  <title>Nova projekcija - Cinematic</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body class="bg-dark">
<c:set var="film" value="${requestScope.film}"/>

<jsp:include page="../../navbar.jsp"/>
<div class="container">
  <div class="row  bg-light p-3">
    <div class="col">
        <form action="/admin/projekcije/dodaj_projekciju" method="post" class="form-horizontal" role="form">
            <input type="hidden" name="id" value="${film.idFilm}">
            <h2>Nova projekcija - ${film.naziv}</h2>
            <div class="form-group">
                <label for="multiplex" class="col-sm-3 control-label">Multiplex</label>
                <div class="col-sm-9">
                    <select id="multiplex" name="multiplex" class="form-control">
                        <option value="">Izaberite...</option>
                        <c:forEach items="${requestScope.multiplexi}" var="m">
                            <option value="${m.idMultiplex}">${m.naziv}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="sala" class="col-sm-3 control-label">Projekciona sala</label>
                <div class="col-sm-9">
                    <select id="sala" disabled="disabled" name="sala" class="form-control">
                        <option value="">Izaberite multiplex...</option>
                        <c:forEach items="${requestScope.sale}" var="sala">
                            <option data-mul="${sala.multiplex.idMultiplex}" value="${sala.idSala}">Sala ${sala.broj}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group" id="input-sediste">
                <c:forEach items="${requestScope.sedista}" var="s">
                    <div class="sediste">
                        Cena za ${s.tip} sediste: <input data-tip="${s.tip}" data-sala="${s.sala.idSala}" type="number"/><br>
                    </div>
                </c:forEach>
            </div>
            <div class="form-group">
                <label for="datum" class="col-sm-3 control-label">Datum</label>
                <div class="col-sm-9">
                    <input type="date" id="datum" name="datum" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <label for="vreme" class="col-sm-3 control-label">Vreme</label>
                <div class="col-sm-9">
                    <input type="time" id="vreme" class="form-control" name= "vreme">
                </div>
            </div>
            <div class="form-group">
                <label for="premijera" class="col-sm-3 control-label">Premijera</label>
                <div class="col-sm-9">
                    <input type="checkbox" id="premijera" name="premijera"  class="form-control">
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

<script src="/js/dodaj_projekciju_forma.js"></script>

</body>
</html>