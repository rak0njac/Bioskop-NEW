<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="film" value="${requestScope.film}"/>

<html>
<head>
  <title>${film.naziv} - Cinematic</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body class="bg-dark">

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
        <form id="frm-rezervacija" data-user="${sessionScope.user}" action="/rezervacija" method="post" class="mb-5">
          <div class="row">
            <div class="col">
              <label for="input-datum">Datum</label>
              <select class="form-control mr-2 mb-3" id="input-datum">
                <option value="">Izaberite...</option>
                <c:forEach items="${requestScope.datumi}" var="d">
                  <option value="${d.date}">${d.displayDate}</option>
                </c:forEach>
              </select>
              <h1 id="cena"></h1>
            </div>
            <div class="col">
              <label for="input-projekcija">Projekcija</label>
              <select name="projekcija" class="form-control mr-2 mb-3" id="input-projekcija">
                <option value="">Odaberite datum...</option>
                <c:forEach items="${requestScope.karte}" var="k">
                  <option data-datetime="${k.projekcija.vremePocetka.localDateTime}" data-date="${k.projekcija.vremePocetka.localDate}" value="${k.projekcija.idProjekcija}">${k.projekcija.vremePocetka.displayTime} - Sala ${k.projekcija.sala.broj}</option>
                </c:forEach>
              </select>
              <div id="input-kolicina" style="display: none">
                <label for="kolicina">Kolicina:</label>
                <input type="number" id="kolicina" min="1" value="1" name="kolicina" class="form-control" max="6" style="width:50px;">
              </div>
            </div>
            <select style="display: none" id="hidden-sediste">
              <c:forEach items="${requestScope.karte}" var="k">
                <option data-id="${k.projekcija.idProjekcija}" data-cena="${k.cena}" value="${k.sediste.tip}">${k.sediste.tip}</option>
              </c:forEach>
            </select>
            <div class="col">
              <label for="input-sediste">Sediste</label>
              <select name="sediste" class="form-control mr-2 mb-3" id="input-sediste">
                <option value="">Odaberite datum...</option>
              </select>
              <input type="submit" id="btn-rezervacija" style="display: none" class="btn btn-danger" data-user="${sessionScope.user}" style="width: 100%" value="REZERVISI">
            </div>
          </div>
                        <c:if test="${sessionScope.user.brPoena >= 100 && sessionScope.user.brPoena < 200}">
          <input type="checkbox" name="popust" value="10"> Aktiviraj 10% popusta (100 poena)<br>
                        </c:if>
                        <c:if test="${sessionScope.user.brPoena >= 200}">
                          <input type="radio" name="popust" value="0" checked>Bez popusta<br>
                          <input type="radio" name="popust" value="10"> Aktiviraj 10% popusta (100 poena)<br>
                          <input type="radio" name="popust" value="25"> Aktiviraj 25% popusta (200 poena)
                        </c:if>
          <c:if test="${sessionScope.user.klub.equals('Senior')}">
            <input type="hidden" name="senior">
            <br><span style="color: red">Vi ste clan Senior kluba i automatski Vam je uracunato 20% popusta na prikazanu cenu.</span>
          </c:if>
          <c:if test="${sessionScope.user.klub.equals('Kids') && film.zanr.equals('Animirani')}">
            <input type="hidden" name="kids">
            <br><span style="color: red">Vi ste clan Kids kluba i automatski Vam je uracunato 20% popusta na prikazanu cenu za svaki animirani film.</span>
          </c:if>
        </form>
      </div>
    </div>
  </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

<script src="/js/film.js"></script>

<script src="/js/login.js"></script>

</body></html>