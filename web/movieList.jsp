<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:forEach items="${requestScope.atr}" var="p">

  <div class="row bg-light p-3 film mb-2" style="outline: #343a40 solid 5px">
    <div class="col-3">
      <img src="res/img/cover/${p.film.coverPath}" height="200">
    </div>
    <div class="col-9">
      <div class="row">
        <div class="col">
            <button style="font-size: 150%;padding: 0;" value="${p.idProjekcija}" class="btn btn-link btn-projekcija">${p.film.naziv}</button>
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