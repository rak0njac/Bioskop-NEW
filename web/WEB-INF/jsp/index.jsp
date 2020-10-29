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

<form action="film" id="frm-lista-projekcija" method="get">
  <input type="hidden" name="datum"/>
  <input type="hidden" name="id-projekcija"/>
    <input type="hidden" name="id-multiplex">
</form>

<jsp:include page="navbar.jsp"/>
<div class="container">
  <div class="row  bg-light p-3">
    <div class="col">
      <form class="mb-5">
        <div class="row">
          <div class="col-3">
              <label for="multiplex">Multiplex</label>
              <select class="form-control mr-2" id="multiplex">
                <c:forEach items="${requestScope.multiplexi}" var="m">
                  <option value="${m.idMultiplex}">${m.naziv}</option>
                </c:forEach>
              </select>
          </div>
          <div class="col-3">
            <label for="datum">Datum</label>
            <select class="form-control mr-2" id="datum">
              <c:forEach items="${requestScope.datumi}" var="d">
                <option value="${d.localDate}">${d.displayDate}</option>
              </c:forEach>
            </select>
          </div>
        </div>
      </form>
      <div id="lista-filmova"></div>
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
      var mplex = $("#multiplex").val()
      $.get( "/lista_projekcija", { 'datum': datum, 'id-multiplex': mplex }, function( data ) {
          $("#lista-filmova").hide()
          $( "#lista-filmova" ).html( data )
          $("#lista-filmova").fadeIn("250")
      }, "html");
  }

  $("#lista-filmova").on("click", ".btn-projekcija", function() {
    $('input[name ="id-projekcija"]').val($(this).val())
      $('input[name ="id-multiplex"]').val($("#multiplex").val())
      $("#frm-lista-projekcija").submit();
  })
</script>

<script src="/js/login.js"></script>

</body>
</html>