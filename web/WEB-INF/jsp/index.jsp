<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bioskop.model.Projekcija" %><%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
  <title>Repertoar - Cinematic</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body class="bg-dark">

<form action="film" id="frmProj" method="get">
  <input type="hidden" name="datum"/>
  <input type="hidden" name="projId"/>
    <input type="hidden" name="mplexId">
</form>

<jsp:include page="navbar.jsp"/>
<div class="container">
  <div class="row  bg-light p-3">
    <div class="col">
      <form class="mb-5">
        <div class="row">
          <div class="col-3">
              <label for="mul">Multiplex</label>
              <select class="form-control mr-2" id="mul">
<%--                <option value="ALL">Svi</option>--%>
                <c:forEach items="${requestScope.mul}" var="m">
                  <option value="${m.idMultiplex}">${m.naziv}</option>
                </c:forEach>
              </select>
          </div>
          <div class="col-3">
            <label for="datum">Datum</label>
            <select class="form-control mr-2" id="datum">
<%--              <option value="ALL">Svi</option>--%>
              <c:forEach items="${requestScope.dat}" var="d">
                <option value="${d.date}">${d.displayDate}</option>
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
      loadMovies()
  })

  $("select").change(function () {
    loadMovies()
  })

  function loadMovies(){
      var datum = $("#datum").val()
      $('input[name ="datum"]').val(datum)
      var mplex = $("#mul").val()
      $.get( "/listaProjekcija", { datum: datum, mplex: mplex }, function( data ) {
          $("#movieList").hide()
          $( "#movieList" ).html( data )
          $("#movieList").fadeIn("250")
      }, "html");
  }

  $("#movieList").on("click", ".btn-projekcija", function() {
    $('input[name ="projId"]').val($(this).val())
      $('input[name ="mplexId"]').val($("#mul").val())
      $("#frmProj").submit();
  })
</script>

<script src="/js/login.js"></script>

</body>
</html>