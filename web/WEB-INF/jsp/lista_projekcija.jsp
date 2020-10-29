<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${requestScope.projekcije.isEmpty()}">
  <h1>Nema filmova prema izabranim kriterijumima!</h1>
</c:if>
<c:forEach items="${requestScope.projekcije}" var="p">

  <div class="row bg-light p-3 film mb-2" style="outline: #343a40 solid 5px">
    <div class="col-3">
      <img src="res/img/cover/${p.film.coverPath}" height="200">
    </div>
    <div class="col-9">
      <div class="row">
        <div class="col">
          <c:choose>
            <c:when test="${p.premijera == true}">
              <c:choose>
                <c:when test="${sessionScope.user.brPoena < 500}">
                  <span style="font-size: 150%;padding: 0; color:#ff0000;">PREMIJERA - ${p.film.naziv}</span>
                </c:when>
                <c:otherwise>
                  <button style="font-size: 150%;padding: 0;color:#ff0000;" value="${p.idProjekcija}" class="btn btn-link btn-projekcija">PREMIJERA - ${p.film.naziv}</button>
                </c:otherwise>
              </c:choose>
            </c:when>
            <c:otherwise>
              <button style="font-size: 150%;padding: 0;" value="${p.idProjekcija}" class="btn btn-link btn-projekcija">${p.film.naziv}</button>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
      <div class="row">
        <div class="col-6">
          Režiser: ${p.film.reziser}<br/>
          Žanr: ${p.film.zanr}<br/>
          Vreme trajanja: ${p.film.trajanje.hour}h ${p.film.trajanje.minute}min<br/>
          Godina: ${p.film.godina}.<br/>
        </div>
        <div class="col-6" style="display: -webkit-box;
            -webkit-line-clamp: 6;
            -webkit-box-orient: vertical;
            overflow: hidden;" align="justify">
          ${p.film.opis}
        </div>
      </div>
    </div>
  </div>
</c:forEach>